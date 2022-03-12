package com.revature.model;

public class Account extends Client{
    private int accountID; // used as account's specific id, clients can have multiple accounts
    private String firstName;
    private String lastName;

    private String[] accountType = {"checking", "savings", "credit", "retirement"};
    private int balance;
    private int clientID;

    public Account() {
    }

    public Account(int accountID, String firstName, String lastName) {

    }

    public Account(int accountID, int clientID) {

    }

    public static Account addAccount(Account a) {
        return a;
    }

    // Getters and Setters
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

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

    public String[] getAccountType() {
        return new String[]{String.valueOf(accountType)};
    }

    public void setAccountType(String[] accountType) {
        this.accountType = accountType;
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
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
