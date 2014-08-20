package com.hk.orderCheckoutDto;

import com.hk.constants.EnumDB;
import com.hk.jdbc.JdbcConnectionFile;
import com.hk.jdbc.ResultSetExtractor;
import com.hk.orderPlacement.OrderDetailsUtil;
import com.hk.util.AutoStringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/17/14
 * Time: 11:54 PM
 */
public class BrightDetails {
    public static ForeignSiCliDTO ForeignSiCli() {
        SoDetailsDTO soDetailsdto = SoDetails.Sodetails();

        String query = "select s.base_order_id,c.id as cart_line_item_id,f.foreign_barcode,f.foreign_base_order_id,f.foreign_shipping_order_id,f.foreign_shipping_order_gateway_id \n" +
                "from shipping_order s join cart_line_item c on s.base_order_id=c.order_id \n" +
                "join foreign_si_cli f on c.id=f.cart_line_item_id \n" +
                "where s.gateway_order_id in (" + AutoStringUtils.getListAsString(soDetailsdto.getShippingOrderIdList()) + ")";
        return
                JdbcConnectionFile.readJdbcprop(query, new ResultSetExtractor<ForeignSiCliDTO>() {
                    ForeignSiCliDTO foreignSiCliDTO = new ForeignSiCliDTO();

                    @Override
                    public ForeignSiCliDTO extract(ResultSet rs) throws SQLException {
                        while (rs.next()) {
                            foreignSiCliDTO.getForeignBarcodeList().add(rs.getString("foreign_barcode"));
                            foreignSiCliDTO.getForeignBoIdList().add(rs.getString("foreign_base_order_id"));
                            foreignSiCliDTO.getForeignSoGatewayIdList().add(rs.getString("foreign_shipping_order_gateway_id"));
                            foreignSiCliDTO.getForeignSoIdList().add(rs.getString("foreign_shipping_order_id"));

                        }

                        return foreignSiCliDTO;
                    }
                }, EnumDB.AQUA);
    }
}
