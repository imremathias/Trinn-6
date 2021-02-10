import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Read data from bok.txt and add book objects from those to ArrayList

        System.out.println("Welcome to the library!" + "\n");
        printChoices();
    }

    private static void printChoices() {
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

    private static void checkUserInput() {
        Scanner keyInput = new Scanner(System.in);

        switch (keyInput.nextLine()) {
            case "1":
                System.out.println("Overview:");
                System.out.println("Press 1 to move on, or '0' to end program");
                printChoicesOrTerminate();
        }

    }

    private static void printChoicesOrTerminate() {
        Scanner input = new Scanner(System.in);
        switch (input.nextLine()) {
            case "1":
                System.out.println("You pressed 1, moving on");
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

}
