package com.xhg.studyelement.common.safesoft;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导出excel
 *
 * @author marvin.zhong
 */
public class User1ExcelTemplate extends AbstractExportExcel {

    @Override
    protected String getExcelUri() {
        return "export/user.xlsx";
    }

    @Override
    protected void buildExcel(XSSFWorkbook workBook) { }

}
