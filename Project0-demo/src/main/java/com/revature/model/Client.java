package com.revature.model;

import java.util.Objects;

// TODO 7: Create a class that will serve as a model for database client table



public class Client {
    private int id; //will signify client's unique clientId
    private String firstName;
    private String lastName;

    public Client() {
    }

    public Client(int id, String lastName) {
    }

    public Client(int id, String firstName, String lastName) {
    }

// Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && o.equals(firstName, client.firstName) && o.equals(lastName, client.lastName); }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName,lastName);
        }

    @Override
    public String toString() {
        return  "Client{" +
                "id=" + id +
                ", firstName='" + firstName + " \'" +
                ", lastName='" + lastName + " }'"; }

}
