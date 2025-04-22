package dev.isuru.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Book {

    @NotNull(message = "Book ID cannot be null")
    private Integer id;

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Author ID cannot be null")
    private Integer authorId;

    @NotNull(message = "ISBN cannot be null")
    private String isbn;

    @NotNull
    @Min(1600)
    @Max(65535)
    private Integer publicationYear;

    @NotNull
    @Min(0)
    private Double price;

    @NotNull
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    public Book() {}

    public Book(int id, String title, int authorId, String isbn, int publicationYear, double price, int stock) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stock = stock;
    }

    public Book(String title, int authorId, String isbn, int publicationYear, double price, int stock) {
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stock = stock;
    }

    public static void validateBook() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}