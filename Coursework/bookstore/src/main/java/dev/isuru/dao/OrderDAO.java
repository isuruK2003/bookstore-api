package dev.isuru.dao;

import dev.isuru.model.Order;
import dev.isuru.model.Cart;
import dev.isuru.model.Cart.CartItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class OrderDAO {
    private final Map<Integer, Order> orders = new ConcurrentHashMap<>();
    private final Map<Integer, List<Integer>> customerOrders = new ConcurrentHashMap<>();
    private final AtomicInteger lastId = new AtomicInteger(0);

    private final BookDAO bookDAO;
    private final CartDAO cartDAO;

    private static volatile OrderDAO instance;

    private OrderDAO() {
        this.bookDAO = BookDAO.getInstance();
        this.cartDAO = CartDAO.getInstance();
    }

    public static OrderDAO getInstance() {
        if (instance == null) {
            synchronized (OrderDAO.class) {
                if (instance == null) {
                    instance = new OrderDAO();
                }
            }
        }
        return instance;
    }

    public Order createOrder(int customerId) {
        Cart cart = cartDAO.getCartByCustomerId(customerId);
        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());  // Snapshot

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty. Cannot create order.");
        }

        // Convert to OrderItems atomically
        List<Order.OrderItem> orderItems = cartItems.stream()
                .map(item -> new Order.OrderItem(item.getBookId(), item.getQuantity()))
                .collect(Collectors.toList());

        // Reduce stock - thread-safe through BookDAO
        for (CartItem item : cartItems) {
            bookDAO.reduceStock(item.getBookId(), item.getQuantity());
        }

        // Create and save order
        Order order = new Order();
        order.setId(lastId.getAndIncrement());
        order.setCustomerId(customerId);
        order.setItems(orderItems);

        orders.put(order.getId(), order);
        customerOrders.computeIfAbsent(customerId, k -> new CopyOnWriteArrayList<>())
                .add(order.getId());

        // Clear cart - thread-safe through CartDAO
        cartDAO.clearCart(customerId);

        return order;
    }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public List<Order> getCustomerOrders(int customerId) {
        return customerOrders.getOrDefault(customerId, Collections.emptyList())
                .stream()
                .map(orders::get)
                .collect(Collectors.toList());
    }
}