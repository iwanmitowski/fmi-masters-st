package org.example.system;

public class ResponseMessage {
    private int code;
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
        this.code = 200;
    }

    public ResponseMessage(String message, int statusCode) {
        this.message = message;
        this.code = statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getCodeMessage() {
        if (code == 200) {
            return "OK";
        } else if (code == 201) {
            return "Created";
        } else if (code == 204) {
            return "No Content";
        } else if (code == 400) {
            return "Bad Request";
        } else if (code == 401) {
            return "Unauthorized";
        } else if (code == 403) {
            return "Forbidden";
        } else if (code == 404) {
            return "Not Found";
        } else if (code == 500) {
            return "Internal Server Error";
        } else if (code == 502) {
            return "Bad Gateway";
        } else if (code == 503) {
            return "Service Unavailable";
        } else {
            return "Unknown Status Code";
        }
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
}
