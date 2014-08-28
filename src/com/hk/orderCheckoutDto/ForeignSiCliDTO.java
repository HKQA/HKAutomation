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
    private Set<String> foreignSoIdList = new HashSet<String>();
    private Set<String> foreignSoGatewayIdList = new HashSet<String>();
    private Set<String> foreignBarcodeList = new HashSet<String>();

    public List<String> getForeignBoIdList() {
        return foreignBoIdList;
    }

    public void setForeignBoIdList(List<String> foreignBoIdList) {
        this.foreignBoIdList = foreignBoIdList;
    }

    public Set<String> getForeignSoIdList() {
        return foreignSoIdList;
    }

    public void setForeignSoIdList(Set<String> foreignSoIdList) {
        this.foreignSoIdList = foreignSoIdList;
    }

    public Set<String> getForeignSoGatewayIdList() {
        return foreignSoGatewayIdList;
    }

    public void setForeignSoGatewayIdList(Set<String> foreignSoGatewayIdList) {
        this.foreignSoGatewayIdList = foreignSoGatewayIdList;
    }

    public Set<String> getForeignBarcodeList() {
        return foreignBarcodeList;
    }

    public void setForeignBarcodeList(Set<String> foreignBarcodeList) {
        this.foreignBarcodeList = foreignBarcodeList;
    }
}
