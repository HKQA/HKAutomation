package com.hk.variants;

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
 * Date: 1/6/15
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetVariants {

    public List<String> getVariants() throws Exception {


        List<String> variantsList = new ArrayList<String>();

        String productId = null;

        Class.forName("com.mysql.jdbc.Driver");

        //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.70.27/hk_cat", "hkadmin", "admin2K11!");
        Connection conn = DriverManager.getConnection(TestUtil.getDBURL() + "hk_cat", TestUtil.getDBUser(), TestUtil.getDBPassword());



        /*PreparedStatement pstmt = conn.prepareStatement("select a.id ,  count(*) from\n" +
                "(select id,old_variant_id,(CASE WHEN sv.id IS NULL THEN 1 ELSE (CASE WHEN (sv.bool_bitset % 12673 = 0) THEN 1 ELSE 0 END) END )hidden_flg, \n" +
                "(CASE WHEN sv.id IS NULL THEN 1 ELSE (CASE WHEN (sv.bool_bitset % 2800733 =0) THEN 1 ELSE 0 END) END) deleted_flg,\n" +
                "(CASE WHEN sv.id IS NULL THEN 1 ELSE (CASE WHEN (sv.bool_bitset % 164749 =0) THEN 1 ELSE 0 END) END) discontinue_flg,\n" +
                "out_of_stock from store_variant sv \n" +
                ")a ,\n" +
                "(select warehouse_id,product_variant_id,count(*) from\n" +
                "bright_prod.sku_item,bright_prod.sku_group,bright_prod.sku where sku_item_status_id=10 and sku_item.sku_group_id=sku_group.id and sku_group.sku_id=sku.id\n" +
                "group by 1,2)b \n" +
                "where a.old_variant_id=b.product_variant_id \n" +
                "and a.hidden_flg = 0\n" +
                "and a.discontinue_flg = 0\n" +
                "and a.out_of_stock = 0\n" +
                "and b.warehouse_id IN (1,2)\n" +
                "group by 1\n" +
                "having count(*) > 1\n" +
                "order by a.old_variant_id limit 1 \n " +
                ";") ;   */

        PreparedStatement pstmt = conn.prepareStatement("select sv.id from hk_cat.store_variant sv join hk_cat.product_variant pv on sv.product_variant_id = pv.id\n" +
                "join hk_cat.variant_inventory vi on pv.wms_variant_id = vi.wms_variant_id\n" +
                "join store_category sc on sv.store_category_id = sc.id\n" +
                "join store_variant_vendor svv on svv.store_variant_id = sv.id\n" +
                "where sv.out_of_stock = 0 and sv.offer_price > 200 and sc.store_id = 1\n" +
                "and vi.inventory > 0 and vi.location_code in (10, 20)\n" +
                "and svv.vendor_id = 1 and svv.active = 1 and sv.indexable = 1\n" +
                "and (sv.bool_bitset % 7 <> 0)\n" +
                "group by vi.wms_variant_id\n" +
                "having count(vi.id) > 1 limit 1;") ;


        ResultSet rs = pstmt.executeQuery();

        while (rs.next())
        {

               productId = rs.getString("id");

        }

        System.out.println(productId);

        variantsList.add(productId);

        TestUtil.excel.setCellData("Flip", "VariantId", 2, productId);

        /*PreparedStatement pstmt1 = conn.prepareStatement("select a.id ,  count(*) from\n" +
                "(select id,old_variant_id,(CASE WHEN sv.id IS NULL THEN 1 ELSE (CASE WHEN (sv.bool_bitset % 12673 = 0) THEN 1 ELSE 0 END) END )hidden_flg, \n" +
                "(CASE WHEN sv.id IS NULL THEN 1 ELSE (CASE WHEN (sv.bool_bitset % 2800733 =0) THEN 1 ELSE 0 END) END) deleted_flg,\n" +
                "(CASE WHEN sv.id IS NULL THEN 1 ELSE (CASE WHEN (sv.bool_bitset % 164749 =0) THEN 1 ELSE 0 END) END) discontinue_flg,\n" +
                "out_of_stock from store_variant sv \n" +
                ")a ,\n" +
                "(select warehouse_id,product_variant_id,count(*) from\n" +
                "bright_prod.sku_item,bright_prod.sku_group,bright_prod.sku where sku_item_status_id=10 and sku_item.sku_group_id=sku_group.id and sku_group.sku_id=sku.id\n" +
                "group by 1,2)b \n" +
                "where a.old_variant_id=b.product_variant_id \n" +
                "and a.hidden_flg = 0\n" +
                "and a.discontinue_flg = 0\n" +
                "and a.out_of_stock = 0\n" +
                "and b.warehouse_id IN (1,2)\n" +
                "group by 1\n" +
                "having count(*) = 1\n" +
                "order by a.old_variant_id limit 1 \n" +
                ";");  */

        PreparedStatement pstmt1 = conn.prepareStatement("select sv.id from hk_cat.store_variant sv join hk_cat.product_variant pv on sv.product_variant_id = pv.id\n" +
                "join hk_cat.variant_inventory vi on pv.wms_variant_id = vi.wms_variant_id\n" +
                "join store_category sc on sv.store_category_id = sc.id\n" +
                "join store_variant_vendor svv on svv.store_variant_id = sv.id\n" +
                "where sv.out_of_stock = 0 and sv.offer_price > 200 and sc.store_id = 1\n" +
                "and vi.inventory > 0 and vi.location_code in (10,20)\n" +
                "and svv.vendor_id = 1 and svv.active = 1 and sv.indexable = 1\n" +
                "and (sv.bool_bitset % 7 <> 0)\n" +
                "group by vi.wms_variant_id\n" +
                "having count(vi.id) = 1 limit 1;");

        ResultSet rs1 = pstmt1.executeQuery();

        while (rs1.next())
        {

            productId = rs1.getString("id");

        }

        System.out.println(productId);

        variantsList.add(productId);

        TestUtil.excel.setCellData("Flip", "VariantId", 3, productId);














       conn.close();
       return variantsList;

    }

    public static void main(String[] args) throws Exception {
           GetVariants variants = new GetVariants();

        variants.getVariants();
        //variants.getVariants();




    }
}
