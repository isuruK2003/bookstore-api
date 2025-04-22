package dev.isuru.resource;

import dev.isuru.dao.BookDAO;
import dev.isuru.dao.CustomerDAO;
import dev.isuru.dao.OrderDAO;
import dev.isuru.exception.CustomerNotFoundException;
import dev.isuru.model.Order;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/orders")
@Produces("application/json")
@Consumes("application/json")
public class OrderResource {

    private final OrderDAO orderDAO = new OrderDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final BookDAO bookDAO = new BookDAO();

    @POST
    public Response createOrder(@PathParam("customerId") int customerId) {
        validateCustomerExists(customerId);
        Order newOrder = orderDAO.createOrder(customerId);
        return Response.status(Response.Status.CREATED).entity(newOrder).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrder(
            @PathParam("customerId") int customerId,
            @PathParam("orderId") int orderId
    ) {
        validateCustomerExists(customerId);
        Order order = orderDAO.getOrder(orderId);
        return Response.ok(order).build();
    }

    @GET
    public Response getCustomerOrders(@PathParam("customerId") int customerId) {
        validateCustomerExists(customerId);
        List<Order> orders = orderDAO.getCustomerOrders(customerId);
        return Response.ok(orders).build();
    }

    private void validateCustomerExists(int customerId) {
        if (!customerDAO.contains(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
    }
}