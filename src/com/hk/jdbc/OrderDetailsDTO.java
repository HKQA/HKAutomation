package com.hk.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 7/30/14
 * Time: 7:34 PM
 */
public class OrderDetailsDTO {

    private String email;
    private String userName;
    private Double amount;
    private String gatewayOrderId;
    private List<Long> productList = new ArrayList<Long>();
    private List<Long> cartLineItemIdList = new ArrayList<Long>();

    public List<Long> getCartLineItemIdList() {
        return cartLineItemIdList;
    }

    public void setCartLineItemIdList(List<Long> cartLineItemIdList) {
        this.cartLineItemIdList = cartLineItemIdList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getGatewayOrderId() {
        return gatewayOrderId;
    }

    public void setGatewayOrderId(String gatewayOrderId) {
        this.gatewayOrderId = gatewayOrderId;
    }

    public List<Long> getProductList() {
        return productList;
    }

    public void setProductList(List<Long> productList) {
        this.productList = productList;
    }

    }
