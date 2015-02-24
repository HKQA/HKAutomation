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
 * Date: 1/2/15
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class StoreCheckoutBarcodes {




    public String getStoreCheckoutBarcodes(int warehouse, String productVariant) throws Exception {
        //List<String> storeCheckoutBarcodes = new ArrayList<String>();
        String storeCheckoutBarcode = null;
        //String productVariantId =  "NUT1642-01";
        String productVariantId = productVariant;

        //int warehouseId = 1003;
        int warehouseId = warehouse;
        Class.forName("com.mysql.jdbc.Driver");
        //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.70.27/hk_qa", "hkadmin", "admin2K11!");
        Connection conn = DriverManager.getConnection(TestUtil.getDBURL() + "hk_qa", TestUtil.getDBUser(), TestUtil.getDBPassword());
        PreparedStatement pstmt = conn.prepareStatement("SELECT si.barcode FROM `sku` s,`sku_group` sg, `sku_item` si WHERE s.`id`=sg.`sku_id` AND sg.`id`=si.`sku_group_id` AND\n" +
                "s.`warehouse_id`= ? AND s.product_variant_id= ? AND si.`sku_item_status_id`=10 limit 1; ");

        pstmt.setInt(1, warehouseId);
        pstmt.setString(2, productVariantId );

        ResultSet rs = pstmt.executeQuery();




        while (rs.next())
        {

            storeCheckoutBarcode = rs.getString("barcode");
            System.out.println(storeCheckoutBarcode);



        }

       //System.out.println("Size of the Barcodes List =" + storeCheckoutBarcodes.size());

       conn.close();
       return storeCheckoutBarcode;
    }


    public static void main(String[] args) throws Exception {

        StoreCheckoutBarcodes barcodes = new StoreCheckoutBarcodes();

        barcodes.getStoreCheckoutBarcodes(1003, "NUT1642-01");

    }

}
