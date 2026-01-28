package org.example.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBconnection {

    private static Connection connection;

    private DBconnection(){

    }

    private static final Logger logger = LoggerFactory.getLogger(DBconnection.class);

    private static Connection getConnection()  {
        String url = "jdbc:mysql://localhost:3306/WorkForceRev_db";
        String user = "root";
        String password = "root";
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }
        }catch (SQLException se){
            System.out.println(se);
        }
        return connection;

    }

    public static Connection getInstance() {
        return getConnection();
    }
}



