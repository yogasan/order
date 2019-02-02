package com.yoga.order.constant;

import java.io.Serializable;
import java.util.Map;

public class BaseResponse<T> implements Serializable {
    private int statusCode;
    private String statusMsg;
    private T data;

    public BaseResponse() {
        this.statusCode = ResponseStatusCode.OPERATION_SUCCESS.getCode();
        this.statusMsg = ResponseStatusCode.OPERATION_SUCCESS.getMsg();
    }

    public String getStatusMsg() {
        return this.statusMsg;
    }

    public BaseResponse setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public BaseResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public BaseResponse setStatusCode(ResponseStatusCode statusCode) {
        this.setStatusCode(statusCode.getCode());
        this.setStatusMsg(statusCode.getMsg());
        return this;
    }

    @Override
    public String toString() {
        return "BaseResponse{statusCode=" + this.statusCode + ", statusMsg='" + this.statusMsg + '\'' + ", data=" + this.data + '}';
    }

    public static <T> BaseResponse<T> successInstance(T date) {
        BaseResponse<T> response = new BaseResponse();
        response.setStatusCode(ResponseStatusCode.OPERATION_SUCCESS);
        return response.setData(date);
    }

    public static BaseResponse successInstance(String msg) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(ResponseStatusCode.OPERATION_SUCCESS);
        response.setStatusMsg(msg);
        return response;
    }

    public static BaseResponse errorInstance(String errorMsg) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(ResponseStatusCode.SERVER_ERROR);
        response.setStatusMsg(errorMsg);
        return response;
    }

    public static BaseResponse errorInstance(int status, String errorMsg) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(status);
        response.setStatusMsg(errorMsg);
        return response;
    }

    public static BaseResponse notLogin() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(ResponseStatusCode.UNAUTHORIZED_ERROR);
        response.setStatusMsg("登录失效，请重新登录！");
        return response;
    }

    public static BaseResponse queryDataEmpty() {
        BaseResponse response = new BaseResponse();
        ResponseStatusCode statusCode = ResponseStatusCode.QUERY_DATA_EMPTY;
        response.setStatusCode(statusCode);
        return response;
    }

    public static BaseResponse idError(String... idName) {
        BaseResponse response = new BaseResponse();
        ResponseStatusCode statusCode = ResponseStatusCode.ID_VALUE_ERROR;
        response.setStatusCode(statusCode);
        String[] var3 = idName;
        int var4 = idName.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String id = var3[var5];
            response.setStatusMsg(statusCode.getMsg() + ":参数" + id);
        }

        return response;
    }

    public static BaseResponse idNull(String... idName) {
        BaseResponse response = new BaseResponse();
        ResponseStatusCode statusCode = ResponseStatusCode.ID_VALUE_NULL;
        response.setStatusCode(statusCode);
        String[] var3 = idName;
        int var4 = idName.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String id = var3[var5];
            response.setStatusMsg(statusCode.getMsg() + ":没有传输参数" + id);
        }

        return response;
    }

    public static <T> BaseResponse service(Map<String, T> map) {
        BaseResponse<T> baseResponse = new BaseResponse();
        T data = map.get(ServiceKey.data.name());
        Boolean success = (Boolean)map.get(ServiceKey.success.name());
        if (success) {
            return successInstance(data);
        } else {
            baseResponse.setData(data);
            Object status = map.get(ServiceKey.status.name());
            int statusCode;
            if (status == null) {
                statusCode = ResponseStatusCode.SERVER_ERROR.getCode();
            } else {
                statusCode = (Integer)status;
            }

            baseResponse.setStatusCode(statusCode);
            Object att = map.get(ServiceKey.attribute.name());
            Object msg = map.get(ServiceKey.msg.name());
            String message = null;
            if (msg == null) {
                if (att != null) {
                    message = "(" + att + ")";
                }
            } else {
                message = (String)msg;
                if (att != null) {
                    message = message + "(" + att + ")";
                }
            }

            baseResponse.setStatusMsg(message);
            return baseResponse;
        }
    }
}
