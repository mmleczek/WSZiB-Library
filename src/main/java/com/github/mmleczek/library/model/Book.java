package com.github.mmleczek.library.model;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Book {
    // There might be multiple books from the same author, publisher etc.
    // so we want to somehow keep a track of them, that's why there is a
    // unique id for each of them.
    private final int id;
    private final String isbn;
    private final String title;
    private final String author;

    private boolean borrowed = false;
    private LocalDateTime borrowStartTime;
    private String borrowersFirstName;
    private String borrowersLastName;

    public Book(int id, String isbn, String title, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book(int id, String isbn, String title, String author, boolean rent, LocalDateTime borrowStartTime, String borrowersFirstName, String borrowersLastName) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowed = rent;
        this.borrowStartTime = borrowStartTime;
        this.borrowersFirstName = borrowersFirstName;
        this.borrowersLastName = borrowersLastName;
    }

    public void setRent(LocalDateTime borrowStartTime, String borrowersFirstName, String borrowersLastName) {
        this.borrowed = true;
        this.borrowStartTime = borrowStartTime;
        this.borrowersFirstName = borrowersFirstName;
        this.borrowersLastName = borrowersLastName;
    }

    public void clearRent() {
        this.borrowed = false;
        this.borrowStartTime = null;
        this.borrowersFirstName = null;
        this.borrowersLastName = null;
    }

    public boolean isBorrowed() {
        return this.borrowed;
    }

    public boolean isOverdue() {
        if (this.borrowStartTime != null) { // for not borrowed books, this will be null, that's why this check is needed.
            Duration duration = Duration.between(this.borrowStartTime, LocalDateTime.now());
            long weeksPassed = duration.toDays() / 7;
            return weeksPassed >= 2;
        } else {
            return false;
        }
    }

    public int getId() {
        return this.id;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public boolean containsTitle(String searchInput) {
        return this.title.toLowerCase().contains(searchInput.toLowerCase());
    }

    public boolean containsAuthor(String searchInput) {
        return this.author.toLowerCase().contains(searchInput.toLowerCase());
    }

    public boolean containsISBN(String searchInput) {
        return this.isbn.toLowerCase().contains(searchInput.toLowerCase());
    }

    public LocalDateTime getBorrowStartTime() {
        return this.borrowStartTime;
    }

    public String getBorrowersFirstName() {
        return this.borrowersFirstName;
    }

    public String getBorrowersLastName() {
        return this.borrowersLastName;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Book ID: ");
        s.append(this.getId());
        s.append(" ISBN: ");
        s.append(this.getIsbn());
        s.append(" TITLE: ");
        s.append(this.getTitle());
        s.append(" AUTHOR: ");
        s.append(this.getAuthor());
        s.append(" BORROWED: ");
        s.append(this.isBorrowed() ? "YES" : "NO");

        return s.toString();
    }
}
