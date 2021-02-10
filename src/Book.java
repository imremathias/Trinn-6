public class Book {
    private long ISBN;
    private String bookTitle;
    private String authorName;
    private short pageCount;
    private Genre genre;

    public Book(long ISBN, String bookTitle, String authorName, short pageCount, Genre genre) {
        this.ISBN = ISBN;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.pageCount = pageCount;
        this.genre = genre;
    }

    // START -- GETTERS & SETTERS

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public short getPageCount() {
        return pageCount;
    }

    public void setPageCount(short pageCount) {
        this.pageCount = pageCount;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    // END -- GETTERS & SETTERS

    // GET EVERY BOOK FIELD

    public void getBookInfo() {
        System.out.println(String.valueOf(ISBN));
        System.out.println(bookTitle);
        System.out.println(authorName);
        System.out.println(String.valueOf(pageCount));
        System.out.println(String.valueOf(genre));
        System.out.println();
    }
}
