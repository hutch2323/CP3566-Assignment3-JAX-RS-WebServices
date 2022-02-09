package com.example.assignment3;

import java.sql.SQLException;
import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


@Path("/library")
public class LibraryController {

    LibraryService libraryService = new LibraryService();
    @GET
    @Produces("text/plain")
    public String hello() {

        // not finished yet. Need to add all options
        return "Hello and welcome to the Library API. The API provides the following services:" +
                "\n1.  View Books: navigate to app/library/books" +
                "\n2.  View individual book: navigate to app/library/book/ID - where ID is the ISBN of the desired book" +
                "\n3.  View Authors: navigate to app/library/authors" +
                "\n4.  View individual author: navigate to app/library/author/ID - where ID is the authorID of the desired author" +
                "\n5.  Add book: send a json Book object to the path app/library/addbook using a POST request" +
                "\n6.  Add author: send a json Author object to the path app/library/addauthor using a POST request" +
                "\n7.  Associate author with book: send a json AuthorISBN (authorID, isbn) object to the path app/library/associateauthor using a POST request" +
                "\n8.  Modify book: send a modified json Book object to the path app/library/modbook using a PUT request" +
                "\n9.  Modify author: send a modified json Author object to the path app/library/modauthor using a PUT request" +
                "\n10. Delete book: navigate to app/library/delbook/ID - where ID is the ISBN of the desired book" +
                "\n11. Delete author: navigate to app/library/delauthor/ID - where ID is the authorID of the desired author";
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

    @PUT
    @Path("/modbook")
    @Produces(MediaType.APPLICATION_JSON)
    public Book modBook(Book book) throws SQLException {
        return libraryService.updateBook(book);
    }

    @PUT
    @Path("/modauthor")
    @Produces(MediaType.APPLICATION_JSON)
    public Author modAuthor(Author author) throws SQLException {
        return libraryService.updateAuthor(author);
    }

    @DELETE
    @Path("/delbook/{id}")
    @Produces("text/plain")
    public String delBook(@PathParam("id") String id) throws SQLException {
        return libraryService.deleteBook(id);
    }

    @DELETE
    @Path("/delauthor/{id}")
    @Produces("text/plain")
    public String delAuthor(@PathParam("id") int id) throws SQLException {
        return libraryService.deleteAuthor(id);
    }

}