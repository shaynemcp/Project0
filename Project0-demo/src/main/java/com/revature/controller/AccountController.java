package com.revature.controller;

import com.revature.model.Account;
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

    private Handler getAllAccounts = (ctx) -> {
       ;

        String id = ctx.pathParam("client_id");
        String floor = ctx.pathParam("amountGreaterThan");
        String ceiling = ctx.pathParam("amountLessThan");

        if(floor!= null && ceiling!= null) {
            List<Account> accounts = accountService.getAllAccounts(id, floor, ceiling);
            ctx.json(accounts);
        }else{
            List<Account> accounts = accountService.getAllAccounts();
            ctx.json(accounts);
        }
    };

    private Handler getAllAccountsById = (ctx) -> {
        String id =ctx.pathParam("client_id");
        String accountId =ctx.pathParam("account_id");
        Account accounts = accountService.getAccountById(id,accountId);
        ctx.json(accounts);
    };

    private Handler addClientAccount = (ctx) -> {
        Account account = ctx.bodyAsClass(Account.class);
        String id = ctx.pathParam("cliend_id");
        Account addAccount = accountService.addAccount(id, account);
    };

    private Handler updateAccountById = (ctx) -> {
        String id = ctx.pathParam("client_id");

        Account account = ctx.bodyAsClass(Account.class);
        Account editedAccount = accountService.editAccount(id, account);
        ctx.json(editedAccount);
    };

    private Handler deleteAccountById = (ctx) -> {
    };






    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/clients/{client_id}/accounts", addClientAccount);
        app.get("/clients/{client_id}/accounts",getAllAccounts);
        app.get("/clients/{client_id}/accounts/{account_id}",getAllAccountsById);
        app.put("/clients/{client_id}/accounts/{account_id}",updateAccountById);
        app.delete("/clients/{client_id}/accounts/{account_id}",deleteAccountById)
    }
}
