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

    private List<String> foreignBoIdList = new ArrayList<String>();
    private List<String> foreignSoIdList = new ArrayList<String>();
    private Set<String> foreignSoGatewayIdList = new HashSet<String>();
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

    public Set<String> getForeignSoGatewayIdList() {
        return foreignSoGatewayIdList;
    }

    public void setForeignSoGatewayIdList(Set<String> foreignSoGatewayIdList) {
        this.foreignSoGatewayIdList = foreignSoGatewayIdList;
    }

    public List<String> getForeignBarcodeList() {
        return foreignBarcodeList;
    }

    public void setForeignBarcodeList(List<String> foreignBarcodeList) {
        this.foreignBarcodeList = foreignBarcodeList;
    }
}
