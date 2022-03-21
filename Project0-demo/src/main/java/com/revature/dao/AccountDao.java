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
            String sql = "INSERT INTO accounts (first_name, last_name, balance, client_id) VALUES (?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setInt(3, account.getBalance());
            pstmt.setInt(4, account.getClientID());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt(1);

            return new Account(generatedId, account.getFirstName(), account.getLastName(), account.getBalance(), account.getClientID());
        }
    }

    //R
    public static Account getAccountById(int id) throws SQLException{
        //TODO 9: Call the getConnection method from ConnectionUtility (Which we made)
        try (Connection con = ConnectionUtility.getConnection()) {
            //TODO 10: Create a prepared statement object using the connection object
            String sql = "SELECT * FROM accounts WHERE id = ?"  ;
            PreparedStatement pstmt = con.prepareStatement(sql);

            //TODO 11: If any parameter need to be set, set the parameters (?)
            pstmt.setInt(1,id);
//            pstmt.setInt(2, client_id);

            //TODO12: Execute the query and retrieve a ResultSet object
            ResultSet rs = pstmt.executeQuery();

            //TODO 13: Iterate over records(s) using the ResultSet's next() method
            if (rs.next()) {
                //TODO 14: Grab the info from the record
                //  int accountId = rs.getInt("id");
                id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int balance = rs.getInt("balance");
                int clientId = rs.getInt("client_id");
                return new Account(id, firstName, lastName, balance, clientId);
            }
        }

        return null;
    }

    public static Boolean deleteAccountById(int accountId) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "DELETE FROM  accounts  WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1,accountId);

            int numberOfRecordsDeleted = pstmt.executeUpdate(); // executeUpdate() is used with INSERT, UPDATE, DELETE

            if (numberOfRecordsDeleted ==1) {
                System.out.print("We're sorry you ended your account. Stay blessed");
                return true;
            }
        }
        return false;
    }
    public List<Account> getAllAccounts(String id) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        int client_id = Integer.parseInt(id);
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM accounts where client_id= ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,client_id);


            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int accountId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int balance = rs.getInt("balance");
                client_id = rs.getInt("client_id");



                accounts.add(new Account(accountId, firstName, lastName, balance, client_id));
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
                    "balance = ?, " +
                    "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setInt(3,account.getBalance());
            pstmt.setInt(4, account.getId());

            pstmt.executeUpdate();
        }

        return account;
    }

    //D
    public boolean deleteAccountByClientId(int id) throws SQLException{
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "DELETE FROM  accounts  WHERE client_id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1,id);

            int numberOfRecordsDeleted = pstmt.executeUpdate(); // executeUpdate() is used with INSERT, UPDATE, DELETE

            if (numberOfRecordsDeleted ==1) {
                System.out.println("We're sorry you closed all accounts. Come back soon.");
                return true;
            }
        }
        return false;
    }

    // Returns account of id 'y' for client 'x'
    public Account getAccountIdById(int id, int client_id)  {
        try (Connection con = ConnectionUtility.getConnection()) {
        String sql = "SELECT * FROM accounts WHERE (id = ? AND client_id = ?)"  ;
        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1,id);

        //TODO12: Execute the query and retrieve a ResultSet object
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            id = rs.getInt("id");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            int clientId = (rs.getInt("client_id"));
            int balance = rs.getInt("balance");
            return new Account(id, firstName, lastName, balance, clientId);
        }
    } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
}
}