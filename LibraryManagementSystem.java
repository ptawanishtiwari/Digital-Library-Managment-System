import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String category;
    private boolean isIssued;

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issueBook() {
        isIssued = true;
    }

    public void returnBook() {
        isIssued = false;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Category: " + category + ", Issued: " + (isIssued ? "Yes" : "No");
    }
}

class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean authenticate(String userId, String password) {
        return this.userId.equals(userId) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email;
    }
}

class Library {
    private ArrayList<Book> books;
    private HashMap<String, User> users;
    private User currentUser;

    public Library() {
        books = new ArrayList<>();
        users = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully.");
    }

    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
        System.out.println("Book removed successfully.");
    }

    public void viewBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void issueBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isIssued()) {
                book.issueBook();
                System.out.println("Book issued successfully.");
                return;
            }
        }
        System.out.println("Book not available or already issued.");
    }

    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isIssued()) {
                book.returnBook();
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Invalid return request.");
    }

    public void registerUser(User user) {
        users.put(user.toString(), user);
        System.out.println("User registered successfully.");
    }

    public boolean login(String userId, String password) {
        for (User user : users.values()) {
            if (user.authenticate(userId, password)) {
                currentUser = user;
                System.out.println("Login successful. Welcome, " + currentUser.toString());
                return true;
            }
        }
        System.out.println("Invalid login credentials.");
        return false;
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println("Goodbye, " + currentUser.toString());
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
}

public class LibraryManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        System.out.println("--- Welcome to Library Management System ---");
        setupDemoData();

        while (true) {
            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void setupDemoData() {
        library.addBook(new Book("Harry Potter", "J.K. Rowling", "Fiction"));
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        library.addBook(new Book("1984", "George Orwell", "Dystopian"));

        library.registerUser(new User("user1", "pass1", "Alice", "alice@example.com"));
        library.registerUser(new User("user2", "pass2", "Bob", "bob@example.com"));
    }

    private static void adminMenu() {
        System.out.println("--- Admin Menu ---");
        while (true) {
            System.out.println("\n1. Add Book\n2. Remove Book\n3. View Books\n4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    library.addBook(new Book(title, author, category));
                    break;
                case 2:
                    System.out.print("Enter book title to remove: ");
                    title = scanner.nextLine();
                    library.removeBook(title);
                    break;
                case 3:
                    library.viewBooks();
                    break;
                case 4:
                    System.out.println("Logging out as Admin.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void userMenu() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (library.login(userId, password)) {
            while (true) {
                System.out.println("\n1. View Books\n2. Issue Book\n3. Return Book\n4. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        library.viewBooks();
                        break;
                    case 2:
                        System.out.print("Enter book title to issue: ");
                        String title = scanner.nextLine();
                        library.issueBook(title);
                        break;
                    case 3:
                        System.out.print("Enter book title to return: ");
                        title = scanner.nextLine();
                        library.returnBook(title);
                        break;
                    case 4:
                        library.logout();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
}
