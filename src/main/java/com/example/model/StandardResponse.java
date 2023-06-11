package com.example.model;

public class StandardResponse {
    private String message;
    private int bookId;

    public StandardResponse(String message, int bookId) {
        this.message = message;
        this.bookId = bookId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
// Getters and setters
}

