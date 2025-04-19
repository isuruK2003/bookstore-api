package dev.isuru.exception.mapper;

import dev.isuru.exception.CustomerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {
    private static final Logger logger = LoggerFactory.getLogger(CustomerNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(CustomerNotFoundException exception) {
        logger.error("Customer not found: {}", exception.getMessage(), exception);
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
    }
}
