package dev.isuru.resource;

import dev.isuru.dao.AuthorDAO;
import dev.isuru.dao.BookDAO;
import dev.isuru.exception.AuthorNotFoundException;
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

@Path("/authors")
public class AuthorResource {

    private static final AuthorDAO authorDAO = new AuthorDAO();
    private static final BookDAO bookDAO = new BookDAO();
    private static final Logger logger = LoggerFactory.getLogger(AuthorResource.class);


    /*Remove in production*/
    static {
        AuthorDAO authorDAO = new AuthorDAO();
        authorDAO.add(new Author(
                "Stephen",
                "Hawking",
                "Stephen William Hawking was an English theoretical physicist, cosmologist, and author who was director of research at the Centre for Theoretical Cosmology at the University of Cambridge."
        ));
        authorDAO.add(new Author(
                "James",
                "Clear",
                "James Clear (born 1986) is an American writer. He is best known for his book Atomic Habits."
        ));
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        Response response = Response.ok(authorDAO.getAll()).build();
        logger.info("{} GET authors/", response.getStatus());
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        if (!authorDAO.contains(id)) {
            throw new AuthorNotFoundException(id);
        }
        Response response = Response.ok(authorDAO.get(id)).build();
        logger.info("{} GET authors/{}", response.getStatus(), id);
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/books")
    public Response getBooksByAuthorId(@PathParam("id") int id) {
        if (!authorDAO.contains(id)) {
            throw new AuthorNotFoundException(id);
        }
        List<Book> books = bookDAO.getBooksByAuthor(id);
        Response response = Response.ok().entity(books).build();
        logger.info("{} GET authors/{}/books", response.getStatus(), id);
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        validateAuthor(author);
        authorDAO.add(author);
        Response response = Response.status(Response.Status.CREATED).entity(author).build();
        logger.info("{} POST authors/", response.getStatus());
        return response;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author author) {
        validateAuthor(author);
        if (!authorDAO.contains(id)) {
            throw new AuthorNotFoundException(id);
        }
        authorDAO.update(id, author);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} PUT authors/{}", response.getStatus(), id);
        return response;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        if (!authorDAO.contains(id)) {
            throw new AuthorNotFoundException(id);
        }
        authorDAO.delete(id);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} DELETE authors/{}", response.getStatus(), id);
        return response;
    }

    private void validateAuthor(Author author) {
        List<String> errors = new ArrayList<>();

        if (author.getFirstName() == null || !author.getFirstName().matches("^[A-Za-z]{2,}$")) {
            errors.add("First name must contain only letters and be at least 2 characters long");
        }

        if (author.getLastName() == null || !author.getLastName().matches("^[A-Za-z]{2,}$")) {
            errors.add("Last name must contain only letters and be at least 2 characters long");
        }

        if (author.getBiography() == null) {
            errors.add("Biography cannot be null");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputException(String.join(", ", errors));
        }
    }
}
