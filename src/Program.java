import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    public ArrayList<Book> bookArrayList = new ArrayList<>();

    public void printMenu() {
        System.out.println("Press 1: Get overview of books currently in the library");
        System.out.println("Press 2: Add book to the library");
        System.out.println("Press 3: Change book metadata");
        System.out.println("Press 4: Find books in specified genre");
        System.out.println("Press 5: Find books from their author");
        System.out.println("Press 6: Find books from their ISBN");
        System.out.println("Press 7: Remove book");
        System.out.println("Press 8: End program" + "\n");
        System.out.println("Write a number followed by enter/return." + "\n");

        switch (checkUserInput()) {
            case 1 -> printBooksFromArrayList();
            case 2 -> addBookToLibrary();
            case 3 -> setBookFields();
            case 4 -> printBooksFromArrayList();
            case 5 -> printBooksFromArrayList();
            case 6 -> printBooksFromArrayList();
            case 7 -> printBooksFromArrayList();
            case 8 -> printBooksFromArrayList();
            case 0 -> printMenu();
        }
    }

    public void addBooksFromFile() {

        try {
            File bokTxt = new File("files/opg1/bok.txt");
            Scanner fileScanner = new Scanner(bokTxt);

            while (fileScanner.hasNextLine()) {
                long ISBN = Long.parseLong(fileScanner.nextLine());
                String bookTitle = fileScanner.nextLine();
                String authorName = fileScanner.nextLine();
                short pageCount = Short.parseShort(fileScanner.nextLine());
                Genre genre = Genre.valueOf(fileScanner.nextLine().toUpperCase());

                bookArrayList.add(new Book(ISBN, bookTitle, authorName, pageCount, genre));
                fileScanner.nextLine();
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found");
        }
    }

    // HELPER METHODS

    private int checkUserInput() {
        Scanner keyInput = new Scanner(System.in);
        int returnNumber = 0;
        String input = keyInput.nextLine();

        try {
            if (Integer.parseInt(input) <= 8) {
                returnNumber = Integer.parseInt(input);
            } else {
                System.out.println("Not a valid number");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Not a number");
        }

        return returnNumber;
    }

    // CASE 1

    private void printBooksFromArrayList() {
        for (Book value : bookArrayList) {
            value.getBookInfo();
        } toMenuOrCloseProgram();
    }

    // CASE 2

    private void addBookToLibrary() {
        System.out.println("To add a book to the library you need these fields:" + "\n");
        System.out.println("ISBN, Title, Author, Number of pages, Genre" + "\n");

        Scanner bookInputScanner = new Scanner(System.in);

        try {
            System.out.println("What is the ISBN?");
                long ISBN = Long.parseLong(bookInputScanner.nextLine());
            System.out.println("What is the title of the book?");
                String bookTitle = bookInputScanner.nextLine();
            System.out.println("What is the name of the author?");
                String authorName = bookInputScanner.nextLine();
            System.out.println("How many pages?");
                short pageCount = Short.parseShort(bookInputScanner.nextLine());
            System.out.println("Which genre (Crime, Action, Fantasy, Classic, Other)?");
                Genre genre = Genre.valueOf(bookInputScanner.nextLine().toUpperCase());

            bookArrayList.add(new Book(ISBN, bookTitle, authorName, pageCount, genre));
        }

            catch (NumberFormatException numberFormatException) {
                System.out.println("Not a number");
                addBookToLibrary();
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("Not a genre");
                addBookToLibrary();
            }

        System.out.println("Book successfully added to the library");
        toMenuOrCloseProgram();
    }

    // CASE 3

    private void setBookFields() {
    }

    // HELPER METHOD

    private void toMenuOrCloseProgram() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 to move on, or '0' to end program");

        String userInput = scanner.nextLine();

        switch (userInput) {
            default:
                System.out.println("Write a valid number");
                System.out.println("Press 1 to move on, or '0' to end program");
            case "0":
                System.out.println("Goodbye");
                break;
            case "1":
                printMenu();
                break;
        }
    }
}
