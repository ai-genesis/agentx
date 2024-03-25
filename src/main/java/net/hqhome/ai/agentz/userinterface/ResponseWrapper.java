package net.hqhome.ai.agentz.userinterface;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    private String code;
    private T data;
    private String error;
    private String requestId;

    public static ResponseWrapper success(Object data) {
        ResponseWrapper wrapper = new ResponseWrapper<>();
        wrapper.setCode(ResponseCode.SUCCESS.name());
        wrapper.setData(data);

        return wrapper;
    }

    public static ResponseWrapper failed(String error) {
        ResponseWrapper wrapper = new ResponseWrapper<>();
        wrapper.setCode(ResponseCode.ERROR.name());
        wrapper.setError(error);

        return wrapper;
    }

    public static ResponseWrapper failed(Exception error) {
        ResponseWrapper wrapper = new ResponseWrapper<>();
        wrapper.setCode(ResponseCode.ERROR.name());
        wrapper.setError(error.getMessage());

        return wrapper;
    }

}
