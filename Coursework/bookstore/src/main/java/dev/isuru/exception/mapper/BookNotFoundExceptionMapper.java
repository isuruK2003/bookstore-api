package dev.isuru.exception.mapper;

import dev.isuru.exception.BookNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    private static final Logger logger = LoggerFactory.getLogger(BookNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(BookNotFoundException exception) {
        logger.error("Book not found: {}", exception.getMessage(), exception);
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
    }
}
