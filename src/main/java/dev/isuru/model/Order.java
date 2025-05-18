package dev.isuru.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private int customerId;
    private List<OrderItem> items;

    public static class OrderItem {
        private Integer bookId;
        private Integer quantity;

        public OrderItem() {
        }

        public OrderItem(Integer bookId, Integer quantity) {
            this.bookId = bookId;
            this.quantity = quantity;
        }

        public Integer getBookId() {
            return bookId;
        }

        public void setBookId(Integer bookId) {
            this.bookId = bookId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "OrderItem{" +
                    "bookId=" + bookId +
                    ", quantity=" + quantity +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderItem that = (OrderItem) o;
            return Objects.equals(bookId, that.bookId) &&
                    Objects.equals(quantity, that.quantity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bookId, quantity);
        }
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
