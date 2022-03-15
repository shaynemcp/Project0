package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;
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

    public List<Account> getAllAccounts(String id, String floor, String ceiling) throws SQLException {
        return this.accountDao.getAllAccounts();
    }

    public Account getAccountById(String clientId, String accountId) throws SQLException, ClientNotFoundException {
        try {
            clientId = String.valueOf(Integer.parseInt(clientId));
            accountId = String.valueOf(Integer.parseInt(clientId)); // This could throw an unchecked exception
            // known as NumberFormatException
            // Important to take note of this, because any unhandled exceptions will result
            // in a 500 Internal Server Error (which we should try to avoid)
            Client c = ClientDao.getClientById(Integer.parseInt(clientId));

            if (c == null) {
                throw new AccountNotFoundException("Client with id " + clientId + " was not found");
            }
            Account a = AccountDao.getAccountById(Integer.parseInt(accountId)); // this could return null

            return a;

        } catch (AccountNotFoundException e) {
            throw new IllegalArgumentException("The value provided for the client account must be a valid integer");
        }
    }


    public Account addAccount(String id, Account a) throws SQLException {

    validateAccountInformation(a);
    // choose the type of account here
    //Account accountType;

    Account addedAccount = AccountDao.addAccount(a);
        return addedAccount;
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

        if (a.getaccountId() == null) {
            throw new IllegalArgumentException("Account Id must be a valid integer. Account Id provided was: " + id);
        }
    }


}
