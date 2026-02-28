package com.infinitycodehubltd.librarymanagement.entity;

import java.util.List;

public class BulkReturnRequest {

    private Long userId;
    private List<ReturnItem> items;
    private String returnedBy; // "USER" or "ADMIN"

    public static class ReturnItem {
        private Long issueBookId;

        public Long getIssueBookId() {
            return issueBookId;
        }

        public void setIssueBookId(Long issueBookId) {
            this.issueBookId = issueBookId;
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ReturnItem> getItems() {
        return items;
    }

    public void setItems(List<ReturnItem> items) {
        this.items = items;
    }

    public String getReturnedBy() {
        return returnedBy;
    }

    public void setReturnedBy(String returnedBy) {
        this.returnedBy = returnedBy;
    }
}
