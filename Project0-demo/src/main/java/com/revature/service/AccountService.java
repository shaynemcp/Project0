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
import java.util.Objects;


public class AccountService {

    private static Logger logger = LoggerFactory.getLogger(ClientService.class);

    private ClientDao clientDao;
    private AccountDao accountDao;


    public AccountService() {
        this.clientDao = new ClientDao();
        this.accountDao = new AccountDao();
    }

    // This constructor is used to allow us to "inject" a mock dao when we
    // are testing this class
//    public AccountService (AccountDao mockDao) {
//        this.accountDao = mockDao;
//    }

//    public List<Account> getAllAccounts() throws SQLException {
//        return this.accountDao.getAllAccounts();
//    }

    public List<Account> getAllAccounts(String id) throws SQLException, AccountNotFoundException {
//        return this.accountDao.getAllAccounts();
        int client_id = Integer.parseInt(id);
        List<Account> accounts = accountDao.getAllAccounts(id);
        if(accounts.isEmpty()) {
            throw new AccountNotFoundException("This account is not found");
        }
        return accounts;
    }

    public Account getAccountById(String id) throws SQLException, ClientNotFoundException {
        try {
//           int clientId = (Integer.parseInt(client_id));
            int accountId =(Integer.parseInt(id)); // This could throw an unchecked exception
            // known as NumberFormatException
            // Important to take note of this, because any unhandled exceptions will result
            // in a 500 Internal Server Error (which we should try tvo avoid)
            Client c = ClientDao.getClientById(Integer.parseInt(id));

            if (c == null) {
                throw new AccountNotFoundException("Client with id " + id + " was not found");
            }
            Account a = AccountDao.getAccountById(accountId); // this could return null

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
    public Account editAccount(String id, String client_id, Account a) throws SQLException, AccountNotFoundException {
        try {
            int accountId = Integer.parseInt(id);
            int clientId = Integer.parseInt(client_id);

            if (AccountDao.getAccountById(accountId) == null) {
                throw new AccountNotFoundException("Sorry, but this account does not exist. Could not find: " + accountId);
            }

            validateAccountInformation(id,a);

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

        if (Objects.equals(a.getAccountId(), "null")) {
            throw new IllegalArgumentException("Account Id must be a valid integer. Account Id provided was: " + id);
        }
        if (a.getBalance()<0){
            throw new IllegalArgumentException("Cannot have negative balance");
        }
    }

    public boolean deleteAccountById(String id, String account_id) throws SQLException, ClientNotFoundException, AccountNotFoundException {

        int clientId = Integer.parseInt(id);
        int accountId = Integer.parseInt(account_id);
        Account account = accountDao.getAccountIdById(clientId, accountId);
        if ( account == null) {
            throw new AccountNotFoundException("Account with id :" + clientId + "was not found");
        }
        Boolean account1 = AccountDao.deleteAccountById(accountId);
        if (account1 == false) {
            throw new AccountNotFoundException("Account with id :" + accountId  + "was not found");
        }
        return true;

    }

}
