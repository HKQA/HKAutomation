package com.hk.poscheckout;

import com.hk.util.TestUtil;

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

    public String getBarcodeStatus(int storeId) throws Exception {

        String barcode = null;

        //int barcodeStatus = 0;

        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(TestUtil.getDBURL()+"hk_qa", "hkadmin", "admin2K11!");

        PreparedStatement pstmt = conn.prepareStatement("SELECT si.barcode FROM `sku` s,`sku_group` sg, `sku_item` si WHERE s.`id`=sg.`sku_id` AND sg.`id`=si.`sku_group_id` AND s.`warehouse_id`= ? \n" +
                "AND si.`sku_item_status_id`=10 limit 1;");

        pstmt.setInt(1, storeId);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next())
        {

           barcode  = rs.getString("barcode");

        }

        conn.close();

        return barcode;

    }

    public static void main(String[] args) throws Exception {

        BarcodeStatus bs = new BarcodeStatus();

        bs.getBarcodeStatus(301);


    }
}
