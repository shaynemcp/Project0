package com.revature.controller;


import com.revature.exception.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.service.AccountService;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class AccountController implements Controller {
    ClientService clientService;
    AccountService accountService;

    public AccountController() {
        this.accountService = new AccountService();
        this.clientService = new ClientService();
    }

    private Handler getAccountsBetween = (ctx) -> {
        String id = ctx.pathParam("client_id");
        String floor = ctx.pathParam("amountGreaterThan");
        String ceiling = ctx.pathParam("amountLessThan");

        if(floor!= null && ceiling!= null) {
            List<Account> accounts = accountService.getAllAccounts(id);
            ctx.json(accounts);
        }else{
            List<Account> accounts = accountService.getAllAccounts(id);
            ctx.json(accounts);
        }
    };

    private Handler getAllAccounts = (ctx) -> {
        String id =ctx.pathParam("client_id");
        List<Account> accounts = accountService.getAllAccounts(id);
        ctx.json(accounts);
    };

    private Handler getAccountById = (ctx) -> {
        String id =ctx.pathParam("client_id");
        String accountId =ctx.pathParam("account_id");
        Account accounts = accountService.getAccountById(id);
        ctx.json(accounts);
    };

    private Handler addClientAccount = (ctx) -> {
        Account account = ctx.bodyAsClass(Account.class);
        String id = ctx.pathParam("cliend_id");
        Account addAccount = accountService.addAccount(id, account);
        ctx.json(addAccount);
    };

    private Handler updateAccountById = (ctx) -> {
        String clientId = ctx.pathParam("client_id");
        String accountId = ctx.pathParam("account_id");
        Account account = ctx.bodyAsClass(Account.class);
        Account editedAccount = accountService.editAccount(accountId, clientId, account);
        ctx.json(editedAccount);
    };

    private Handler deleteAccountById = (ctx) ->  {
    String clientId = ctx.pathParam("client_id");
    String accountId = ctx.pathParam("account_id");
    Boolean clientDelete = accountService.deleteAccountById(clientId, accountId);
    ctx.json("We're sorry you closed your account");
    };







    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/clients/{client_id}/accounts", addClientAccount);
        app.get("/clients/{client_id}/accounts?amountGreaterThan=?&amountLessThan=?", getAccountsBetween);
        app.get("/clients/{client_id}/accounts", getAllAccounts);
        app.get("/clients/{client_id}/accounts/{account_id}", getAccountById);
        app.put("/clients/{client_id}/accounts/{account_id}", updateAccountById);
        app.delete("/clients/{client_id}/accounts/{account_id}",deleteAccountById);
    }
}
