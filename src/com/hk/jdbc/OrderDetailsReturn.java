package com.hk.jdbc;

import com.hk.orderPlacement.OrderDetailsUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/10/14
 * Time: 3:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderDetailsReturn {
    public static List<String>  orderDetail() {
        String query="select u.email,u.name,b.amount,b.gateway_order_id,c.store_variant_name from base_order b inner join cart_line_item c  on b.id=c.base_order_id join user u on b.user_id=u.id where gateway_order_id='"+ OrderDetailsUtil.GatewayOrderId()+"'" ;
        List<String> results =
        JdbcConnectionFile.
                readJdbcprop(query, new ResultSetExtractor<List<String>>() {
            String email = null;
            String name = null;
            String amount = null;
            String gatewayOrderId = null;
            String productName = null;



            List<String> resultquery = new ArrayList<String>();
            @Override
            public List<String> extract(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    email = rs.getString("email");
                    name = rs.getString("name");
                    amount = rs.getString("amount");
                    gatewayOrderId  = rs.getString("gateway_order_id");
                    productName  = rs.getString("store_variant_name");

                }
                resultquery.add(email);
                resultquery.add(name);
                resultquery.add(amount);
                resultquery.add(gatewayOrderId);
                resultquery.add(productName);

                return resultquery;
            }
        });

        return results;

    }

    public static void printList(List<String> stringList) {
        for (String string : stringList) {
            System.out.println(string);
        }
    }
}