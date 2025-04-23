package dev.isuru.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private Integer id;
    private Integer customerId;

    private List<CartItem> items;

    public Order() {}

    public Order(int id, int customerId, List<CartItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }

    public Order(int customerId, List<CartItem> items) {
        this.id = UUID.randomUUID().hashCode();
        this.customerId = customerId;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", items=" + items +
                '}';
    }
}
