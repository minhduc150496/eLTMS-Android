package com.project.xetnghiem.api.responseObj;

import com.google.gson.annotations.SerializedName;

public class ResponseMessage {
    @SerializedName("Success")
    private boolean success;
    @SerializedName("Message")
    private String message;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
