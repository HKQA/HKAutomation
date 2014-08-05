package com.hk.excel;

import com.hk.excel.dto.TestDetailsDTO;
import com.hk.io.HKRow;
import com.hk.io.HKXlsParser;
import com.hk.property.PropertyHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 8/1/14
 * Time: 8:15 PM
 */
public class TestDetailsExcelService {

    private static final String VARIANT_ID_HEADER = "VARIANT_ID";
    private static final String LOGIN_HEADER = "LOGIN";
    private static final String SIGNUP_HEADER = "SIGNUP";
    private static final String PASSWORD_HEADER = "PASSWORD";

    public static TestDetailsDTO getTestDetails() {
        TestDetailsDTO testDetailsDTO = new TestDetailsDTO();
        HKXlsParser excel = new HKXlsParser(PropertyHelper.readProperty("productIdExcel"), "Sheet1", true);
        Iterator<HKRow> iter = excel.parse();

        while (iter.hasNext()) {
            HKRow row = iter.next();

            Long variantId = null;
            String stringVariantId = row.getColumnValue(VARIANT_ID_HEADER);
            if (StringUtils.isNotBlank(stringVariantId)) {
                variantId = Long.valueOf(stringVariantId);
            }
            if (variantId != null) {
                testDetailsDTO.getVariantIdList().add(variantId);
            }
            String stringLoginId = row.getColumnValue(LOGIN_HEADER);
            if (StringUtils.isNotBlank(stringLoginId)) {
                testDetailsDTO.setLoginList(stringLoginId);
            }
            String stringSignUpId = row.getColumnValue(SIGNUP_HEADER);
            if (StringUtils.isNotBlank(stringSignUpId)) {
                testDetailsDTO.setSignUpList(stringSignUpId);
            }
            String stringPassword = row.getColumnValue(PASSWORD_HEADER);
            if (StringUtils.isNotBlank(stringPassword)) {
                testDetailsDTO.setPasswordList(stringPassword);
            }
        }

        return testDetailsDTO;
    }
}
