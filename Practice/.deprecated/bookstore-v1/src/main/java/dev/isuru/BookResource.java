package dev.isuru;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.ArrayList;

@Path("/books")
public class BookResource {

    public static class Book {
        public int id;
        public String title;
        public String author;

        public Book() {}  // Needed for deserialization

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }
    }

    private final List<Book> bookList;
    private int nextId;

    public BookResource() {
        this.bookList = new ArrayList<>();
        this.bookList.add(new Book(1, "The Hobbit", "John Smith"));
        this.nextId = 2; // Start from 2 because 1 is already used
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok(bookList).build();
    }

    @GET
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("bookId") int id) {
        Book result = bookList.stream()
                .filter(book -> id == book.id)
                .findAny()
                .orElse(null);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        book.id = nextId++;  // Auto-increment ID
        bookList.add(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }
}
