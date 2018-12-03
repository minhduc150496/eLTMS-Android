package com.project.xetnghiem.api.responseObj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SuccessResponse implements Serializable {
        @SerializedName("Status")
        private String status;
        @SerializedName("Success")
        private boolean success;
        @SerializedName("Code")
        private int code;
        @SerializedName("Message")
        private String message;
        @SerializedName("Data")
        private Object data;

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public int getCode() {
                return code;
        }

        public void setCode(int code) {
                this.code = code;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public Object getData() {
                return data;
        }

        public void setData(Object data) {
                this.data = data;
        }

        public boolean isSuccess() {
                return success;
        }

        public void setSuccess(boolean success) {
                this.success = success;
        }
}
