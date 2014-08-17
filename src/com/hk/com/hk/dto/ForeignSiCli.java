package com.hk.com.hk.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/17/14
 * Time: 10:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForeignSiCli {
    private String gatewayOrderId;
    private String foreignBarcode;
    private List<Long> foreignBaseOrderId = new ArrayList<Long>();
    private List<Long> foreignSOrderId = new ArrayList<Long>();
    private List<Long> foreignShippingOrderGatewayID = new ArrayList<Long>();

    public String getGatewayOrderId() {
        return gatewayOrderId;
    }

    public void setGatewayOrderId(String gatewayOrderId) {
        this.gatewayOrderId = gatewayOrderId;
    }

    public String getForeignBarcode() {
        return foreignBarcode;
    }

    public void setForeignBarcode(String foreignBarcode) {
        this.foreignBarcode = foreignBarcode;
    }

    public List<Long> getForeignBaseOrderId() {
        return foreignBaseOrderId;
    }

    public void setForeignBaseOrderId(List<Long> foreignBaseOrderId) {
        this.foreignBaseOrderId = foreignBaseOrderId;
    }

    public List<Long> getForeignSOrderId() {
        return foreignSOrderId;
    }

    public void setForeignSOrderId(List<Long> foreignSOrderId) {
        this.foreignSOrderId = foreignSOrderId;
    }

    public List<Long> getForeignShippingOrderGatewayID() {
        return foreignShippingOrderGatewayID;
    }

    public void setForeignShippingOrderGatewayID(List<Long> foreignShippingOrderGatewayID) {
        this.foreignShippingOrderGatewayID = foreignShippingOrderGatewayID;
    }
}
