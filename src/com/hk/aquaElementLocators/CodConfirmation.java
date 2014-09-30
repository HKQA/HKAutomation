package com.hk.aquaElementLocators;

/**
 * Created with IntelliJ IDEA.
 * User: vipul.jain1
 * Date: 9/29/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CodConfirmation {

    private String email = "/html/body/div[3]/div/div/div[3]/div/form/fieldset/input[1]"      ;

    private String password = "/html/body/div[3]/div/div/div[3]/div/form/fieldset/input[2]" ;

    private String login = "/html/body/div[3]/div/div/div[3]/div/form/fieldset/div/div/input[2]" ;

    private String paymentHover = "//*[@id='cssmenu']/ul/li[5]/a/span";

    private String codPayment = "//*[@id='cssmenu']/ul/li[5]/ul/li[2]/a"     ;

    private String inputOrderId = "//*[@id='filter-box']/div/div/fieldset/fieldset[2]/div/form/input[1]";

    private String filter = "//*[@id='filter-box']/div/div/fieldset/fieldset[2]/div/form/input[2]";

    private String confirmOrder = "html/body/div[5]/div/div/table/tbody/tr[2]/td[5]/a[1]";

    public String getEmail()
    {

        return email;
    }

    public String getPassword()
    {

        return password;
    }

    public String getLogin()
    {

        return login;
    }

    public String getPaymentHover()
    {

        return paymentHover;
    }

    public String getCodPayment()
    {


        return codPayment;
    }

    public String getInputOrderId()
    {

        return inputOrderId;


    }

    public String getFilter()
    {

         return filter;

    }

    public String getConfirmOrder()
    {

        return confirmOrder;

    }
}

