package dev.isuru.resource;

import dev.isuru.dao.BookDAO;
import dev.isuru.dao.CartDAO;
import dev.isuru.dao.CustomerDAO;
import dev.isuru.exception.*;
import dev.isuru.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
@Produces("application/json")
@Consumes("application/json")
public class CartResource {

    private final CartDAO cartDAO = new CartDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private static final Logger logger = LoggerFactory.getLogger(CartResource.class);

    @POST
    @Path("/items")
    public Response addToCart(@PathParam("customerId") int customerId, Cart.CartItem item) {
        validateCustomerExists(customerId);

        int bookId = item.getBookId();
        int quantity = item.getQuantity();

        validateBook(bookId, quantity);
        cartDAO.addCartItem(customerId, bookId, quantity);
        Response response = Response.status(Response.Status.CREATED).entity(item).build();
        logger.info("{} POST /customers/{}/cart/items", response.getStatus(), customerId);
        return response;
    }

    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        validateCustomerExists(customerId);
        Response response = Response.ok(cartDAO.getCartByCustomerId(customerId)).build();
        logger.info("{} GET /customers/{}/cart/", response.getStatus(), customerId);
        return response;
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(
            @PathParam("customerId") int customerId,
            @PathParam("bookId") int bookId,
            Cart.CartItem item
    ) {
        validateCustomerExists(customerId);
        validateCartExists(customerId);
        validateBookInCart(customerId, bookId);
        validateBookIdMatch(bookId, item.getBookId());

        int quantity = item.getQuantity();
        validateQuantity(quantity);
        validateStock(bookId, quantity);

        cartDAO.addCartItem(customerId, bookId, quantity);
        Response response = Response.noContent().build();
        logger.info("{} PUT /customers/{}/cart/items/{}", response.getStatus(), customerId, bookId);
        return response;
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(
            @PathParam("customerId") int customerId,
            @PathParam("bookId") int bookId
    ) {
        validateCustomerExists(customerId);
        validateCartExists(customerId);
        validateBookInCart(customerId, bookId);

        cartDAO.removeCartItem(customerId, bookId);
        Response response = Response.noContent().build();
        logger.info("{} DELETE /customers/{}/cart/items/{}", response.getStatus(), customerId, bookId);
        return response;
    }

    // Helper Methods:

    private void validateCustomerExists(int customerId) {
        if (!customerDAO.contains(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
    }

    private void validateBook(int bookId, int quantity) {
        if (!bookDAO.contains(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        validateQuantity(quantity);
        validateStock(bookId, quantity);
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be positive");
        }
    }

    private void validateStock(int bookId, int quantity) {
        if (bookDAO.get(bookId).getStock() < quantity) {
            throw new OutOfStockException();
        }
    }

    private void validateCartExists(int customerId) {
        if (!cartDAO.hasCart(customerId)) {
            throw new CartNotFoundException(customerId);
        }
    }

    private void validateBookInCart(int customerId, int bookId) {
        if (!cartDAO.hasBookInCart(customerId, bookId)) {
            throw new BookNotFoundException(bookId);
        }
    }

    private void validateBookIdMatch(int pathBookId, int itemBookId) {
        if (pathBookId != itemBookId) {
            throw new InvalidInputException("Book ID in path does not match item");
        }
    }
}