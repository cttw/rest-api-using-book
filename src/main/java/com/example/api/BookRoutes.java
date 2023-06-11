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
        List<Book> books = bookDAO.getAllBooks();
        response.type("application/json");
        return gson.toJson(books);
    };

    public Route addBook = (Request request, Response response) -> {
        Book book = gson.fromJson(request.body(), Book.class);
        int bookId = bookDAO.addBook(book);
        response.status(201); // Created
        response.type("application/json");
        return gson.toJson(new StandardResponse("Book created successfully", bookId));
    };

    public Route updateBook = (Request request, Response response) -> {
        int bookId = Integer.parseInt(request.params(":id"));
        Book book = gson.fromJson(request.body(), Book.class);
        book.setId(bookId);
        bookDAO.updateBook(book);
        response.type("application/json");
        return gson.toJson(new StandardResponse("Book updated successfully", bookId));
    };

    public Route deleteBook = (Request request, Response response) -> {
        int bookId = Integer.parseInt(request.params(":id"));
        bookDAO.deleteBook(bookId);
        response.type("application/json");
        return gson.toJson(new StandardResponse("Book deleted successfully", bookId));
    };
}
