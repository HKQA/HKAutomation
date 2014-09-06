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
    public static ForeignSiCliDTO foreignSiCliDTO = new ForeignSiCliDTO();

    public static ForeignSiCliDTO ForeignSiCli(String soGatewayId) {
        String query = " select sku.barcode,si.shipping_order_id,si.base_order_id,s.gateway_order_id \n" +
                "from si_foreign_cli si \n" +
                "join sku_item sku on si.sku_item_id=sku.id\n" +
                "join shipping_order s on si.shipping_order_id=s.id \n" +
                "where foreign_shipping_order_id = '"+soGatewayId+"'";
        System.out.print("\n SO ID:- " +soGatewayId);

        return
                JdbcConnectionFile.readJdbcprop(query, new ResultSetExtractor<ForeignSiCliDTO>() {

                    @Override
                    public ForeignSiCliDTO extract(ResultSet rs) throws SQLException {
                        while (rs.next()) {
                            foreignSiCliDTO.getForeignBarcodeList().add(rs.getString("foreign_barcode"));
                            foreignSiCliDTO.setForeignBoId(rs.getString("foreign_base_order_id"));
                            foreignSiCliDTO.setForeignSoGatewayId(rs.getString("foreign_shipping_order_gateway_id"));
                            foreignSiCliDTO.setForeignSoId(rs.getString("foreign_shipping_order_id"));
                        }

                        return foreignSiCliDTO;
                    }
                }, EnumDB.BRIGHT);
    }
}
