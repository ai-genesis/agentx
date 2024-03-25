package net.hqhome.ai.agentz.infrastructor.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.util.ParameterizedTypeImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Component
public class SuperHttpClient {

    private RestTemplate restTemplate;
    private RestTemplate streamableRestTemplate;

    @Value("${superHttpClient.connectTimeout:10}")
    private long connectTimeout;
    @Value("${superHttpClient.socketTimeout:10}")
    private long socketTimeout;
    @Value("${superHttpClient.maxConnPerRoute:10}")
    private int maxConnPerRoute;
    @Value("${superHttpClient.maxConnTotal:10}")
    private int maxConnTotal;
    @Value("#{new Boolean('${superHttpClient:debugResponseBody:false}')}")
    private boolean debugResponseBody;

    public SuperHttpClient() {
        restTemplate = new RestTemplate(getClientHttpRequestFactory(debugResponseBody));
        streamableRestTemplate = new RestTemplate(getClientHttpRequestFactory(false));

        restTemplate.setInterceptors(List.of(new LoggingRequestInterceptor(debugResponseBody)));
        streamableRestTemplate.setInterceptors(List.of(new LoggingRequestInterceptor(false)));
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory(boolean debugResponseBody) {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(getHttpClient());
        return debugResponseBody ? new BufferingClientHttpRequestFactory(httpComponentsClientHttpRequestFactory) : httpComponentsClientHttpRequestFactory;
    }

    private HttpClient getHttpClient() {
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(connectTimeout))
                .setSocketTimeout(Timeout.ofMilliseconds(socketTimeout))
                .build();
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                .setMaxConnPerRoute(maxConnPerRoute)
                .setMaxConnTotal(maxConnTotal)
                .build();

        return HttpClientBuilder.create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .build();
    }

    public <T> T request(String url, HttpMethod method, HttpHeaders headers, Object body, Type... types) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (Exception e) {
            throw new SuperHttpClientException(String.format("SuperHttpClient %s %s occurs error", method, url), e);
        }
        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        Class clazz;
        boolean isRawType = true;

        if (types.length == 1) {
            clazz = (Class) types[0];
        } else {
            clazz = String.class;
            isRawType = false;
        }
        ResponseEntity response;

        try {
            response = restTemplate.exchange(uri, method, entity, clazz);
        } catch (HttpClientErrorException e) {
            throw new SuperHttpClientException(String.format("SuperHttpClient %s %s occurs error", method, uri), e);
        } catch (HttpServerErrorException e) {
            throw new SuperHttpClientException(String.format("SuperHttpClient %s %s occurs error", method, uri), e);
        } catch (Exception e) {
            throw new SuperHttpClientException(String.format("SuperHttpClient %s %s occurs error", method, uri), e);
        }

        if (isRawType) {
            return (T) response.getBody();
        } else {
            ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl(types[0], Arrays.copyOfRange(types, 1, types.length));
            return JSON.parseObject((String) response.getBody(), parameterizedType);
        }
    }

    public void streamableRequest(String url, HttpMethod method, HttpHeaders headers, Object body, Consumer<ClientHttpResponse> responseConsumer) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new SuperHttpClientException(String.format("SuperHttpClient %s %s occurs error", method, url), e);
        }
        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        try {
            streamableRestTemplate.execute(uri, method, streamableRestTemplate.httpEntityCallback(entity), clientHttpResponse -> {
                responseConsumer.accept(clientHttpResponse);
                return null;
            });
        } catch (HttpClientErrorException e) {
            throw new SuperHttpClientException(String.format("SuperHttpClient %s %s occurs error", method, uri), e);
        } catch (HttpServerErrorException e) {
            throw new SuperHttpClientException(String.format("SuperHttpClient %s %s occurs error", method, uri), e);
        } catch (Exception e) {
            throw new SuperHttpClientException(String.format("SuperHttpClient %s %s occurs error", method, uri), e);
        }
    }

    public static class SuperHttpClientException extends RuntimeException {
        public SuperHttpClientException(Throwable cause) {
            super(cause);
        }
        public SuperHttpClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

        private boolean debugResponseBody;

        public LoggingRequestInterceptor(boolean debugResponseBody) {
            this.debugResponseBody = debugResponseBody;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            String requestId = generateRequestId();
            traceRequest(request, body, requestId);
            long start = System.currentTimeMillis();
            ClientHttpResponse response = execution.execute(request, body);
            long end = System.currentTimeMillis();
            traceResponse(response, requestId, end - start);
            return response;
        }

        private String generateRequestId() {
            return UUID.randomUUID().toString().split("-")[0];
        }

        private void traceRequest(HttpRequest request, byte[] body, String requestId) throws IOException {
            log.info("[{}] SuperHttpClient {} {}, headers: {}, body: {}", requestId, request.getMethod(), request.getURI(), request.getHeaders(), new String(body, "UTF-8"));
        }

        private void traceResponse(ClientHttpResponse response, String requestId, long lapse) throws IOException {
            log.info("[{}] SuperHttpClient response in {}ms, status: {}, headers: {}, body: {}", requestId, lapse, response.getStatusCode(), response.getHeaders(), debugResponseBody ? getResponseBody(response) : "");
        }

        private String getResponseBody(ClientHttpResponse response) throws IOException {
            StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }

            return inputStringBuilder.toString();
        }
    }
}
