package com.hk.warehouseSpecific;

import com.hk.util.TestUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 3/10/15
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class FetchPinCodeAndVariantId {

    public void getPinCodeAndVariantId(int storeId) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(TestUtil.getDBURL() + "hk_cat", "hkadmin", "admin2K11!");
        PreparedStatement pstmt = conn.prepareStatement("select distinct(sv.id) from hk_cat.store_variant sv join hk_cat.product_variant pv on sv.product_variant_id = pv.id\n" +
                "join hk_cat.variant_inventory vi on pv.wms_variant_id = vi.wms_variant_id\n" +
                "join store_category sc on sv.store_category_id = sc.id\n" +
                "join store_variant_vendor svv on svv.store_variant_id = sv.id\n" +
                "join hk_qa.sku s on s.product_variant_id = sv.old_variant_id\n" +
                "join hk_qa.sku_group sg on sg.sku_id = s.id\n" +
                "join hk_qa.sku_item si on si.sku_group_id = sg.id\n" +
                "join hk_cat.store_variant_freebie svf on svf.active = 0 and sv.id = svf.store_variant_id\n" +
                "where sv.out_of_stock = 0 and sv.offer_price > 200 and sc.store_id = 1\n" +
                "and vi.inventory > 0 and vi.location_code in (10)\n" +
                "and svv.vendor_id = 1 and svv.active = 1 and sv.indexable = 1\n" +
                "and (sv.bool_bitset % 7 <> 0)\n" +
                "and si.sku_item_status_id = 10\n" +
                "and s.warehouse_id = ? LIMIT 1;") ;

        pstmt.setInt(1, storeId);

        ResultSet rs = pstmt.executeQuery();
        if(rs.next())
        {

            System.out.println("inside if condition");
            String id = rs.getString("id");
            System.out.println(id);
            if(storeId == 1003)
            {
            TestUtil.excel.setCellData("DWARKA_Store", "Test Scenario", 8, id );
            TestUtil.excel.setCellData("DWARKA_Store", "Test Scenario", 12, id );
            TestUtil.excel.setCellData("DWARKA_Store", "Test Scenario", 16, id );
            }
            if(storeId == 301)
            {

                TestUtil.excel.setCellData("PUN_Store", "Test Scenario", 8, id );
                TestUtil.excel.setCellData("PUN_Store", "Test Scenario", 12, id );
                TestUtil.excel.setCellData("PUN_Store", "Test Scenario", 16, id );

            }

            if(storeId == 1004)
            {

                TestUtil.excel.setCellData("Galleria_Store", "Test Scenario", 8, id );
                TestUtil.excel.setCellData("Galleria_Store", "Test Scenario", 12, id );
                TestUtil.excel.setCellData("Galleria_Store", "Test Scenario", 16, id );


            }



        }

        PreparedStatement pstmt1 = conn.prepareStatement("select pincode from hk_qa.warehouse where id = ?;");

        pstmt1.setInt(1, storeId);

        ResultSet rs1 = pstmt1.executeQuery();

        if(rs1.next())
        {

            //String pincode = rs.getInt("pincode");

            String pincode = rs1.getString("pincode");

            System.out.println(pincode);

            if(storeId == 1003)
            {

            TestUtil.excel.setCellData("DWARKA_Store", "Test Scenario", 19, pincode );
            }
            if(storeId == 301)
            {

                TestUtil.excel.setCellData("PUN_Store", "Test Scenario", 19, pincode );

            }

            if(storeId == 1004)
            {

                TestUtil.excel.setCellData("Galleria_Store", "Test Scenario", 19, pincode );


            }



        }






    }


    public static void main(String[] args) throws Exception {

        FetchPinCodeAndVariantId test = new FetchPinCodeAndVariantId();

        test.getPinCodeAndVariantId(1003);

    }

}
