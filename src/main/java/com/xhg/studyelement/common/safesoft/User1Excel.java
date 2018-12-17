package com.xhg.studyelement.common.safesoft;

import com.xhg.studyelement.pojo.User1;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * 导出excel
 *
 * @author marvin.zhong
 */
public class User1Excel extends AbstractExportExcel {

    private final String excelName ;

    private final List<User1> list;

    public User1Excel(List<User1> list, String excelName) {
        this.list = list;
        this.excelName = excelName;
    }

    @Override
    protected String getExcelUri() {
        return excelName;
    }

    @Override
    protected void buildExcel(XSSFWorkbook workBook) {
        final XSSFSheet sheet = workBook.getSheetAt(0);
        final int beginLine = 1;

        final List<User1> users = list;
        int lineNum = beginLine;

        //获取单元格样式
        final XSSFCellStyle style = getCellStyle(sheet, 0, 1);
        final Font font = workBook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("宋体");
        style.setFont(font);

        // 数据填入Excel
        for (User1 user : users) {
            setSheetValue(sheet, lineNum, 0, String.valueOf(user.getId()), style);
            setSheetValue(sheet, lineNum, 1, user.getUsername(), style);
            setSheetValue(sheet, lineNum, 2, user.getPassword(), style);
            lineNum++;
        }
    }

}
