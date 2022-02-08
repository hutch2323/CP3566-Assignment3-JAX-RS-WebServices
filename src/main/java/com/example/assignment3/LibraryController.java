package com.example.assignment3;

import java.sql.SQLException;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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

        // not finished yet. Need to add all options
        return "Hello and welcome to the Library API. The API provides the following services:" +
                "\n1. View Books: use app/library/books" +
                "\n2. View individual book: use app/library/book/ID - where ID is the ISBN of the desired book" +
                "\n3. View Authors: use app/library/authors" +
                "\n4. View individual author: use app/library/author/ID - where ID is the authorID of the desired author";
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() throws SQLException {
        return libraryService.getBooks();
    }

    @GET
    @Path("/authors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() throws SQLException {
        return libraryService.getAuthors();
    }

    @GET
    @Path("/book/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBookById(@PathParam("id") String id) throws SQLException {
        return libraryService.getBook(id);
    }

    @GET
    @Path("/author/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author getAuthorById(@PathParam("id") int id) throws SQLException {
        return libraryService.getAuthor(id);
    }

    @POST
    @Path("/addbook")
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook(Book book) throws SQLException {
        return libraryService.addBook(book);
    }

    @POST
    @Path("/addauthor")
    @Produces(MediaType.APPLICATION_JSON)
    public Author addAuthor(Author author) throws SQLException {
        return libraryService.addAuthor(author);
    }

    @POST
    @Path("/associateauthor")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorISBN associateAuthor(AuthorISBN authorISBN) throws SQLException {
        return libraryService.addAuthorISBN(authorISBN);
    }


}