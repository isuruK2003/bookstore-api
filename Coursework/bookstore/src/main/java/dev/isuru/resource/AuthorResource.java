package dev.isuru.resource;

import dev.isuru.dao.AuthorDAO;
import dev.isuru.exception.AuthorNotFoundException;
import dev.isuru.model.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAuthor(@NotNull @Valid Author author) {
        authorDAO.add(author);
        Response response = Response.status(Response.Status.CREATED).entity(author).build();
        logger.info("{} POST authors/", response.getStatus());
        return response;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, @NotNull @Valid Author author) {
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
}
