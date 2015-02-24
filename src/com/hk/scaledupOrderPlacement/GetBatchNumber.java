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
 * Date: 2/2/15
 * Time: 7:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetBatchNumber {

    public List<String> getBatchNumber(String gatewayOrderId, String variant) throws Exception {

        List<String> returnList = new ArrayList<String>();

        String batchNumber = null;
        String productVariant = null;
        int warehouse_id = 0;
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(TestUtil.getDBURL() + "bright_prod", TestUtil.getDBUser(), TestUtil.getDBPassword());
        PreparedStatement pstmt = conn.prepareStatement("SELECT  sg.batch_number, sv.old_variant_id, so.warehouse_id from hk_qa.base_order bo, hk_qa.shipping_order so, si_foreign_cli sfc, sku_item si, sku_group sg, hk_cat.store_variant sv\n" +
                "where bo.gateway_order_id = ? \n" +
                "AND sv.id = ? \n" +
                "AND bo.id = so.base_order_id\n" +
                "AND so.id = sfc.foreign_shipping_order_id\n" +
                "AND sfc.sku_item_id = si.id\n" +
                "AND si.sku_group_id = sg.id;") ;

        pstmt.setString(1, gatewayOrderId);
        pstmt.setString(2, variant);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next())
        {

           batchNumber = rs.getString("batch_number");
            returnList.add(batchNumber);
            productVariant = rs.getString("old_variant_id");
            returnList.add(productVariant);
            warehouse_id = rs.getInt("warehouse_id");
            returnList.add(String.valueOf(warehouse_id));


        }

        System.out.println(batchNumber);
        System.out.println(productVariant);


       conn.close();
       return returnList;
    }

    public static void main(String[] args) throws Exception {

        GetBatchNumber bnumber = new GetBatchNumber();

        bnumber.getBatchNumber("HKD-16791-930774", "42868");

    }

}
