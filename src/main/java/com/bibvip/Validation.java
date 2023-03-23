package com.bibvip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validation {

    DbConnection dbConnection = new DbConnection();
    Connection con = null;

    {
        try {
            con = dbConnection.readConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isNotExisting(int id) throws SQLException {
        boolean notExisting = true;

        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM user_info WHERE id = " + id);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            notExisting = false;
        }
        return notExisting;
    }


    public boolean isExisting(String name) throws SQLException {

        boolean existing = false;

        PreparedStatement ps = con.prepareStatement
                        ("SELECT * FROM user_info WHERE name = " + "'" + name + "'");

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            existing = true;
        }
        return existing;
    }
}
