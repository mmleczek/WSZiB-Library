package pl.edu.wszib.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pl.edu.wszib.model.Book;

public class BookRepo {
    private final List<Book> books = new ArrayList<>();

    public BookRepo() {
        this.books.add(new Book(0, "978-0-306-40615-7", "Clean Code", "Robert C. Martin"));
        this.books.add(new Book(1, "978-0-7356-6745-7", "Effective Java", "Joshua Bloch"));
        this.books.add(new Book(2, "978-0-321-35668-0", "The Pragmatic Programmer", "Andrew Hunt, David Thomas"));
        this.books.add(new Book(3, "978-1-4493-8504-9", "Java Concurrency in Practice", "Brian Goetz"));
        this.books.add(new Book(4, "978-83-10-12798-8", "Pan Tadeusz", "Adam Mickiewicz"));
        this.books.add(new Book(5, "978-1-59059-725-4", "Head First Design Patterns", "Eric Freeman, Elisabeth Robson"));
        this.books.add(new Book(6, "978-0-321-55378-3", "Refactoring: Improving the Design of Existing Code", "Martin Fowler"));
        this.books.add(new Book(7, "978-0-596-52068-7", "Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides"));
        this.books.add(new Book(8, "978-0-201-63361-0", "Code Complete", "Steve McConnell"));
        this.books.add(new Book(9, "978-0-134-00927-0", "The Mythical Man-Month", "Frederick P. Brooks Jr."));
        this.books.add(new Book(10, "978-83-240-2345-2", "Ferdydurke", "Witold Gombrowicz"));
        this.books.add(new Book(11, "978-0-13-235088-4", "Clean Architecture", "Robert C. Martin"));
        this.books.add(new Book(12, "978-0-201-89551-1", "Domain-Driven Design", "Eric Evans"));
        this.books.add(new Book(13, "978-83-7425-176-3", "Krzyżacy", "Henryk Sienkiewicz"));
        this.books.add(new Book(14, "978-0-596-00381-3", "Pragmatic Unit Testing in Java with JUnit", "Andy Hunt, Dave Thomas"));
        this.books.add(new Book(15, "978-0-321-34970-8", "Effective Python", "Brett Slatkin"));
        this.books.add(new Book(16, "978-1-491-92467-0", "Java 8 in Action", "Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft"));
        this.books.add(new Book(17, "978-0-13-468599-1", "Modern Java in Action", "Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft"));
        this.books.add(new Book(18, "978-83-10-07678-4", "Lalka", "Bolesław Prus"));
    }

    public void addBook(String isbn, String title, String author) {
        Book lastAddedBook = this.books.stream()
                .max(Comparator.comparingInt(Book::getId))
                .orElse(null);

        int newId = 1;
        if (lastAddedBook != null) {
            newId = lastAddedBook.getId() + 1;
        }

        this.books.add(new Book(newId, isbn, title, author));
    }

    public boolean rentBook(int id, String rentFirstName, String rentLastName) {
        Book book = this.getBookById(id);
        if (book != null && !book.isRent()) {
            book.setRent(LocalDateTime.now(), rentFirstName, rentLastName);
            return true;
        }

        return false;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public List<Book> getRentedBooks() {
        return this.books.stream()
                .filter(book -> book.isRent())
                .collect(Collectors.toList());
    }

    public List<Book> getOverdueRentBooks() {
        return this.books.stream()
                .filter(book -> book.isRentOverdue())
                .collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        Optional<Book> foundBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
        return foundBook.orElse(null);
    }

    public List<Book> findBookByISBN(String searchInput) {
        return this.books.stream()
                .filter(book -> book.containsISBN(searchInput))
                .collect(Collectors.toList());
    }

    public List<Book> findBookByTitle(String searchInput) {
        return this.books.stream()
                .filter(book -> book.containsTitle(searchInput))
                .collect(Collectors.toList());
    }

    public List<Book> findBookByAuthor(String searchInput) {
        return this.books.stream()
                .filter(book -> book.containsAuthor(searchInput))
                .collect(Collectors.toList());
    }
}
