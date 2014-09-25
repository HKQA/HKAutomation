package com.hk.orderCheckoutDto;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/20/14
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoDetailsDTO {
    /*private List<String> shippingOrderIdList = new ArrayList<String>();
    private Set<Integer> warehouseId = new HashSet<Integer>();*/

    private String soGatewayOrderId;
    private Integer warehouseId;

    public SoDetailsDTO(String soGatewayOrderId, Integer warehouseId) {
        this.soGatewayOrderId = soGatewayOrderId;
        this.warehouseId = warehouseId;
    }

    public String getSoGatewayOrderId() {
        return soGatewayOrderId;
    }

    public void setSoGatewayOrderId(String soGatewayOrderId) {
        this.soGatewayOrderId = soGatewayOrderId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }


    /*public Set<Integer> getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Set<Integer> warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<String> getShippingOrderIdList() {
        return shippingOrderIdList;
    }

    public void setShippingOrderIdList(List<String> shippingOrderIdList) {
        this.shippingOrderIdList = shippingOrderIdList;
    }*/
}
