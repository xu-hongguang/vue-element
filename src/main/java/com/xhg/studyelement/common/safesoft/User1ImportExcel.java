package com.xhg.studyelement.common.safesoft;

import com.xhg.studyelement.common.exception.ExcelException;
import com.xhg.studyelement.pojo.User1;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 导入Excel文件
 */
public class User1ImportExcel extends AbstractImportExcel {
    private MultipartFile file;

    public User1ImportExcel(MultipartFile file) {
        this.file = file;
    }

    /**
     * 将excel文件数据变为实体集
     * @return 用户实体
     * @throws ExcelException 读取异常
     */
    public List<User1> analysisExcel() throws ExcelException {
        List<User1> enjoySubsidedList = newArrayList();
        final Workbook workBook = getWorkBook(file);
        //读取第一个标签
        final Sheet sheet = workBook.getSheetAt(0);
        final Integer rowCount = sheet.getLastRowNum();

        int index = 0;
        //获取数据 行数从0开始，数据从第2开始取 当没有实际数据时rowCount为1（两行）
        for (int i = 1; i < rowCount + 1; i++) {
            final Row row = sheet.getRow(i);
            //如果不是空行
            if(!isRowEmpty(row)) {
                ++index;
                final User1 entity = createImportCertificationEntity(row, index);
                enjoySubsidedList.add(entity);
            }
        }

        return enjoySubsidedList;
    }

    /**
     * 构建导入用户实体
     * @param row 行
     * @return 用户实体
     */
    private User1 createImportCertificationEntity(Row row, int index) {
        final User1 user1 = new User1();
        //序号id
//        user1.setId(index);

        //用户名
        final String userName = getCellData(row, 1);
        user1.setUsername(userName);

        //密码
        final String password= getCellData(row, 2);
        user1.setPassword(password);

        return user1;
    }
}
