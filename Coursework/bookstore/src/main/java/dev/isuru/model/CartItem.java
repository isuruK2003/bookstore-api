package dev.isuru.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItem {

    @NotNull(message = "Book ID cannot be null")
    private Integer bookId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity should be at least 1")
    private Integer quantity;

    public CartItem() {}

    public CartItem(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
