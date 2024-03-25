package net.hqhome.ai.agentz.infrastructor.agent;

import com.alibaba.fastjson2.JSONObject;
import net.hqhome.ai.agentz.domain.agent.ChatMessage;
import net.hqhome.ai.agentz.domain.agent.IAgentResource;
import net.hqhome.ai.agentz.domain.agent.ModelType;
import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;
import net.hqhome.ai.agentz.domain.agent.authorization.BearerAuthorization;
import net.hqhome.ai.agentz.infrastructor.agent.dto.OpenAIRequest;
import net.hqhome.ai.agentz.infrastructor.agent.dto.OpenAIResponse;
import net.hqhome.ai.agentz.infrastructor.common.SuperHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AgentResource implements IAgentResource {

    @Autowired
    private SuperHttpClient superHttpClient;

    @Override
    public String chatCompletions(ModelType type, String url, String token, String model, List<ChatMessage> messageList, List<String> stop, Double temperature) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        Class clazz = null;
        Object body = null;

        switch (type) {
            case OPENAI:
                clazz = OpenAIResponse.class;
                body = new OpenAIRequest(model, messageList, stop, temperature);
                break;
            default:
                // no op
        }

        Object res = superHttpClient.request(url, HttpMethod.POST, headers, body, clazz);

        switch (type) {
            case OPENAI:
                return ((OpenAIResponse) res).getChoices().get(0).getMessage().getContent();
            default:
                // no op
        }

        return null;
    }

    @Override
    public String chatCompletions(ModelType type, String url, Authorization authorization, String model, List<ChatMessage> messageList, List<String> stop, Double temperature) {
        if (authorization instanceof BearerAuthorization bearerAuthorization) {
            return chatCompletions(type, url, bearerAuthorization.getToken(), model, messageList, stop, temperature);
        }
        return null;
    }
}
