package com.hk.jdbc;

import com.hk.property.PropertyHelper;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/9/14
 * Time: 11:55 AM
 */
public class JdbcConnectionFile {// JDBC driver name and database URL

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(
                    PropertyHelper.readProperty("DB_URL"),
                    PropertyHelper.readProperty("USER"),
                    PropertyHelper.readProperty("PASS"));

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, email, gateway_order_id FROM payment order by create_dt desc limit 0,3";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String first = rs.getString("gateway_order_id");

                System.out.println("ID: " + id);
                System.out.println(", email: " + email);
                System.out.println(", gateway_order_id: " + first);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
