public class Main {
    public static void main(String[] args) {
        // Read data from bok.txt and add book objects from those to ArrayList

        Program program = new Program();

        System.out.println("Welcome to the library!" + "\n");

        program.addBooksFromFile();
        program.printMenu();
    }
}
