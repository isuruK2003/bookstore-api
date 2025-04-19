package dev.isuru.resource;

import dev.isuru.dao.CustomerDAO;
import dev.isuru.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
public class CustomerResource {
    private final CustomerDAO customerDAO = new CustomerDAO();
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
        Response response = Response.ok(customerDAO.get(id)).build();
        logger.info("{} GET customers/{}", response.getStatus(), id);
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomers(Customer customer) {
        customerDAO.add(customer);
        Response response = Response.status(Response.Status.CREATED).build();
        logger.info("{} POST customers/", response.getStatus());
        return response;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer customer) {
        customerDAO.update(id, customer);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} PUT customers/{}", response.getStatus(), id);
        return response;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        customerDAO.delete(id);
        Response response = Response.status(Response.Status.NO_CONTENT).build();
        logger.info("{} DELETE customers/{}", id, response.getStatus());
        return response;
    }
}
