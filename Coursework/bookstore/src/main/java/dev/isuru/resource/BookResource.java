package dev.isuru.resource;

import dev.isuru.dao.AuthorDAO;
import dev.isuru.dao.BookDAO;
import dev.isuru.exception.AuthorNotFoundException;
import dev.isuru.exception.BookNotFoundException;
import dev.isuru.exception.InvalidInputException;
import dev.isuru.model.Author;
import dev.isuru.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BookResource {
    private final BookDAO bookDAO = new BookDAO();
    private final AuthorDAO authorDAO = new AuthorDAO();
    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

    /*Remove in production*/
    static {
        BookDAO bookDAO = new BookDAO();
        bookDAO.add(new Book(
                "Atimic Habits",
                1,
                "978-3-16-148410-0",
                2016,
                10.99,
                5
        ));
        bookDAO.add(new Book(
                "A Brief History of Time",
                0,
                "978-3-16-148410-3",
                2016,
                12.99,
                30
        ));
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        Response response = Response.ok(bookDAO.getAll()).build();
        logger.info("{} GET books/", response.getStatus());
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        if (!bookDAO.contains(id)) {
            throw new BookNotFoundException(id);
        }
        Response response = Response.ok(bookDAO.get(id)).build();
        logger.info("{} GET books/{}", response.getStatus(), id);
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        validateBook(book);
        if (!authorDAO.contains(book.getAuthorId())) {
            throw new InvalidInputException("Author does not exist");
        }
        bookDAO.add(book);
        Response response = Response.status(Response.Status.CREATED).entity(book).build();
        logger.info("{} POST books/", response.getStatus());
        return response;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book book) {
        validateBook(book);
        if (!bookDAO.contains(id)) {
            throw new BookNotFoundException(id);
        }
        if (!authorDAO.contains(book.getAuthorId())) {
            throw new InvalidInputException("Author does not exist");
        }
        bookDAO.update(id, book);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} PUT books/{}", response.getStatus(), id);
        return response;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        if (!bookDAO.contains(id)) {
            throw new BookNotFoundException(id);
        }
        bookDAO.delete(id);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} DELETE books/{}", response.getStatus(), id);
        return response;
    }

    public static void validateBook(Book book) throws InvalidInputException {
        List<String> errors = new ArrayList<>();

        if (book == null) {
            throw new InvalidInputException("Book cannot be null");
        }

        // Title validation
        if (book.getTitle() == null) {
            errors.add("Title cannot be null");
        } else {
            if (book.getTitle().trim().isEmpty()) {
                errors.add("Title cannot be empty");
            }
            if (book.getTitle().length() < 2) {
                errors.add("Title must be at least 2 characters long");
            }
        }

        // Author ID validation (using getter returns primitive int)
        if (book.getAuthorId() <= 0) {
            errors.add("Author ID must be a positive number");
        }

        // ISBN validation
        if (book.getIsbn() == null) {
            errors.add("ISBN cannot be null");
        } else {
            if (!book.getIsbn().matches("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")) {
                errors.add("ISBN must be a valid 10 or 13 digit ISBN (can include hyphens)");
            }
        }

        // Publication Year validation (using getter returns primitive int)
        int currentYear = java.time.Year.now().getValue();
        if (book.getPublicationYear() < 1000 || book.getPublicationYear() > currentYear + 1) {
            errors.add("Publication year must be between 1000 and " + (currentYear + 1));
        }

        // Price validation (using getter returns primitive double)
        if (book.getPrice() == null) {
            errors.add("Price must be greater than 0");
        }

        // Stock validation (using getter returns primitive int)
        if (book.getStock() < 0) {
            errors.add("Stock cannot be negative");
        }

        if (!errors.isEmpty()) {
            throw new InvalidInputException(String.join(", ", errors));
        }
    }
}