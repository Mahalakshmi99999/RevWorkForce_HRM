package TestDao;

import org.example.Dao.LoginDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

        @Test
        void testLoginDAOObjectCreation() {
            LoginDAO dao = new LoginDAO();
            assertNotNull(dao);
        }

        @Test
        void testGetPasswordMethodExists() {
            LoginDAO dao = new LoginDAO();

            assertDoesNotThrow(() -> {
                dao.getClass().getMethod("getPassword", int.class);
            });
        }

        @Test
        void testUpdatePasswordMethodExists() {
            LoginDAO dao = new LoginDAO();

            assertDoesNotThrow(() -> {
                dao.getClass().getMethod("updatePassword", int.class, String.class);
            });
        }

        @Test
        void testGetSecurityQuestionMethodExists() {
            LoginDAO dao = new LoginDAO();

            assertDoesNotThrow(() -> {
                dao.getClass().getMethod("getSecurityQuestion", int.class);
            });
        }


        }

