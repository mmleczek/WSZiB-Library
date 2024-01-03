package pl.edu.wszib.model;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Book {
    // There might be multiple books from the same author, publisher etc.
    // so we want to somehow keep a track of them, that's why there is a
    // unique id for each of them.
    private int id;
    private String isbn;
    private String title;
    private String author;

    private boolean rent = false;
    private LocalDateTime rentStartTime;
    private String rentFirstName;
    private String rentLastName;

    public Book(int id, String isbn, String title, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book(int id, String isbn, String title, String author, boolean rent, LocalDateTime rentStartTime, String rentFirstName, String rentLastName) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.rent = rent;
        this.rentStartTime = rentStartTime;
        this.rentFirstName = rentFirstName;
        this.rentLastName = rentLastName;
    }

    public void setRent(LocalDateTime rentStartTime, String rentFirstName, String rentLastName) {
        this.rent = true;
        this.rentStartTime = rentStartTime;
        this.rentFirstName = rentFirstName;
        this.rentLastName = rentLastName;
    }

    public boolean isRent() {
        return this.rent;
    }

    public boolean isRentOverdue() {
        Duration duration = Duration.between(rentStartTime, LocalDateTime.now());
        long weeksPassed = duration.toDays() / 7;
        return weeksPassed >= 2;
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

    public LocalDateTime getRentStartTime() {
        return this.rentStartTime;
    }

    public String getRentFirstName() {
        return this.rentFirstName;
    }

    public String getRentLastName() {
        return this.rentLastName;
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
        s.append(" RENT: ");
        s.append(this.isRent() ? "YES" : "NO");

        if (this.isRent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
            s.append("\nRENT START DATE: ");
            s.append(this.rentStartTime.format(formatter));
            s.append(" IS RENT OVERDUE: ");
            s.append(this.isRentOverdue() ? "YES" : "NO");
            s.append(" RENT FIRST NAME: ");
            s.append(this.getRentFirstName());
            s.append(" RENT LAST NAME: ");
            s.append(this.getRentLastName());
        }

        return s.toString();
    }
}
