package com.hk.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/10/14
 * Time: 3:40 PM
 */
public class OrderDetailsReturn {

    public static OrderDetailsDTO orderDetail() {
        String query = "select u.email,u.name,b.amount,b.gateway_order_id,c.store_variant_name from base_order b inner join cart_line_item c  on b.id=c.base_order_id join user u on b.user_id=u.id where gateway_order_id='" + "test" + "'";
        return
                JdbcConnectionFile.readJdbcprop(query, new ResultSetExtractor<OrderDetailsDTO>() {
                    OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();

                    @Override
                    public OrderDetailsDTO extract(ResultSet rs) throws SQLException {
                        while (rs.next()) {
                            orderDetailsDTO.setUserName(rs.getString("name"));
                            orderDetailsDTO.setAmount(rs.getDouble("amount"));
                            orderDetailsDTO.setGatewayOrderId(rs.getString("gateway_order_id"));
                            orderDetailsDTO.getProductList().add(rs.getString("store_variant_name"));
                        }

                        return orderDetailsDTO;
                    }
                });
    }
}