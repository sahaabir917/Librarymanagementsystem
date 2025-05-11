package com.infinitycodehubltd.librarymanagement.apiresponse;

public class ApiResponse {
    private String message;
    private int code;

    // Constructors, Getters, and Setters


    public ApiResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ApiResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}