package com.infinitycodehubltd.librarymanagement.entity;

public class ReturnItemResult {

    private Long issueBookId;
    private boolean success;
    private String message;

    public ReturnItemResult(Long issueBookId, boolean success, String message) {
        this.issueBookId = issueBookId;
        this.success = success;
        this.message = message;
    }

    public Long getIssueBookId() {
        return issueBookId;
    }

    public void setIssueBookId(Long issueBookId) {
        this.issueBookId = issueBookId;
    }

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
