import java.io.*;
import java.util.*;
class Book {
    private String id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }
    public void setIssued(boolean issued) { isIssued = issued; }

    @Override
    public String toString() {
        return String.format("Book[ID=%s, Title=%s, Author=%s, Status=%s]",
                id, title, author, isIssued ? "Issued" : "Available");
    }
}
class Member {
    private String memberId;
    private String name;
    private List<String> borrowedBookIds;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBookIds = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }

    public void borrowBook(String bookId) {
        borrowedBookIds.add(bookId);
    }

    public void returnBook(String bookId) {
        borrowedBookIds.remove(bookId);
    }

    public List<String> getBorrowedBookIds() {
        return borrowedBookIds;
    }

    @Override
    public String toString() {
        return "Member[ID=" + memberId + ", Name=" + name + ", Borrowed=" + borrowedBookIds + "]";
    }
}
class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}

class InvalidReturnException extends Exception {
    public InvalidReturnException(String message) {
        super(message);
    }
}
class Library {
    private HashMap<String, Book> inventory = new HashMap<>();
    private HashMap<String, Member> members = new HashMap<>();
    private final String logFile = "library_log.txt";

    public void addBook(Book book) {
        inventory.put(book.getId(), book);
        logOperation("Added Book: " + book);
    }

    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
        logOperation("Added Member: " + member);
    }

    public void issueBook(String bookId, String memberId) throws BookNotAvailableException {
        Book book = inventory.get(bookId);
        Member member = members.get(memberId);

        if (book == null) throw new BookNotAvailableException("Book not found!");
        if (book.isIssued()) throw new BookNotAvailableException("Book already issued!");
        if (member == null) throw new BookNotAvailableException("Member not found!");

        book.setIssued(true);
        member.borrowBook(bookId);
        logOperation("Issued Book: " + bookId + " to Member: " + memberId);
        System.out.println(" Book issued successfully!");
    }

    public void returnBook(String bookId, String memberId, int daysLate) throws InvalidReturnException {
        Book book = inventory.get(bookId);
        Member member = members.get(memberId);

        if (book == null || member == null)
            throw new InvalidReturnException("Invalid Book ID or Member ID!");
        if (!book.isIssued() || !member.getBorrowedBookIds().contains(bookId))
            throw new InvalidReturnException("Book was not borrowed by this member!");

        book.setIssued(false);
        member.returnBook(bookId);

        int lateFee = daysLate * 2; // ₹2 per day late
        logOperation("Returned Book: " + bookId + " by Member: " + memberId + " | Late fee: ₹" + lateFee);
        System.out.println("Book returned successfully! Late Fee: ₹" + lateFee);
    }

    public void showInventory() {
        System.out.println("\n===== Library Inventory =====");
        for (Book b : inventory.values()) {
            System.out.println(b);
        }
        System.out.println("=============================\n");
    }

    public void logOperation(String message) {
        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(new Date() + " - " + message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }
}

// ---------------------- Main Class ----------------------
public class LibrarySystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n===== LIBRARY MENU =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Show Inventory");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Book ID: ");
                        String id = sc.nextLine();
                        System.out.print("Enter Title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter Author: ");
                        String author = sc.nextLine();
                        library.addBook(new Book(id, title, author));
                        System.out.println("✅ Book added successfully!");
                        break;

                    case 2:
                        System.out.print("Enter Member ID: ");
                        String memberId = sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        library.addMember(new Member(memberId, name));
                        System.out.println("✅ Member added successfully!");
                        break;

                    case 3:
                        System.out.print("Enter Book ID to Issue: ");
                        String issueBookId = sc.nextLine();
                        System.out.print("Enter Member ID: ");
                        String issueMemberId = sc.nextLine();
                        library.issueBook(issueBookId, issueMemberId);
                        break;

                    case 4:
                        System.out.print("Enter Book ID to Return: ");
                        String returnBookId = sc.nextLine();
                        System.out.print("Enter Member ID: ");
                        String returnMemberId = sc.nextLine();
                        System.out.print("Enter Days Late (0 if none): ");
                        int daysLate = sc.nextInt();
                        sc.nextLine();
                        library.returnBook(returnBookId, returnMemberId, daysLate);
                        break;

                    case 5:
                        library.showInventory();
                        break;

                    case 6:
                        System.out.println("👋 Exiting Library System. Goodbye!");
                        return;

                    default:
                        System.out.println("❌ Invalid choice! Try again.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
    }
}
