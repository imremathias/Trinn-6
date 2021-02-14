import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    public ArrayList<Book> bookArrayList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("Press 1: Get overview of books currently in the library");
        System.out.println("Press 2: Add book to the library");
        System.out.println("Press 3: Change book metadata");
        System.out.println("Press 4: See books in specified genre");
        System.out.println("Press 5: Find books from their author");
        System.out.println("Press 6: Find books from their ISBN");
        System.out.println("Press 7: Remove book");
        System.out.println("Press 8: End program" + "\n");
        System.out.println("Write a number followed by enter/return." + "\n");

        switch (checkUserInput()) {
            case 1 -> printBooksFromArrayList();
            case 2 -> addBookToLibrary();
            case 3 -> startBookUpdate();
            case 4 -> printBooksByGenre();
            case 5 -> printBooksFromArrayList();
            case 6 -> printBooksFromArrayList();
            case 7 -> printBooksFromArrayList();
            case 8 -> printBooksFromArrayList();
            case 0 -> printMenu();
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
        int returnNumber = 0;
        String input = scanner.nextLine();

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
            System.out.println("What is the title of the book?");
            String bookTitle = scanner.nextLine();
            System.out.println("What is the name of the author?");
            String authorName = scanner.nextLine();
            System.out.println("How many pages?");
            short pageCount = Short.parseShort(scanner.nextLine());
            System.out.println("Which genre (Crime, Action, Fantasy, Classic, Other)?");
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
        System.out.println("Which book would you like to update? Specify either by ISBN or title");
        System.out.println("1: ISBN \n2: Title \n0: Go back");

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
        System.out.println("For which genre would you like to display its' books?");
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

    // OTHER METHODS

    private int findBook(int choice) {
        int bookIndex = -1;

        switch (choice) {
            case 1:
                System.out.println("Write the ISBN");
                bookIndex = findBookByISBN(Long.parseLong(scanner.nextLine()));
                break;
            case 2:
                System.out.println("Write the title");
                bookIndex = findBookByTitle(String.valueOf(scanner.nextLine()));
                break;
            case 0:
                System.out.println("Menu loading...");
                printMenu();
                break;
            default:
                System.out.println("Not a valid number");
                startBookUpdate();
        } return bookIndex;
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

        // HELPER METHOD

        private void toMenuOrCloseProgram() {
            System.out.println("Press 1 to move on, or '0' to end program");
            System.out.println();
            String userInput = scanner.nextLine();
            System.out.println();

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
