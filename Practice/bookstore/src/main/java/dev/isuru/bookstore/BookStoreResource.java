package dev.isuru.bookstore;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/books")
public class BookStoreResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllBooks() {
        return "<p>All Books</p>";
    }
}
