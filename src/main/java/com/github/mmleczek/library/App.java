package com.github.mmleczek.library;

import com.github.mmleczek.library.auth.Authenticator;
import com.github.mmleczek.library.db.BookRepo;
import com.github.mmleczek.library.gui.GUI;

public class App {
    public static void main(String[] args) {
        Authenticator auth = new Authenticator();
        BookRepo bookRepo = new BookRepo();
        GUI gui = new GUI(auth);

        boolean loggedIn = false;
        int numOfLoginTries = 0;

        while (!loggedIn && numOfLoginTries < 3) {
            loggedIn = gui.loginIntoApp();
            numOfLoginTries++;
        }

        if (loggedIn) gui.welcomeText();

        while (loggedIn) {
            switch(gui.mainMenu()) {
                case "user_not_logged_in":
                    System.out.println("You are not logged in.");
                    System.exit(0);
                    break;
                case "add_book":
                    gui.addBook(bookRepo);
                    break;
                case "borrow_book":
                    gui.borrowBook(bookRepo);
                    break;
                case "search_by_title":
                    gui.searchForBooks(bookRepo, 0);
                    break;
                case "search_by_author":
                    gui.searchForBooks(bookRepo, 1);
                    break;
                case "search_by_isbn":
                    gui.searchForBooks(bookRepo, 2);
                    break;
                case "list_all_books":
                    System.out.println("All books in library:");
                    gui.printBooks(bookRepo.getBooks());
                    break;
                case "list_borrowed_books":
                    gui.printBooks(bookRepo.getBorrowedBooks());
                    break;
                case "list_overdue_books":
                    gui.printBooks(bookRepo.getOverdueBooks());
                    break;
                case "exit":
                    System.out.println("Exiting. Bye!");
                    System.exit(0);
                    break;
            }
            try {
                Thread.sleep(3000); // this is to make console output more readable
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
