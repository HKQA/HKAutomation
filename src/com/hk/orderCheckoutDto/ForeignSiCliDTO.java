package com.hk.orderCheckoutDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/17/14
 * Time: 10:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForeignSiCliDTO {

    private String foreignBoId;
    private String foreignSoId;
    private String foreignSoGatewayId;
    private String foreignWareHouseId;
    private Set<String> foreignBarcodeList = new HashSet<String>();

    public String getForeignWareHouseId() {
        return foreignWareHouseId;
    }

    public void setForeignWareHouseId(String foreignWareHouseId) {
        this.foreignWareHouseId = foreignWareHouseId;
    }


    public String getForeignBoId() {
        return foreignBoId;
    }

    public void setForeignBoId(String foreignBoId) {
        this.foreignBoId = foreignBoId;
    }

    public String getForeignSoId() {
        return foreignSoId;
    }

    public void setForeignSoId(String foreignSoId) {
        this.foreignSoId = foreignSoId;
    }

    public String getForeignSoGatewayId() {
        return foreignSoGatewayId;
    }

    public void setForeignSoGatewayId(String foreignSoGatewayId) {
        this.foreignSoGatewayId = foreignSoGatewayId;
    }

    public Set<String> getForeignBarcodeList() {
        return foreignBarcodeList;
    }

    public void setForeignBarcodeList(Set<String> foreignBarcodeList) {
        this.foreignBarcodeList = foreignBarcodeList;
    }
}
