package org.example.controller;
import org.example.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class LoginController {

    private static final Logger log =
            LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService = new LoginService();
    private final Scanner sc = new Scanner(System.in);

    public boolean handleLogin() {

        log.info("Enter Employee ID:");
        int empId = sc.nextInt();

        log.info("Enter Password:");
        String password = sc.next();

        boolean success = loginService.login(empId, password);

        if (!success) {
            log.warn("Login failed");
            return false;
        }

        log.info("Login successful");
        return true;
    }


}
