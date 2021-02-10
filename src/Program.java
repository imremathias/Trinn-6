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
        System.out.println("Press ESC: End program" + "\n");
        System.out.println("Write a number followed by enter/return." + "\n");

        checkUserInput();
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

        fileScanner.close();
        }

        catch (FileNotFoundException fnfe) {
            System.out.println("File not found");
        }
    }

    // HELPER METHODS

    private void checkUserInput() {
        Scanner keyInput = new Scanner(System.in);

        switch (keyInput.nextLine()) {
            case "1":
                printBooksFromArrayList();
                break;
            case "2":
                printBooksFromArrayList();
                break;
            case "3":
                printBooksFromArrayList();
                break;
            case "4":
                printBooksFromArrayList();
                break;
            case "5":
                printBooksFromArrayList();
                break;
            case "6":
                printBooksFromArrayList();
                break;
            case "7":
                printBooksFromArrayList();
                break;
            case "8":
                printBooksFromArrayList();
                break;
            default:
                System.out.println("Closing program...");
        }
        keyInput.close();
    }

    public void printBooksFromArrayList() {
        for (Book value : bookArrayList) {
            value.getBookInfo();
        }
        toMenuOrCloseProgram();
    }

    public void toMenuOrCloseProgram() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 to move on, or '0' to end program");

        switch (scanner.nextLine()) {
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
        scanner.close();
    }

    /*
    private void printChoicesOrTerminate() {
        Scanner input = new Scanner(System.in);
        switch (input.nextLine()) {
            case "1":
                Program.addBooksFromFile();
                printChoices();
                break;
            case "0":
                System.out.println("Goodbye");
                break;
            default:
                System.out.println("Write number");
                printChoicesOrTerminate();
        }
        input.close();
    }

     */
}
