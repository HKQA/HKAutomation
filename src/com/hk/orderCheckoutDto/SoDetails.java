package com.hk.orderCheckoutDto;

import com.hk.constants.EnumDB;
import com.hk.jdbc.JdbcConnectionFile;
import com.hk.jdbc.ResultSetExtractor;
import com.hk.orderPlacement.OrderDetailsUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/20/14
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoDetails {
    public static SoDetailsDTO Sodetails() {

                String query = "select b.id,s.base_order_id from base_order b join shipping_order s on b.id=s.base_order_id where shipping_order_status_id=120 and gateway_order_id="+ OrderDetailsUtil.GatewayOrderId()+"'";
        return
                JdbcConnectionFile.readJdbcprop(query, new ResultSetExtractor<SoDetailsDTO>() {
                  SoDetailsDTO soDetails = new SoDetailsDTO();
                    @Override
                    public SoDetailsDTO extract(ResultSet rs) throws SQLException {
                        while (rs.next()) {

                        }

                        return soDetails;
                    }
                }, EnumDB.AQUA);
    }
}
