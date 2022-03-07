package com.sqLite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "sqLite" + File.separator + "BaseDatos.db";

    private Connection conn;


    public DBConnection() {

        try {

            conn = DriverManager.getConnection(URL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
