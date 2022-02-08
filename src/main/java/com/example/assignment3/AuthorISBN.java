package com.example.assignment3;

import java.util.ArrayList;
import java.util.List;

public class AuthorISBN {
    private int authorID;
    private String isbn;

    public AuthorISBN(){
        super();
    };

    public AuthorISBN(int authorID, String isbn){
        this.authorID = authorID;
        this.isbn = isbn;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
