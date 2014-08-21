package com.hk.aquaElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/21/14
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateUpdateShipmentLink {

    private String createUpdateShipmentLink="/html/body/div/div[2]/div[2]/h3[4]/a";
    private String soGatewayIdTxt="//*[@id=\"gatewayOrderId\"]";
    private String boxSizeDropDown="//*[@id=\"boxSize\"]";
    private String packerNameDropDown="//*[@id=\"packer\"]";
    private String pickerNameDropDown="//*[@id=\"picker\"]";
    private String saveCreateUpdateShipmentBtn="//*[@id=\"validate\"]";

    public String getCreateUpdateShipmentLink() {
        return createUpdateShipmentLink;
    }

    public String getSoGatewayIdTxt() {
        return soGatewayIdTxt;
    }

    public String getBoxSizeDropDown() {
        return boxSizeDropDown;
    }

    public String getPackerNameDropDown() {
        return packerNameDropDown;
    }

    public String getPickerNameDropDown() {
        return pickerNameDropDown;
    }

    public String getSaveCreateUpdateShipmentBtn() {
        return saveCreateUpdateShipmentBtn;
    }
}
