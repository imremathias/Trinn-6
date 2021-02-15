import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    public ArrayList<Book> bookArrayList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("Press 1: Get overview of books currently in the library");
        System.out.println("Press 2: Add book to the library");
        System.out.println("Press 3: Change book metadata");
        System.out.println("Press 4: Show books by genre");
        System.out.println("Press 5: Show books by author");
        System.out.println("Press 6: Find books from their ISBN");
        System.out.println("Press 7: Remove book");
        System.out.println("Press 0: End program" + "\n");
        System.out.println("Write a number followed by enter/return." + "\n");

        switch (checkUserInput()) {
            case -1 -> printMenu();
            case 1 -> printBooksFromArrayList();
            case 2 -> addBookToLibrary();
            case 3 -> startBookUpdate();
            case 4 -> printBooksByGenre();
            case 5 -> startPrintBooksByAuthor();
            case 6 -> startFindBookByISBN();
            case 7 -> startRemoveBook();
            case 0 -> {
                System.out.println("\nSaving library state...");
                System.out.println("\nHave a nice day");
            }
        }

        try {
            writeChangesToFile();
        } catch (IOException ioException) {
            System.out.println("File not found. Changes to the library will not be saved.");
        }
    }


    public void addBooksFromFile() {

        try {
            File bokTxt = new File("./bok.txt");
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

    private int checkUserInput() {
        int returnNumber = -1;
        String input = scanner.nextLine();

        try {
            if (Integer.parseInt(input) <= 7) {
                returnNumber = Integer.parseInt(input);
            } else {
                System.out.println("\nNot a valid number\n");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Not a number");
        }
        return returnNumber;
    }

    // CASE 1

    private void printBooksFromArrayList() {
        System.out.println();

        for (Book value : bookArrayList) {
            value.getBookInfo();
        }
        toMenuOrCloseProgram();
    }

    // CASE 2

    private void addBookToLibrary() {
        System.out.println();
        System.out.println("To add a book to the library you need these fields:" + "\n");
        System.out.println("ISBN, Title, Author, Number of pages, Genre" + "\n");

        try {
            System.out.println("What is the ISBN?");
            long ISBN = Long.parseLong(scanner.nextLine());
            System.out.println("\nWhat is the title of the book?");
            String bookTitle = scanner.nextLine();
            System.out.println("\nWhat is the name of the author?");
            String authorName = scanner.nextLine();
            System.out.println("\nHow many pages?");
            short pageCount = Short.parseShort(scanner.nextLine());
            System.out.println("\nWhich genre (Crime, Action, Fantasy, Classic, Other)?");
            Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());

            bookArrayList.add(new Book(ISBN, bookTitle, authorName, pageCount, genre));
        } catch (NumberFormatException numberFormatException) {
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

    private void startBookUpdate() throws NumberFormatException {
        System.out.println();
        System.out.println("Which book would you like to update? Specify either by ISBN or title.\n");
        System.out.println("1: ISBN \n2: Title \n0: Go back\n");

        int userInput = checkUserInput();

        int foundIndex = findBook(userInput);
        if (foundIndex >= 0) {
            updateBook(foundIndex);
        } else {
            System.out.println("Book not found in library");
            startBookUpdate();
        }
    }

    // CASE 4

    private void printBooksByGenre() {
        System.out.println("\nFor which genre would you like to display its' books?");
        System.out.println("(Crime, Action, Fantasy, Classic, Other)?\n");

        try {
            Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());
            System.out.println();

            for (Book book : bookArrayList) {
                if (genre == book.getGenre()) {
                    book.getBookInfo();
                }
            }
            toMenuOrCloseProgram();
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Not a genre");
            printBooksByGenre();
        }
    }

    // CASE 5

    private void startPrintBooksByAuthor() {
        System.out.println("\nWhich author's books would you like to display?");
        System.out.println("Specify either the authors name, or write \"1\" for a list of all authors in the library\n");

        String userString = scanner.nextLine();

        if ("1".equals(userString)) {
            System.out.println();
            printAllAuthors();
            System.out.println("\nWrite the name of the author\n");
            userString = scanner.nextLine();
        }
        if (printBooksByAuthor(userString).equals("Failed")) {
            System.out.println("Author not found");
        }
        toMenuOrCloseProgram();
    }

        private String printBooksByAuthor(String userString) {
            System.out.println();
            String status = "Failed";
            for (Book book : bookArrayList) {
                if (userString.equalsIgnoreCase(book.getAuthorName())) {
                    book.getBookInfo();
                    status = "Success";
                }
            } return status;
        }

    /// CASE 7

    private void startRemoveBook() {
        System.out.println("\nWhich book would you like to delete? Specify either by ISBN or title.\n");
        System.out.println("1: ISBN \n2: Title \n0: Go back\n");

        int userInput = checkUserInput();
        int foundIndex = findBook(userInput);

        if (foundIndex >= 0) {
            deleteBook(foundIndex);
            System.out.println("Book deleted");
        } else {
            System.out.println("Book not found in library");
            startRemoveBook();
        } toMenuOrCloseProgram();
    }

    private void deleteBook(int index) {
        bookArrayList.remove(index);
    }

    private void startFindBookByISBN() {
        int bookIndex = findBook(1);
        if (bookIndex != -1) {
            System.out.println();
            bookArrayList.get(bookIndex).getBookInfo();
        } else {
            System.out.println("Book not found");
        } toMenuOrCloseProgram();
    }

    // WRITE-CHANGES-TO-FILE

    public void writeChangesToFile() throws IOException {
        File bokTxt = new File("./bok.txt");
        FileWriter fileWriter = new FileWriter(bokTxt);

        for (Book book : bookArrayList) {
            fileWriter.write(book.getISBN() + "\n"
                    + book.getBookTitle() + "\n"
                    + book.getAuthorName() + "\n"
                    + book.getPageCount() + "\n"
                    + book.getGenre() + "\n"
                    + "---\n");
        }
        fileWriter.close();
    }

    // OTHER METHODS

    private int findBook(int choice) {
        int bookIndex = -1;

        switch (choice) {
            case 1 -> {
                System.out.println("\nWrite the ISBN\n");
                bookIndex = findBookByISBN(Long.parseLong(scanner.nextLine()));
                    if (bookIndex == -1) {
                        System.out.println("\nISBN not found in library\n");
                        toMenuOrCloseProgram();
                    }
            }
            case 2 -> {
                System.out.println();
                System.out.println("Write the title");
                bookIndex = findBookByTitle(String.valueOf(scanner.nextLine()));
            }
            case 0 -> {
                System.out.println();
                System.out.println("Menu loading...");
                printMenu();
            }
            default -> {
                System.out.println();
                System.out.println("Not a valid number");
                startBookUpdate();
            }
        }
        return bookIndex;
    }

    private int findBookByISBN(long ISBN) {
        int bookArrayListIndex = -1;

        for (Book book : bookArrayList) {
            if (book.getISBN() == ISBN) {
                bookArrayListIndex = bookArrayList.indexOf(book);
            }
        }
        return bookArrayListIndex;
    }

    private int findBookByTitle(String userTitle) {
        int bookArrayListIndex = -1;

        for (Book book : bookArrayList) {
            if (userTitle.equalsIgnoreCase(book.getBookTitle())) {
                bookArrayListIndex = bookArrayList.indexOf(book);
                break;
            }
        }

        return bookArrayListIndex;
    }

    private void updateBook(int bookIndex) {
        System.out.println("Here is the current metadata for the book:\n");
        bookArrayList.get(bookIndex).getBookInfo();

        checkIfThenSetISBN(bookIndex);
        checkIfThenSetTitle(bookIndex);
        checkIfThenSetAuthor(bookIndex);
        checkIfThenSetPages(bookIndex);
        checkIfThenSetGenre(bookIndex);

        System.out.println("Book updated");
        toMenuOrCloseProgram();
    }

    // CHECK- & SET-METHODS

    private void checkIfThenSetISBN(int bookIndex) {
        System.out.println("Set new ISBN?");
        System.out.println("Write new ISBN or \"0\" to move on with current ISBN");

        try {
            String consoleInput = scanner.nextLine();
            if (Long.parseLong(consoleInput) > 0) {
                bookArrayList.get(bookIndex).setISBN(Long.parseLong(consoleInput));
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Not a valid number");
            updateBook(bookIndex);
        }
    }

    private void checkIfThenSetTitle(int bookIndex) {
        System.out.println("Set new title?");
        System.out.println("Write new title or \"0\" to move on with current title");
        String consoleInput = scanner.nextLine();

        if (!consoleInput.equals("0")) {
            bookArrayList.get(bookIndex).setBookTitle(consoleInput);
        }
    }

    private void checkIfThenSetAuthor(int bookIndex) {
        System.out.println("Set new author?");
        System.out.println("Write the name of new author or \"0\" to move on");
        String consoleInput = scanner.nextLine();

        if (!consoleInput.equals("0")) {
            bookArrayList.get(bookIndex).setAuthorName(consoleInput);
        }
    }

    private void checkIfThenSetPages(int bookIndex) {
        System.out.println("Set number of pages?");
        System.out.println("Write number of pages or \"0\" to move on");
        String consoleInput = scanner.nextLine();

        try {
            if (Integer.parseInt(consoleInput) > 0) {
                bookArrayList.get(bookIndex).setPageCount(Integer.parseInt(consoleInput));
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Not a valid number");
            checkIfThenSetGenre(bookIndex);
        }
    }

    private void checkIfThenSetGenre(int bookIndex) {
        System.out.println("Set new genre?");
        System.out.println("Write new genre or \"0\" to move on");
        String consoleInput = scanner.nextLine();

        if (!consoleInput.equals("0")) {
            for (Genre g : Genre.values()) {
                if (g.name().equals(consoleInput.toUpperCase())) {
                    bookArrayList.get(bookIndex).setGenre(Genre.valueOf(consoleInput.toUpperCase()));
                } else {
                    System.out.println("Genre is not in the registry");
                    checkIfThenSetGenre(bookIndex);
                }
            }
        }
    }

    private void printAllAuthors() {
        for (Book book: bookArrayList) {
            System.out.println(book.getAuthorName());
        }
    }

        // HELPER METHOD

        private void toMenuOrCloseProgram() {
            System.out.println("Press 1 to move on, or '0' to end program");
            System.out.println();
            String userInput = scanner.nextLine();
            System.out.println();

            switch (userInput) {
                default:
                    System.out.println("Write a valid number");
                    toMenuOrCloseProgram();
                case "0":
                    break;
                case "1":
                    printMenu();
                    break;
            }
        }
    }
