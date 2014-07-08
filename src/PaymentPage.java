/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/2/14
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaymentPage {

    private String paymentPageDummy="html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[2]/form[1]/div[1]/div/div[4]/input";
    private String paymentPageDropdown="html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[2]/form[1]/div[1]/div/div[5]/select";
    private String proceedToPayment="html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[2]/form[1]/div[1]/div/p/input";
    private String paymentY="html/body/div[1]/form/p[1]/input[1]";
    private String proceedPayment="html/body/div[1]/form/input[5]";
    private String cashOnDelivery= "html/body/div[1]/div[2]/div[1]/div[5]/div[2]/div/div[1]/ul/li[6]";
    private String payOnDelivery=".//*[@name='makeNativePayment']";


    public String paymentPageDummy() {
        return paymentPageDummy;
    }

    public String paymentPageDropdown() {
        return paymentPageDropdown;
    }

    public String proceedToPayment() {
        return proceedToPayment;
    }

    public String paymentY() {
        return paymentY;
    }

    public String proceedPayment() {
        return proceedPayment;
    }

    public String cashOnDelivery() {
        return cashOnDelivery;
    }

    public String payOnDelivery() {
        return payOnDelivery;
    }
}
