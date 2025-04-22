package dev.isuru.dao;

import dev.isuru.model.Order;
import dev.isuru.model.CartItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderDAO {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private static final Map<Integer, Order> orders = new HashMap<>();
    private static final Map<Integer, List<Integer>> customerOrders = new HashMap<>();

    private final BookDAO bookDAO;
    private final CartDAO cartDAO;

    public OrderDAO() {
        this.bookDAO = new BookDAO();
        this.cartDAO = new CartDAO();
    }

    public Order createOrder(int customerId) {
        // Get cart items and validate
        Map<Integer, CartItem> cartItems = cartDAO.getCartByCustomerId(customerId);

        // Create new order
        Order order = new Order();
        order.setId(idGenerator.getAndIncrement());
        order.setCustomerId(customerId);
        order.setItems(new ArrayList<>(cartItems.values()));

        // Process stock changes
        cartItems.values().forEach(item -> {
            bookDAO.reduceStock(item.getBookId(), item.getQuantity());
        });

        // Save order
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
        List<Integer> orderIds = customerOrders.getOrDefault(customerId, new ArrayList<>());
        List<Order> result = new ArrayList<>();
        orderIds.forEach(id -> result.add(orders.get(id)));
        return result;
    }
}