package com.hk.poscheckout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/5/15
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BarcodeStatus {

    public int getBarcodeStatus(String brcode) throws Exception {

        String barcode = brcode;

        int barcodeStatus = 0;

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.70.53/hk_qa", "hkadmin", "admin2K11!");

        PreparedStatement pstmt = conn.prepareStatement("select * from sku_item where barcode = ?;");

        pstmt.setString(1, barcode );

        ResultSet rs = pstmt.executeQuery();

        while (rs.next())
        {

            barcodeStatus = rs.getInt("sku_item_status_id");

        }

        conn.close();

        return barcodeStatus;

    }
}
