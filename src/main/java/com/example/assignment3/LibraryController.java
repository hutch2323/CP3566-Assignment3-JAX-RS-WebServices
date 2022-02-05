package com.example.assignment3;

import java.sql.SQLException;
import java.util.List;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;



@Path("/library")
public class LibraryController {

    LibraryService libraryService = new LibraryService();
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello and welcome to the Library API. The API provides the following services:" +
                "\n1. View Books: use app/library/books" +
                "\n2. View individual book: use app/library/books/ID - where ID is the id of the desired book" +
                "\n3. View Authors: use app/library/authors" +
                "\n4. View individual author: use app/library/authors/ID - where ID is the id of the desired book";
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List getCountries() throws SQLException {

        List<Book> listOfBooks = libraryService.getBooks();
        return listOfBooks;
    }
}