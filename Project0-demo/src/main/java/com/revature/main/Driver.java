package com.revature.main;

//import com.revature.controller.AccountController;
//import com.revature.controller.ClientController;
import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.Controller;
//import com.revature.controller.ExceptionController;
import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Driver {

    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) {


        Javalin app = Javalin.create();

        // This will execute before every single request
        app.before((ctx) -> {
            logger.info(ctx.method() + " request received for " + ctx.path());
        });

        mapControllers(app, new AccountController(), new ClientController()); //new ExceptionController()

        app.start(); // port 8080 by default
        try {
            Connection connection = ConnectionUtility.getConnection();
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mapControllers(Javalin app, Controller... controllers) {
        for (Controller c : controllers) {
            c.mapEndpoints(app);
        }
    }


}