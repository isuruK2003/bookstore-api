package dev.isuru.dao;

import dev.isuru.model.Order;
import dev.isuru.model.Cart;
import dev.isuru.model.Cart.CartItem;

import java.util.*;
import java.util.stream.Collectors;

public class OrderDAO {
    private static final Map<Integer, Order> orders = new HashMap<>();
    private static final Map<Integer, List<Integer>> customerOrders = new HashMap<>();
    private static Integer lastId = 0;

    private final BookDAO bookDAO;
    private final CartDAO cartDAO;

    public OrderDAO() {
        this.bookDAO = new BookDAO();
        this.cartDAO = new CartDAO();
    }

    public Order createOrder(int customerId) {
        Cart cart = cartDAO.getCartByCustomerId(customerId);
        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty. Cannot create order.");
        }

        // Convert to OrderItems
        List<Order.OrderItem> orderItems = cartItems.stream()
                .map(item -> new Order.OrderItem(item.getBookId(), item.getQuantity()))
                .collect(Collectors.toList());

        // Reduce stock
        for (CartItem item : cartItems) {
            bookDAO.reduceStock(item.getBookId(), item.getQuantity());
        }

        // Create and save order
        Order order = new Order();
        order.setId(lastId++);
        order.setCustomerId(customerId);
        order.setItems(orderItems);

        orders.put(order.getId(), order);
        customerOrders.computeIfAbsent(customerId, k -> new ArrayList<>()).add(order.getId());

        // Clear cart
        cartDAO.clearCart(customerId);

        return order;
    }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public List<Order> getCustomerOrders(int customerId) {
        List<Integer> orderIds = customerOrders.getOrDefault(customerId, Collections.emptyList());
        return orderIds.stream().map(orders::get).collect(Collectors.toList());
    }
}
