package com.hk.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 7/9/14
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResultSetExtractor<T> {
    public T extract(ResultSet rs) throws SQLException;
}
