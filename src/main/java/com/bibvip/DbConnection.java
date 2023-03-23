package com.bibvip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    /**
     * @throws SQLException
     * @return
     */
    public Connection readConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/edte-185";
        String user = "root";
        String password = "NO";

        Connection con = DriverManager.getConnection(
                url,
                user,
                password);

        return con;
    }


}

