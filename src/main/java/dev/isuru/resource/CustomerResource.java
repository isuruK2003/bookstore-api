package dev.isuru.resource;

import dev.isuru.dao.CustomerDAO;
import dev.isuru.exception.CustomerNotFoundException;
import dev.isuru.exception.InvalidInputException;
import dev.isuru.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Path("/customers")
public class CustomerResource {
    private final CustomerDAO customerDAO = CustomerDAO.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(CustomerResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        Response response = Response.ok(customerDAO.getAll()).build();
        logger.info("{} GET customers/", response.getStatus());
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        if (!customerDAO.contains(id)) {
            throw new CustomerNotFoundException(id);
        }
        Response response = Response.ok(customerDAO.get(id)).build();
        logger.info("{} GET customers/{}", response.getStatus(), id);
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomers(Customer customer) {
        validateCustomer(customer);
        customerDAO.add(customer);
        Response response = Response.status(Response.Status.CREATED).entity(customer).build();
        logger.info("{} POST customers/", response.getStatus());
        return response;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer customer) {
        if (!customerDAO.contains(id)) {
            throw new CustomerNotFoundException(id);
        }
        validateCustomer(customer);
        customerDAO.update(id, customer);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} PUT customers/{}", response.getStatus(), id);
        return response;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        if (!customerDAO.contains(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerDAO.delete(id);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} DELETE customers/{}", id, response.getStatus());
        return response;
    }

    private void validateCustomer(Customer customer) {
        List<String> errors = new ArrayList<>();

        if (customer.getFirstName() == null || !customer.getFirstName().matches("^[A-Za-z]{2,}$")) {
            errors.add("First name must contain only letters and be at least 2 characters long");
        }

        if (customer.getLastName() == null || !customer.getLastName().matches("^[A-Za-z]{2,}$")) {
            errors.add("Last name must contain only letters and be at least 2 characters long");
        }

        // Validate email with regex
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            errors.add("Email is required");
        }
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matcher(customer.getEmail()).matches()) {
            errors.add("Invalid email format");
        }

        // Validate password (8-100 characters)
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            errors.add("Password is required");
        }

        if (!errors.isEmpty()) {
            throw new InvalidInputException(String.join(", ", errors));
        }
    }
}
