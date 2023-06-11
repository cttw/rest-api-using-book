package com.example.api;

import com.example.dao.BookDAO;
import com.google.gson.Gson;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        // Create instances of BookDAO, BookRoutes, and Gson
        BookDAO bookDAO = new BookDAO();
        BookRoutes bookRoutes = new BookRoutes(bookDAO);

        Gson gson = new Gson();

        // Configure Spark
        port(8080);

        // Define API routes
        get("/books", bookRoutes.getAllBooks);
        post("/books", bookRoutes.addBook);
        put("/books/:id", bookRoutes.updateBook);
        delete("/books/:id", bookRoutes.deleteBook);
    }
}
