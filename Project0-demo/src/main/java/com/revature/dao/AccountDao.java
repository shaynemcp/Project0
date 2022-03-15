package com.revature.dao;
import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// TODO 6: Create a DAO (data access object) class for a particular entity
public class AccountDao {
    // TODO 8: Create methods for the CRUD operations

    //C

    //Whenever an account is added, no id is associate yet the id is automatically generated
    //SO, retrieve an id and return that with the Account object


    public static Account addAccount(Account account) throws SQLException {
        try (Connection  con  = ConnectionUtility.getConnection()) {
            String sql = "INSERT INTO account (first_name, last_name, account_type, balance, client_id) VALUES = ?";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setString(3, String.valueOf(account.getAccountType()));
            pstmt.setInt(4, account.getBalance());
            pstmt.setInt(5, account.getId());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt(1);

            return new Account(generatedId, account.getFirstName(), account.getLastName());
        }
    }

    //R
    public static Account getAccountById(int id) throws SQLException{
        //TODO 9: Call the getConnection method from ConnectionUtility (Which we made)
        try (Connection con = ConnectionUtility.getConnection()) {
            //TODO 10: Create a prepared statement object using the connection object
            String sql = "SELECT * FROM account WHERE id = ?"  ;
            PreparedStatement pstmt = con.prepareStatement(sql);

            //TODO 11: If any parameter need to eb set, set the parameters (?)
            pstmt.setInt(1,id);

            //TODO12: Execute the query and retrieve a ResultSet object
            ResultSet rs = pstmt.executeQuery();

            //TODO 13: Iterate over records(s) using the ResultSet's next() method
            if (rs.next()) {
                //TODO 14: Grab the info from the record
                //  int accountId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                id = rs.getInt("accountId");
               // int accountId = rs.getInt("accountId");

                return new Account(id, firstName, lastName);
            }
        }

        return null;
    }

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM account ";
            PreparedStatement pstmt = con.prepareStatement(sql);


            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int accountId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");

                accounts.add(new Account(accountId, firstName, lastName));
            }
        }
        return accounts;
    }

    //U
    public static Account updateAccounts(Account account) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "UPDATE accounts " +
                    "SET first_name = ?, " +
                    "last_name = ?, " +
                    "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setInt(4, account.getId());

            pstmt.executeUpdate();
        }

        return account;
    }

    //D
    public boolean deleteAccountById(int id) throws SQLException{
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "DELETE FROM  accounts  WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1,id);

            int numberOfRecordsDeleted = pstmt.executeUpdate(); // executeUpdate() is used with INSERT, UPDATE, DELETE

            if (numberOfRecordsDeleted ==1) {
                return true;
            }
        }
        return false;
    }
}