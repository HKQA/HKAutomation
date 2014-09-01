package com.hk.orderCheckoutDto;

import com.hk.constants.EnumDB;
import com.hk.jdbc.JdbcConnectionFile;
import com.hk.jdbc.ResultSetExtractor;
import com.hk.orderPlacement.OrderDetailsUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/20/14
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoDetails {
    public static SoDetailsDTO soDetailsdto = new SoDetailsDTO();

    public static SoDetailsDTO Sodetails() {

        String query = "select s.warehouse_id,s.gateway_order_id from base_order b join shipping_order s on b.id=s.base_order_id " +
                "where s.shipping_order_status_id=120 and b.gateway_order_id='HK24409-720161'";
        return
                JdbcConnectionFile.readJdbcprop(query, new ResultSetExtractor<SoDetailsDTO>() {

                    @Override
                    public SoDetailsDTO extract(ResultSet rs) throws SQLException {
                        while (rs.next()) {
                            soDetailsdto.getShippingOrderIdList().add(rs.getString("gateway_order_id"));
                            soDetailsdto.getWarehouseId().add(rs.getInt("warehouse_id"));
                        }

                        return soDetailsdto;
                    }
                }, EnumDB.AQUA);

    }


}
