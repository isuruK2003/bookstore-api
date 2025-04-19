package dev.isuru.exception.mapper;

import dev.isuru.exception.AuthorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorNotFoundExceptionMapper.class);

    public Response toResponse(AuthorNotFoundException exception) {
        logger.error("Author not found: {}", exception.getMessage(), exception);
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}
