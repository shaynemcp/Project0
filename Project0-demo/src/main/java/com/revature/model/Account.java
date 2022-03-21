package com.revature.model;

import java.util.Arrays;
import java.util.Objects;

public class Account extends Client{
    private String firstName;
    private String lastName;
    private int accountId;
//    private String[] accountType = {"wages", "savings", "endorsements", "retirement"};
    private int balance;
    private int clientID;

    public Account() {
    }

    public Account(int accountId, String firstName, String lastName, int balance, int clientID) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.clientID = clientID;
    }

//    public Account(int accountId, int clientID) {
//        this.accountId = accountId;
//        this.clientID = clientID;
//    }


    // Getters and Setters


    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return accountId == account.accountId && balance == account.balance && clientID == account.clientID && Objects.equals(firstName, account.firstName) && Objects.equals(lastName, account.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, accountId, balance, clientID);
    }

    @Override
    public String
    toString() {
        return "Account{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accountId=" + accountId +
                ", balance=" + balance +
                ", clientID=" + clientID +
                '}';
    }
}
