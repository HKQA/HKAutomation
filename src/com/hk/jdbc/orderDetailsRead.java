package com.hk.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/10/14
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class orderDetailsRead {
    public static List<String>  orderdDetail() {
         JdbcConnectionFile.readJdbcprop("select b.gateway_order_id,c.store_variant_name from base_order b join cart_line_item c on b.id=c.base_order_id where gateway_order_id='" + dataArray.get(5) + "'", new ResultSetExtractor<Object>() {
            String email = null;
            String email2 = null;
            List<String> resultquery = new ArrayList<String>();


            @Override
            public Object extract(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    email = rs.getString("gateway_order_id");
                    email2 = rs.getString("store_variant_name");
                }
                resultquery.add(email);
                resultquery.add(email2);
                return resultquery;
            }
        });
    /*for(String string:resultQuery){
        System.out.println(string);
    }*/
        return null;

    }

    public static void printList(List<String> stringList) {
        for (String string : stringList) {
            System.out.println(string);
        }
    }

}