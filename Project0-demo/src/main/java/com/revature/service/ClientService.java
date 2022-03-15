package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;



public class ClientService {

    private static Logger logger = LoggerFactory.getLogger(ClientService.class);

    private ClientDao clientDao;

    public ClientService() {
        this.clientDao = new ClientDao();
    }

    // This constructor is used to allow us to "inject" a mock dao when we
    // are testing this class
    public ClientService(ClientDao mockDao) {
        this.clientDao = mockDao;
    }

    public List<Client> getAllClients() throws SQLException {
        return this.clientDao.getAllClients();
    }

    public Client getClientById(String id) throws SQLException, ClientNotFoundException {
        try {
            int clientId = Integer.parseInt(id); // This could throw an unchecked exception
            // known as NumberFormatException
            // Important to take note of this, because any unhandled exceptions will result
            // in a 500 Internal Server Error (which we should try to avoid)

            Client c = ClientDao.getClientById(clientId); // this could return null

            if (c == null) {
                throw new ClientNotFoundException("Client with id " + clientId + " was not found");
            }

            return c;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The value provided for the client must be a valid integer");
        }
    }

    // Business logic goes inside of this method
    public Client addClient(Client c) throws SQLException {

        validateClientInformation(c);

        Client addedClient = ClientDao.addClient(c);
        return addedClient;
    }

    // If we are editing a client, we must check if the client exists or not
    public Client editClient(String id, Client c) throws SQLException, ClientNotFoundException {
        try {
            int clientId = Integer.parseInt(id);

            if (ClientDao.getClientById(clientId) == null) {
                throw new ClientNotFoundException("Sorry, but this client does not exist. Could not find: " + clientId);
            }

            validateClientInformation(c);

            c.setId(clientId);
            Client editedClient = clientDao.updateClients(c);

            return editedClient;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The value provided for the client must be a valid integer");
        }
    }

    private void validateClientInformation(Client c) {
        c.setFirstName(c.getFirstName().trim());
        c.setLastName(c.getLastName().trim());

        if (!c.getFirstName().matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("First name must only have alphabetical characters. First name input was " + c.getFirstName());
        }

        if (!c.getLastName().matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Last name must only have alphabetical characters. Last name input was " + c.getLastName());
        }
    }

    public boolean deleteClientById(String id) throws SQLException, ClientNotFoundException {
        try {
            int clientId = Integer.parseInt(id);
            Client c = clientDao.getClientById(clientId);
            if (c == null) {
                throw new ClientNotFoundException("Client not found. We could not delete client " + id);
            }
            boolean deleteClient = clientDao.deleteClientById(clientId);
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Client not found");
        }

    }
}
