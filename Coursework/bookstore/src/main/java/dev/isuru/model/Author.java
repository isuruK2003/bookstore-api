package dev.isuru.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Author {
    @NotNull(message = "Author ID cannot be null")
    private Integer id;

    @NotNull(message = "First name cannot be null")
    @Pattern(regexp = "^[A-Za-z]{2,}$", message = "First name must contain only letters and be at least 2 characters long")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Pattern(regexp = "^[A-Za-z]{2,}$", message = "Last name must contain only letters and be at least 2 characters long")
    private String lastName;

    @NotNull(message = "Biography cannot be null")
    private String biography;

    public Author() {}

    public Author(int id, String firstName, String lastName, String biography) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }

    public Author(String firstName, String lastName, String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
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

    public String getBiography() {
        return biography;
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

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }
}