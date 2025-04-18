package dev.isuru.resource;

import dev.isuru.dao.BookDAO;
import dev.isuru.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/books")
public class BookResource {
    private final BookDAO bookDAO = new BookDAO();
    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        Response response = Response.ok(bookDAO.getAll()).build();
        logger.info("{} GET books/ : {}", response.getStatus(), response);
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Response response = Response.ok(bookDAO.get(id)).build();
        logger.info("{} GET books/{} : {}", response.getStatus(), id, response);
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        bookDAO.add(book);
        Response response = Response.status(Response.Status.CREATED).build();
        logger.info("{} POST books/ : {}", response.getStatus(), response);
        return response;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book book) {
        bookDAO.update(id, book);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} PUT books/ : {}", response.getStatus(), response);
        return response;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        bookDAO.delete(id);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} DELETE books/ : {}", response.getStatus(), response);
        return response;
    }
}
