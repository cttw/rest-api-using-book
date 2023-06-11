package com.example.api;

import com.example.dao.BookDAO;
import com.example.model.*;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;


public class BookRoutes {
    private BookDAO bookDAO;
    private Gson gson;

    public BookRoutes(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
        this.gson = new Gson();
    }

    public Route getAllBooks = (Request request, Response response) -> {
        if(isAuthorized(request)) {
            List<Book> books = bookDAO.getAllBooks();
            response.type("application/json");
            return gson.toJson(books);
        }
        else {
            response.type("application/json");
            response.status(401);
            return gson.toJson(new ErrorResponse("Unauthorized", "Invalid API key"));
        }
    };

    public Route addBook = (Request request, Response response) -> {
        if(isAuthorized(request)) {
            Book book = gson.fromJson(request.body(), Book.class);
            int bookId = bookDAO.addBook(book);
            response.status(201); // Created
            response.type("application/json");
            return gson.toJson(new StandardResponse("Book created successfully", bookId));
        }
        else {
            response.type("application/json");
            response.status(401);
            return gson.toJson(new ErrorResponse("Unauthorized", "Invalid API key"));
        }
    };

    public Route updateBook = (Request request, Response response) -> {
        if(isAuthorized(request)) {
            int bookId = Integer.parseInt(request.params(":id"));
            Book book = gson.fromJson(request.body(), Book.class);
            book.setId(bookId);
            bookDAO.updateBook(book);
            response.type("application/json");
            return gson.toJson(new StandardResponse("Book updated successfully", bookId));
        }
        else {
            response.type("application/json");
            response.status(401);
            return gson.toJson(new ErrorResponse("Unauthorized", "Invalid API key"));
        }
    };

    public Route deleteBook = (Request request, Response response) -> {
        if(isAuthorized(request)) {
            int bookId = Integer.parseInt(request.params(":id"));
            bookDAO.deleteBook(bookId);
            response.type("application/json");
            return gson.toJson(new StandardResponse("Book deleted successfully", bookId));
        }
        else {
            response.type("application/json");
            response.status(401);
            return gson.toJson(new ErrorResponse("Unauthorized", "Invalid API key"));
        }
    };

    private static boolean isAuthorized(Request request) {
        String apiKey = request.headers("X-API-Key");
        String validApiKey = "pv-user00xyz"; // Valid API key

        return validApiKey.equals(apiKey);
    }

    private static class ErrorResponse {
        private String status;
        private String message;

        public ErrorResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
