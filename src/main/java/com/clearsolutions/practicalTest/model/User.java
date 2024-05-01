package com.clearsolutions.practicalTest.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Represents a user entity.
 */
public class User {

    /** The unique identifier of the user. */
    private Long id;

    /** The email address of the user. */
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /** The first name of the user. */
    @NotBlank(message = "First name is required")
    private String firstName;

    /** The last name of the user. */
    @NotBlank(message = "Last name is required")
    private String lastName;

    /** The birthdate of the user. */
    @Past(message = "Birth date must be in the past")
    @NotNull(message = "Birth date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private LocalDate birthDate;

    /** The address of the user. */
    private String address;

    /** The phone number of the user. */
    private String phoneNumber;
    /**
     * Constructor for User class.
     * @param id the ID of the user
     * @param email the email address of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param birthDate the birthdate of the user
     * @param address the address of the user
     * @param phoneNumber the phone number of the user
     */
    public User(Long id, String email, String firstName, String lastName, LocalDate birthDate, String address, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the ID of the user.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the email of the user.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the first name of the user.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the birthdate of the user.
     * @return the birthdate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birthdate of the user.
     * @param birthDate the birthdate to set
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the address of the user.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the phone number of the user.
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}