package com.hk.orderCheckoutDto;

import com.hk.constants.EnumDB;
import com.hk.jdbc.JdbcConnectionFile;
import com.hk.jdbc.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/17/14
 * Time: 11:54 PM
 */
public class BrightDetails {
    private static ForeignSiCliDTO foreignSiCliDTO = null;

    public static ForeignSiCliDTO getForeignSiCliDTO(String soGatewayId) {
        if (null == foreignSiCliDTO || !foreignSiCliDTO.getForeignSoGatewayId().equals(soGatewayId)) {
            foreignSiCliDTO = getForeignSiCliFromDb(soGatewayId);
        }
        return foreignSiCliDTO;
    }

    private static ForeignSiCliDTO getForeignSiCliFromDb(String soGatewayId) {

        String fullSoGatewayId = soGatewayId;
        int index = fullSoGatewayId.indexOf("-");
        String finalSoId = fullSoGatewayId.substring(0, index );
        System.out.print("\n SO ID Final:- " + finalSoId);

        String query = "select sku.barcode,si.shipping_order_id,si.base_order_id,s.gateway_order_id \n" +
                "from si_foreign_cli si \n" +
                "join sku_item sku on si.sku_item_id=sku.id\n" +
                "join shipping_order s on si.shipping_order_id=s.id \n" +
                "where foreign_shipping_order_id = '" + finalSoId + "'";

        return
                JdbcConnectionFile.readJdbcprop(query, new ResultSetExtractor<ForeignSiCliDTO>() {

                    @Override
                    public ForeignSiCliDTO extract(ResultSet rs) throws SQLException {
                        foreignSiCliDTO = new ForeignSiCliDTO();

                        while (rs.next()) {
                            foreignSiCliDTO.getForeignBarcodeList().add(rs.getString("barcode"));
                            foreignSiCliDTO.setForeignBoId(rs.getString("base_order_id"));
                            foreignSiCliDTO.setForeignSoGatewayId(rs.getString("gateway_order_id"));
                            foreignSiCliDTO.setForeignSoId(rs.getString("shipping_order_id"));
                        }

                        return foreignSiCliDTO;
                    }
                }, EnumDB.BRIGHT);
    }
}
