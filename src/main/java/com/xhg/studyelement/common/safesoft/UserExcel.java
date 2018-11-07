package com.xhg.studyelement.common.safesoft;

import com.xhg.studyelement.pojo.User;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * Date 4/23/2018.
 *
 * @author marvin.zhong
 */
public class UserExcel extends AbstractExportExcel {

    private List<User> list;

    public UserExcel(List<User> list) {
        this.list = list;
    }

    @Override
    protected String getExcelUri() {
        return "export/user.xlsx";
    }

    @Override
    protected void buildExcel(XSSFWorkbook workBook) {
        final XSSFSheet sheet = workBook.getSheetAt(0);
        final int beginLine = 1;

        final List<User> users = list;
        int lineNum = beginLine;

        //获取单元格样式
        final XSSFCellStyle style = getCellStyle(sheet, 0, 1);
        final Font font = workBook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("宋体");
        style.setFont(font);

        for (User user : users) {
            setSheetValue(sheet, lineNum, 0, String.valueOf(user.getId()), style);
            setSheetValue(sheet, lineNum, 1, user.getUsername(), style);
            setSheetValue(sheet, lineNum, 2, user.getPassword(), style);
            lineNum++;
        }
    }

    /*private String getQsType(String type) {
        String qsType = "";
        if (ELECTRON_INVOICE_QS_TYPE_ZERO.equals(type)) {
            qsType = "扫码签收";
        } else if (ELECTRON_INVOICE_QS_TYPE_ONE.equals(type)) {
            qsType = "扫描仪签收";
        } else if (ELECTRON_INVOICE_QS_TYPE_TWO.equals(type)) {
            qsType = "app签收";
        } else if (ELECTRON_INVOICE_QS_TYPE_THREE.equals(type)) {
            qsType = "导入签收";
        } else if (ELECTRON_INVOICE_QS_TYPE_FOUR.equals(type)) {
            qsType = "手工签收";
        } else if (ELECTRON_INVOICE_QS_TYPE_FIVE.equals(type)) {
            qsType = "pdf上传";
        }

        return qsType;
    }*/
}
