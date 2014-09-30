package com.hk.orderCheckoutDto;

import com.hk.constants.EnumDB;
import com.hk.jdbc.JdbcConnectionFile;
import com.hk.jdbc.ResultSetExtractor;
import com.hk.orderPlacement.OrderDetailsUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/20/14
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoDetails {
    public static List<SoDetailsDTO> soDetailsdtoList = new ArrayList<SoDetailsDTO>();

    public static String orderIdSoDetails;

    public static List<SoDetailsDTO> Sodetails() {

        /*String query = "select s.warehouse_id,s.gateway_order_id from base_order b join shipping_order s on b.id=s.base_order_id " +
                "where s.shipping_order_status_id=120 and b.gateway_order_id='"+OrderDetailsUtil.GatewayOrderId()+"'";*/

        String query = "select s.warehouse_id,s.gateway_order_id from base_order b join shipping_order s on b.id=s.base_order_id " +
                "where s.shipping_order_status_id=120 and b.gateway_order_id='"+orderIdSoDetails+"'";




        return
                JdbcConnectionFile.readJdbcprop(query, new ResultSetExtractor<List<SoDetailsDTO>>() {

                    @Override
                    public List<SoDetailsDTO> extract(ResultSet rs) throws SQLException {
                        while (rs.next()) {
                            SoDetailsDTO soDetailsDTO = new SoDetailsDTO(rs.getString("gateway_order_id"), rs.getInt("warehouse_id"));
                            soDetailsdtoList.add(soDetailsDTO);


                        }

                        return soDetailsdtoList;
                    }
                }, EnumDB.AQUA);

    }


}
