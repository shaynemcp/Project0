package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.SQLException;
import java.util.List;



public class AccountService<addedAccount> {

    private static Logger logger = LoggerFactory.getLogger(ClientService.class);

    private AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    // This constructor is used to allow us to "inject" a mock dao when we
    // are testing this class
    public AccountService (AccountDao mockDao) {
        this.accountDao = mockDao;
    }

    public List<Account> getAllAccounts() throws SQLException {
        return this.accountDao.getAllAccounts();
    }

    public Account getAccountById(String id) throws SQLException, ClientNotFoundException {
        try {
            int accountId = Integer.parseInt(id); // This could throw an unchecked exception
            // known as NumberFormatException
            // Important to take note of this, because any unhandled exceptions will result
            // in a 500 Internal Server Error (which we should try to avoid)

            Account a = AccountDao.getAccountById(accountId); // this could return null

            if (a == null) {
                throw new AccountNotFoundException("Account with id " + accountId + " was not found");
            }

            return a;

        } catch (AccountNotFoundException e) {
            throw new IllegalArgumentException("The value provided for the client must be a valid integer");
        }
    }

    // Business logic goes inside of this method
    public Account addAccount(Account a) throws SQLException {

    validateAccountInformation(a);
    // choose the type of account here
    Account accountType;

    Account addedAccount = Account.addAccount(a);
        return addedAccount;
    }

    public Account accountType() {
    }

    // If we are editing a client, we must check if the client exists or not
    public Account editAccount(String id, Account a) throws SQLException, AccountNotFoundException {
        try {
            int accountId = Integer.parseInt(id);

            if (AccountDao.getAccountById(accountId) == null) {
                throw new AccountNotFoundException("Sorry, but this account does not exist. Could not find: " + accountId);
            }

            validateAccountInformation(id,a);

            a.setId(accountId);
            Account editedAccount = AccountDao.updateAccounts(a);

            return editedAccount;
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("The value provided for the client must be a valid integer");
        }
    }
    private void validateAccountInformation(Account a) {
        a.setFirstName(a.getFirstName().trim());
        a.setLastName(a.getLastName().trim());

        if (!a.getFirstName().matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("First name must only have alphabetical characters. First name input was " + a.getFirstName());
        }

        if (!a.getLastName().matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Last name must only have alphabetical characters. Last name input was " + a.getLastName());
        }
    }
    private void validateAccountInformation(String id, Account a) {
        a.setFirstName(a.getFirstName().trim());
        a.setLastName(a.getLastName().trim());

        int accountId = Integer.parseInt(id);

        if (!a.getFirstName().matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("First name must only have alphabetical characters. First name input was " + a.getFirstName());
        }

        if (!a.getLastName().matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Last name must only have alphabetical characters. Last name input was " + a.getLastName());
        }

        if (a.getAccountID() == 0) {
            throw new IllegalArgumentException("Account Id must be a valid integer. Account Id provided was: " + id);
        }
    }
}
