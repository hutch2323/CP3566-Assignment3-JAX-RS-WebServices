package com.example.assignment3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dbTest {
    public static void main(String[] args) throws SQLException {
        LibraryService libraryService = new LibraryService();
        List<Book> bookList = libraryService.getBooks();
        for(Book book : bookList){
            System.out.println(book.getTitle());
        }
    }
}
