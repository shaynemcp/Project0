package com.revature.controller;

import com.revature.model.Client;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class ClientController implements Controller{
    ClientService clientService;

    public ClientController() {
        this.clientService =  new ClientService();
    }

    public Handler addClient = (ctx) -> {
        Client client = ctx.bodyAsClass(Client.class);
        Client addedClient = clientService.addClient(client);
        ctx.json(addedClient);
    };

    private Handler getClientById = (ctx) -> {
        String id = ctx.pathParam("clientId");
        Client client = clientService.getClientById(id);
        ctx.json(client);
    };

    private Handler deleteClientById = (ctx) -> {
        String id = ctx.pathParam("clientId");
        boolean client = clientService.deleteClientById(id);
        ctx.json("thanks for the delete. stay blessed.");
    };

    public Handler updateClientById = (ctx) -> {
        String id = ctx.pathParam("clientId");
        Client client = ctx.bodyAsClass(Client.class);
        Client editedClient = clientService.editClient(id, client);
        ctx.json(editedClient);
    };

    private Handler getAllClients = (ctx) -> {
        List<Client> clients = clientService.getAllClients();
    ctx.json(clients);
    };



    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/clients",addClient);

        app.get("/clients/{client_id}",getClientById);
        app.put("/clients/{client_id}",updateClientById);
        app.delete("/clients/{client_id}",deleteClientById);
        app.get("/clients",getAllClients);

    }


}
