package net.hqhome.ai.agentz.userinterface;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    private String code;
    private T data;
    private String error;
    private String requestId;

    public static <T> ResponseWrapper<T> success(T data) {
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setCode(ResponseCode.SUCCESS.name());
        wrapper.setData(data);

        return wrapper;
    }

    public static <T> ResponseWrapper<T> failed(String error) {
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setCode(ResponseCode.ERROR.name());
        wrapper.setError(error);

        return wrapper;
    }

    public static <T> ResponseWrapper<T> failed(Exception error) {
        ResponseWrapper<T> wrapper = new ResponseWrapper<>();
        wrapper.setCode(ResponseCode.ERROR.name());
        wrapper.setError(error.getMessage());

        return wrapper;
    }

}
