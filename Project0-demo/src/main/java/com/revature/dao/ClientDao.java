package com.revature.dao;
import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// TODO 6: Create a DAO (data access object) class for a particular entity
public class ClientDao {
    // TODO 8: Create methods for the CRUD operations

    //C

    //Whenever a client is added, no id is associate yet the id is automatically generated
    //SO, retrieve an id and return that with the Client object


    public static Client addClient(Client client) throws SQLException {
        try (Connection  con  = ConnectionUtility.getConnection()) {
            String sql = "INSER INTO client (first_name, last_name) VALUES = ?";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setInt(3, client.getId());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt(1);

            return new Client(generatedId, client.getFirstName(), client.getLastName());
        }
    }

    //R
    public static Client getClientById(int id) throws SQLException{
        //TODO 9: Call the getConnection method from ConnectionUtility (Which we made)
        try (Connection con = ConnectionUtility.getConnection()) {
            //TODO 10: Create a prepared statement object using the connection object
            String sql = "SELECT * FROM client WHERE id = ?"  ;
            PreparedStatement pstmt = con.prepareStatement(sql);

            //TODO 11: If any parameter need to eb set, set the parameters (?)
            pstmt.setInt(1,id);

            //TODO12: Execute the query and retrieve a ResultSet object
            ResultSet rs = pstmt.executeQuery();

            //TODO 13: Iterate over records(s) using the ResultSet's next() method
            if (rs.next()) {
                //TODO 14: Grab the info from the record
                //  int clientID = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                 id = rs.getInt("clientId");
                int accountId = rs.getInt("accountId");

                return new Client(id, firstName, lastName); //Change to clientId, firstName, lastName, accountId
            }
        }

        return null;
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM client ";
            PreparedStatement pstmt = con.prepareStatement(sql);


            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int clientID = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");

                clients.add(new Client(clientID, firstName, lastName));
            }
        }
        return clients;
    }

    //U
    public Client updateClients(Client client) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "UPDATE clients " +
                    "SET first_name = ?, " +
                    "last_name = ?, " +
                    "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setInt(4, client.getId());

            pstmt.executeUpdate();
        }

        return client;
    }

    //D
    public boolean deleteClientById(int id) throws SQLException{
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "DELETE FROM  clients  WHWERE id = ?";

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