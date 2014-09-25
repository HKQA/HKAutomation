package com.hk.jdbc;

import com.hk.constants.EnumDB;
import com.hk.property.PropertyHelper;
import com.hk.util.TestUtil;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/9/14
 * Time: 11:55 AM
 */
public class JdbcConnectionFile {// JDBC driver name and database URL


    //public  static <T> T readJdbcprop(String inputSql, ResultSetExtractor<T> extract) {

    public static <T> T readJdbcprop(String inputSql, ResultSetExtractor<T> extract, EnumDB enumDB) {


        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");


            System.out.println("Connecting to database...");
            //conn = DriverManager.getConnection(PropertyHelper.readProperty("DB_URL"), PropertyHelper.readProperty("USER"), PropertyHelper.readProperty("PASS"));




            String dbUrl = PropertyHelper.readProperty("DB_IP") + enumDB.getDbName();
            //conn = DriverManager.getConnection(dbUrl, PropertyHelper.readProperty("USER"), PropertyHelper.readProperty("PASS"));
//            conn = DriverManager.getConnection(PropertyHelper.readProperty("DB_URL"), PropertyHelper.readProperty("USER"), PropertyHelper.readProperty("PASS"));
            conn = DriverManager.getConnection(TestUtil.getDBURL(), TestUtil.getDBUser(), TestUtil.getDBPassword());

           stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(inputSql);
            System.out.println("Executing query...");
            return extract.extract(rs);
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
                    System.out.println("DB connection is about to get closed");
                    conn.close();

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return null;
    }
}
