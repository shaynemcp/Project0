package com.revature.model;

import java.util.Arrays;
import java.util.Objects;

public class Account extends Client{
    private String accountId; // used as account's specific id, clients can have multiple accounts
    private String firstName;
    private String lastName;

    private String[] accountType = {"wages", "savings", "endorsements", "retirement"};
    private int balance;
    private String clientID;

    public Account() {
    }

    public Account(int accountId, String firstName, String lastName) {

    }

    public Account(int accountId, int clientID) {

    }


    // Getters and Setters

    public String getaccountId() {
        return accountId;
    }

    public void setaccountId(String accountId) {
        this.accountId = accountId;
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
        return accountType;
    }

    public void setAccountType(String[] accountType) {
        this.accountType = accountType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = Integer.parseInt(balance);
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) && Objects.equals(firstName, account.firstName) && Objects.equals(lastName, account.lastName) && Arrays.equals(accountType, account.accountType) && Objects.equals(balance, account.balance) && Objects.equals(clientID, account.clientID);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), accountId, firstName, lastName, balance, clientID);
        result = 31 * result + Arrays.hashCode(accountType);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accountType=" + Arrays.toString(accountType) +
                ", balance='" + balance + '\'' +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}
