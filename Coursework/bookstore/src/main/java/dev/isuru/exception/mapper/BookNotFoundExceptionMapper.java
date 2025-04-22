package dev.isuru.exception.mapper;

import dev.isuru.exception.BookNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {

    @Override
    public Response toResponse(BookNotFoundException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("type", BookNotFoundException.class.getSimpleName());
        error.put("message", exception.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
