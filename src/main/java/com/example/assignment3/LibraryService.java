package com.example.assignment3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    public List<Book> getBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String querySQL = "Select * from titles";

        String sql = "Select a.authorID, a.firstName, a.lastName " +
                "from authors a join authorisbn ai " +
                "ON a.authorID = ai.authorID " +
                "JOIN titles t " +
                "ON ai.isbn = t.isbn " +
                "where t.isbn = ?";

        try (Connection conn = DBConnection.initDatabase();
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery(querySQL);

            while (rs.next()) {
                Book book = new Book(rs.getString("isbn"), rs.getString("title"),
                        rs.getInt("editionNumber"), rs.getString("copyright"));

                pstmt.setString(1, book.getIsbn());
                ResultSet rsAuthors = pstmt.executeQuery();

                while (rsAuthors.next()) {
                    Author author = new Author(rsAuthors.getInt("authorID"),
                            rsAuthors.getString("firstName"), rsAuthors.getString("lastName"));
                    book.getAuthorList().add(author);
                }
                bookList.add(book);
            }
        }

        return bookList;
    }
}
