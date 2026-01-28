package org.example;

import org.example.controller.LoginController;
import org.example.Dao.RoleDAO;
import org.example.controller.MenuController;
import org.example.models.LoggedInUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

        private static final Logger log =
                LoggerFactory.getLogger(Main.class);

        public static void main(String[] args) {

            LoginController loginController = new LoginController();
            boolean loggedIn = loginController.handleLogin();

            if (!loggedIn) {
                log.warn("Application exiting due to failed login");
                return;
            }

            MenuController menuController = new MenuController();
            menuController.showMenu();

            log.info("RevWorkForce Application Ended");
        }
    }
