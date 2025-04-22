package dev.isuru.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public class Customer {
    @NotNull(message = "ID cannot be null")
    private Integer id;

    @NotNull(message = "First name cannot be null")
    @Pattern(regexp = "^[A-Za-z]{2,}$", message = "First name must contain only letters and be at least 2 characters long")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Pattern(regexp = "^[A-Za-z]{2,}$", message = "Last name must contain only letters and be at least 2 characters long")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email must be a valid format like user@example.com")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^.{8,20}$", message = "Password must be between 8 and 20 characters")
    private String password;

    public Customer() {}

    public Customer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.id = UUID.randomUUID().hashCode();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
