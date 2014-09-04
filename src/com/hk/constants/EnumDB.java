package com.hk.constants;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/19/14
 * Time: 3:36 PM
 */
public enum EnumDB {

    CATALOG("hk_cat"),
    AQUA("hk_qa"),
    BRIGHT("bright_prod");

    private String dbName;

    private EnumDB(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }
}
