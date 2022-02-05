package com.example.assignment3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dbTest {
    public static void main(String[] args) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Connection conn = DBConnection.initDatabase();
        Statement stmt = conn.createStatement();
        String sql = "Select * From titles";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Book book = new Book(rs.getString("isbn"), rs.getString("title"),
                    rs.getInt("editionNumber"), rs.getString("copyright"));
            bookList.add(book);
        }
        for(Book book : bookList){
            System.out.println(book.getTitle());
        }
    }
}
