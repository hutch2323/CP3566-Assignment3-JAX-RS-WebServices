package com.example.assignment3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    public static List<Book> getBooks() throws SQLException {
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

    public static List<Author> getAuthors() throws SQLException {
        List<Author> authorList = new ArrayList<>();
        String querySQL = "Select * from authors";

        String sql = "Select t.isbn, t.title, t.editionNumber, t.copyright " +
                "from titles t join authorisbn ai " +
                "ON t.isbn = ai.isbn " +
                "JOIN authors a " +
                "ON ai.authorID = a.authorID " +
                "where a.authorID = ?";

        try (Connection conn = DBConnection.initDatabase();
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery(querySQL);

            while (rs.next()) {
                Author author = new Author(rs.getInt("authorID"), rs.getString("firstName"),
                        rs.getString("lastName"));

                pstmt.setInt(1, author.getAuthorID());
                ResultSet rsBooks = pstmt.executeQuery();

                while (rsBooks.next()) {
                    Book book = new Book(rsBooks.getString("isbn"), rsBooks.getString("title"),
                            rsBooks.getInt("editionNumber"), rsBooks.getString("copyright"));
                    author.getBookList().add(book);
                }
                authorList.add(author);
            }
        }
        return authorList;
    }

    public Book getBook(String isbn) throws SQLException {
        String querySQL = "Select * from titles " +
                "where isbn = ?";

        String sql = "Select a.authorID, a.firstName, a.lastName " +
                "from authors a join authorisbn ai " +
                "ON a.authorID = ai.authorID " +
                "JOIN titles t " +
                "ON ai.isbn = t.isbn " +
                "where t.isbn = ?";

        try (Connection conn = DBConnection.initDatabase();
             PreparedStatement pstmt = conn.prepareStatement(querySQL);
             PreparedStatement pstmt2 = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Book book = new Book(rs.getString("isbn"), rs.getString("title"),
                        rs.getInt("editionNumber"), rs.getString("copyright"));

                pstmt2.setString(1, book.getIsbn());
                ResultSet rsAuthors = pstmt2.executeQuery();

                while (rsAuthors.next()) {
                    Author author = new Author(rsAuthors.getInt("authorID"),
                            rsAuthors.getString("firstName"), rsAuthors.getString("lastName"));
                    book.getAuthorList().add(author);
                }

                return book;
            } else {
                return null;
            }
        }
    }

    public Author getAuthor(int id) throws SQLException {
        String querySQL = "Select * from authors " +
                "where authorID = ?";

        String sql = "Select t.isbn, t.title, t.editionNumber, t.copyright " +
                "from titles t join authorisbn ai " +
                "ON t.isbn = ai.isbn " +
                "JOIN authors a " +
                "ON ai.authorID = a.authorID " +
                "where a.authorID = ?";

        try (Connection conn = DBConnection.initDatabase();
             PreparedStatement pstmt = conn.prepareStatement(querySQL);
             PreparedStatement pstmt2 = conn.prepareStatement(sql)){

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Author author = new Author(rs.getInt("authorID"), rs.getString("firstName"),
                        rs.getString("lastName"));

                pstmt2.setInt(1, author.getAuthorID());
                ResultSet rsBooks = pstmt2.executeQuery();

                while (rsBooks.next()) {
                    Book book = new Book(rsBooks.getString("isbn"), rsBooks.getString("title"),
                            rsBooks.getInt("editionNumber"), rsBooks.getString("copyright"));
                    author.getBookList().add(book);
                }

                return author;
            } else {
                return null;
            }
        }
    }

    public boolean checkForDuplicateBook(String isbn) throws SQLException {
        String querySQL = "Select * from titles " +
                "where isbn = ?";

        try (Connection conn = DBConnection.initDatabase();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean checkForDuplicateAuthor(int authorID) throws SQLException {
//        String querySQL = "Select * from authors " +
//                "where firstName = ? and lastName = ?";
        String querySQL = "Select * from authors " +
                "where authorID = ?";

        try (Connection conn = DBConnection.initDatabase();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setInt(1, authorID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Book addBook(Book book) throws SQLException {
        if(!checkForDuplicateBook(book.getIsbn())) {
            String SQLBooks = "INSERT into titles (isbn, title, editionNumber, copyright)" +
                    "Values (?, ?, ?, ?)";

            try (Connection conn = DBConnection.initDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(SQLBooks);) {
                pstmt.setString(1, book.getIsbn());
                pstmt.setString(2, book.getTitle());
                pstmt.setInt(3, book.getEditionNumber());
                pstmt.setString(4, book.getCopyright());
                pstmt.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return book;
        } else {
            Book noBook = new Book("Error: Book could not be added", "", 0, "");
            return noBook;
        }
    }

    public Author addAuthor(Author author) throws SQLException {
        if(!checkForDuplicateAuthor(author.getAuthorID())){
            String SQLAuthors = "INSERT into authors (authorID, firstName, lastName)" +
                    "Values (?, ?, ?)";
            try (Connection conn = DBConnection.initDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(SQLAuthors);) {
                pstmt.setInt(1, author.getAuthorID());
                pstmt.setString(2, author.getFirstName());
                pstmt.setString(3, author.getLastName());
                pstmt.execute();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return author;
        } else {
            return null;
        }
    }

    public AuthorISBN addAuthorISBN(AuthorISBN authorISBN) throws SQLException {
        String SQLAuthorISBN = "INSERT into authorISBN (authorID, isbn)" +
                "Values (?, ?)";

        if(checkForDuplicateAuthor(authorISBN.getAuthorID()) && checkForDuplicateBook(authorISBN.getIsbn())) {
            try (Connection conn = DBConnection.initDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(SQLAuthorISBN);) {
                pstmt.setInt(1, authorISBN.getAuthorID());
                pstmt.setString(2, authorISBN.getIsbn());
                pstmt.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return authorISBN;
        } else{
               return null;
        }
    }

    public Book updateBook(Book book) throws SQLException {
        // check to see if the book exists
        if(checkForDuplicateBook(book.getIsbn())) {
            String sql = "UPDATE titles " +
                    "SET title = ?, editionNumber = ?, copyright = ? " +
                    "WHERE isbn = ?";

            try (Connection conn = DBConnection.initDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
//                pstmt.setString(1, book.getIsbn());
                pstmt.setString(1, book.getTitle());
                pstmt.setInt(2, book.getEditionNumber());
                pstmt.setString(3, book.getCopyright());
                pstmt.setString(4, book.getIsbn());
                pstmt.execute();
            } catch (SQLException throwables) {
                System.err.println("Seems like we're throwing an error at line 206");
                throwables.printStackTrace();
            }

            return book;
        } else {
            Book noBook = new Book("Error: Book could not be updated", "", 0, "");
            return noBook;
        }
    }

    public Author updateAuthor(Author author) throws SQLException {
        // insure that author exists
        if(checkForDuplicateAuthor(author.getAuthorID())){
            String sql = "UPDATE authors " +
                    "SET firstName = ?, lastName = ? " +
                    "WHERE authorID = ?";

            try (Connection conn = DBConnection.initDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, author.getFirstName());
                pstmt.setString(2, author.getLastName());
                pstmt.setInt(3, author.getAuthorID());
                pstmt.execute();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return author;
        } else {
            Author noAuthor = new Author(0, "Error: Book could not be updated", "");
            return noAuthor;
        }
    }

    public void deleteAuthorISBN(String isbn) throws SQLException {
        String SQLAuthorISBN = "DELETE from authorisbn " +
                "WHERE isbn = ?";

        try (Connection conn = DBConnection.initDatabase();
             PreparedStatement pstmt = conn.prepareStatement(SQLAuthorISBN);) {
            pstmt.setString(1, isbn);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteAuthorISBN(int authorID) throws SQLException {
        String SQLAuthorISBN = "DELETE from authorisbn " +
                "WHERE authorID = ?";

        try (Connection conn = DBConnection.initDatabase();
             PreparedStatement pstmt = conn.prepareStatement(SQLAuthorISBN);) {
            pstmt.setInt(1, authorID);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String deleteBook(String isbn) throws SQLException {
        // check to see if the book exists
        if(checkForDuplicateBook(isbn)) {
            deleteAuthorISBN(isbn);
            String sql = "DELETE FROM titles " +
                    "WHERE isbn = ?";

            try (Connection conn = DBConnection.initDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, isbn);
                pstmt.execute();
            } catch (SQLException throwables) {
                System.err.println("Seems like we're throwing an error at line 206");
                throwables.printStackTrace();
            }

            return isbn + " has been deleted.";
        } else {
            return "Error: " + isbn + " does not exist.";
        }
    }

    public String deleteAuthor(int authorID) throws SQLException {
        // check to see that author exists
        if(checkForDuplicateAuthor(authorID)){
            deleteAuthorISBN(authorID);
            String sql = "DELETE from authors " +
                    "WHERE authorID = ?";

            try (Connection conn = DBConnection.initDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setInt(1, authorID);
                pstmt.execute();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return authorID + " has been deleted.";
        } else {
            return "Error: " + authorID + " does not exist.";
        }
    }
}
