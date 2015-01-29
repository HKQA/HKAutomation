package com.hk.scaledupOrderPlacement;

import com.hk.util.TestUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 1/21/15
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class FetchBarcodeRTO {

    String SOOrderId ;

    public String getBarcode(String SOOrderId, String rtobarcode) throws Exception {
        this.SOOrderId = SOOrderId;
        String barcode = null;
        int barcodeStatus = 0;
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(TestUtil.getDBURL()+ "hk_qa", "hkadmin", "admin2K11!");

        if(rtobarcode == null)
        {

            PreparedStatement pstmt = conn.prepareStatement("SELECT si.*, pvi.* from product_variant_inventory pvi, sku_item si \n" +
                "where pvi.sku_item_id = si.id AND pvi.inv_txn_type_id = 20 AND shipping_order_id = ?;") ;

            pstmt.setString(1, this.SOOrderId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {

                barcode  = rs.getString("barcode");

            }

            System.out.println(barcode);

        } if(!(rtobarcode == null))
        {
            PreparedStatement pstmt = conn.prepareStatement("select * from sku_item where barcode = ?;");

            pstmt.setString(1, rtobarcode);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {

                 barcodeStatus = rs.getInt("sku_item_status_id");

            }

            System.out.println("The sku item status id of the " + rtobarcode + "= "+ barcodeStatus);








        }

        conn.close();


       return barcode;

    }

    public static void main(String[] args) throws Exception {

           FetchBarcodeRTO getBarcode = new FetchBarcodeRTO();

        getBarcode.getBarcode("2699564", "IVN-308304-19");

    }
}
