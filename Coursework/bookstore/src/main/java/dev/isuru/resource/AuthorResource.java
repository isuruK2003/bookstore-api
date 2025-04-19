package dev.isuru.resource;

import dev.isuru.dao.AuthorDAO;
import dev.isuru.model.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authors")
public class AuthorResource {
    private final AuthorDAO authorDAO = new AuthorDAO();
    private static final Logger logger = LoggerFactory.getLogger(AuthorResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        Response response = Response.ok(authorDAO.getAll()).build();
        logger.info("{} GET authors/ : {}", response.getStatus(), response);
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Response response = Response.ok(authorDAO.get(id)).build();
        logger.info("{} GET authors/{} : {}", response.getStatus(), id, response);
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        authorDAO.add(author);
        Response response = Response.status(Response.Status.CREATED).build();
        logger.info("{} POST authors/ : {}", response.getStatus(), response);
        return response;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author author) {
        authorDAO.update(id, author);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} PUT authors/ : {}", response.getStatus(), response);
        return response;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        authorDAO.delete(id);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} DELETE authors/ : {}", response.getStatus(), response);
        return response;
    }
}
