package com.hk.scaledupOrderPlacement;

import com.hk.util.TestUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/30/15
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FetchBarcode {

    String SOOrderId;
    int barcodeStatus = 0;
    String barcode = null;

    public List<Object> fetchBarcode(String SOOrderId) throws Exception {

        ArrayList<Object> returnList  = new ArrayList<Object>();

        this.SOOrderId = SOOrderId;

        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(TestUtil.getDBURL() + "bright_prod", "hkadmin", "admin2K11!");

        PreparedStatement pstmt = conn.prepareStatement("select si.* from si_foreign_cli sfc, sku_item si  where foreign_shipping_order_id = ? AND si.id = sfc.sku_item_id ;");

        pstmt.setString(1, this.SOOrderId);

        ResultSet rs = pstmt.executeQuery();

        if (rs.first())
        {
            System.out.println("Inside while loop");
            barcode  = rs.getString("barcode");
            returnList.add(barcode);
            barcodeStatus = rs.getInt("sku_item_status_id");
            returnList.add(barcodeStatus);

        }

        System.out.println(barcode);
        System.out.println(barcodeStatus);


     conn.close();
     return returnList;
    }

    public static void main(String[] args) throws Exception {

          FetchBarcode fb = new FetchBarcode();

        fb.fetchBarcode("2716243");


    }

}
