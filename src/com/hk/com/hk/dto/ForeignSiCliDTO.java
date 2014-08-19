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
public class ForeignSiCliDTO {

    private List<String> foreignBoIdList = new ArrayList<String>();
    private List<String> foreignSoIdList = new ArrayList<String>();
    private List<String> foreignSoGatewayIdList = new ArrayList<String>();
    private List<String> foreignBarcodeList = new ArrayList<String>();

    public List<String> getForeignBoIdList() {
        return foreignBoIdList;
    }

    public void setForeignBoIdList(List<String> foreignBoIdList) {
        this.foreignBoIdList = foreignBoIdList;
    }

    public List<String> getForeignSoIdList() {
        return foreignSoIdList;
    }

    public void setForeignSoIdList(List<String> foreignSoIdList) {
        this.foreignSoIdList = foreignSoIdList;
    }

    public List<String> getForeignSoGatewayIdList() {
        return foreignSoGatewayIdList;
    }

    public void setForeignSoGatewayIdList(List<String> foreignSoGatewayIdList) {
        this.foreignSoGatewayIdList = foreignSoGatewayIdList;
    }

    public List<String> getForeignBarcodeList() {
        return foreignBarcodeList;
    }

    public void setForeignBarcodeList(List<String> foreignBarcodeList) {
        this.foreignBarcodeList = foreignBarcodeList;
    }
}
