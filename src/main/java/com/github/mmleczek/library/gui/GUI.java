package com.github.mmleczek.library.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import com.github.mmleczek.library.auth.Authenticator;
import com.github.mmleczek.library.db.BookRepo;
import com.github.mmleczek.library.model.Book;

public class GUI {
    private final Authenticator auth;
    private final Scanner sc = new Scanner(System.in);
    private final List<GUIOption> mainMenuOptions = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public GUI (Authenticator auth) {
        this.auth = auth;
        mainMenuOptions.add(new GUIOption(999, "Add book to library", "add_book"));
        mainMenuOptions.add(new GUIOption(0, "Borrow book", "borrow_book"));
        mainMenuOptions.add(new GUIOption(0, "Search for book by title", "search_by_title"));
        mainMenuOptions.add(new GUIOption(0, "Search for book by author", "search_by_author"));
        mainMenuOptions.add(new GUIOption(0, "Search for book by ISBN", "search_by_isbn"));
        mainMenuOptions.add(new GUIOption(0, "List all books", "list_all_books"));
        mainMenuOptions.add(new GUIOption(999, "List all borrowed books", "list_borrowed_books"));
        mainMenuOptions.add(new GUIOption(999, "List all overdue books", "list_overdue_books"));
        mainMenuOptions.add(new GUIOption(0, "Exit", "exit"));
    }

    public void welcomeText() {
        System.out.println("-----------------------------------\n" +
                           "|             Library             |\n" +
                           "-----------------------------------");
    }

    public boolean loginIntoApp() {
        System.out.println("Enter login:");
        String name = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();

        return auth.authenticate(name, password);
    }

    public String getSearchInput(String title) {
        System.out.println(title);
        return sc.nextLine();
    }

    public void addBook(BookRepo bookRepo) {
        System.out.println("Adding book to library:");
        System.out.println("ISBN: ");
        String isbn = sc.nextLine();
        System.out.println("Title: ");
        String title = sc.nextLine();
        System.out.println("Author: ");
        String author = sc.nextLine();
        bookRepo.addBook(isbn, title, author);
        System.out.println("Successfully added book!");
    }

    public void borrowBook(BookRepo bookRepo) {
        System.out.println("Borrowing book from library:");
        System.out.println("Book ID:");
        String idStr = sc.nextLine();
        int bookId;
        try {
            bookId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("Wrong book ID!");
            return;
        }

        System.out.println("Borrower's First Name:");
        String firstname = sc.nextLine();
        System.out.println("Borrower's Last Name:");
        String lastname = sc.nextLine();

        int status = bookRepo.borrowBook(bookId, firstname, lastname);
        switch(status) {
            case 0:
                System.out.println("Book successfully borrowed to: " + firstname + " " + lastname);
                break;
            case 1:
                System.out.println("Wrong book ID!");
                break;
            case 2:
                System.out.println("You cannot borrow already borrowed book!");
                break;
        }
    }

    public void searchForBooks(BookRepo bookRepo, int type) {
        String searchInput;
        List<Book> books = new ArrayList<>();

        switch(type) {
            case 0:
                searchInput = this.getSearchInput("Search for books by title: ");
                books = bookRepo.findBookByTitle(searchInput);
                break;
            case 1:
                searchInput = this.getSearchInput("Search for books by author: ");
                books = bookRepo.findBookByAuthor(searchInput);
                break;
            case 2:
                searchInput = this.getSearchInput("Search for books by ISBN: ");
                books = bookRepo.findBookByISBN(searchInput);
                break;
        }

       this.printBooks(books);
    }

    public void printBooks(Collection<Book> books) {
        for (Book book : books) {
            if (auth.authUser.getAccessLevel() >= 999 && book.isBorrowed()) { // details are for admins only
                System.out.println(book.toString() +
                        " BORROWER'S NAME: " + book.getBorrowersFirstName() +
                        " BORROWER'S LASTNAME: " + book.getBorrowersLastName() +
                        " OVERDUE: " +
                        (book.isOverdue() ? "YES" : "NO") +
                        " BORROWED ON: " + book.getBorrowStartTime().format(formatter) +
                        " BORROW END TIME: " + book.getBorrowEndTime().format(formatter));
            } else {
                System.out.println(book.toString());
            }
        }
    }

    public String mainMenu() {
        if (this.auth.authUser == null) return "user_not_logged_in";
        List<GUIOption> filteredList = mainMenuOptions.stream()
                .filter(option -> option.getAccessLevel() <= this.auth.authUser.getAccessLevel())
                .toList();

        System.out.println("Main menu:");
        for (int i = 0; i < filteredList.size(); i++) {
            GUIOption option = filteredList.get(i);
            System.out.println((i + 1) + ". " + option.getLabel());
        }

        String optionIndexStr = sc.nextLine();

        try {
            int optionIndex = Integer.parseInt(optionIndexStr);
            GUIOption option = filteredList.get(optionIndex - 1);
            if (option == null) return "error";
            return option.getAction();
        } catch (NumberFormatException e) {
            return "wrong_user_input";
        }
    }
}
