package com.xhg.studyelement.controller;

import com.xhg.studyelement.common.safesoft.User1Excel;
import com.xhg.studyelement.common.safesoft.User1ExcelTemplate;
import com.xhg.studyelement.serivce.User1Service;
import com.xhg.studyelement.shiro.realm.PermissionName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class User1ExcelController {

    private static final Logger LOGGER = getLogger(User1ExcelController.class);

    private final User1Service user1Service;

    @Autowired
    public User1ExcelController(User1Service user1Service) {
        this.user1Service = user1Service;
    }

    @RequestMapping("/export/userExcel")
    @RequiresPermissions("export:exportUser1List")
    @PermissionName("导出用户列表Excel")
    public void toExcel(HttpServletResponse response){
        User1Excel userExcel = new User1Excel(user1Service.findAll(), "export/user.xlsx");
        userExcel.write(response,"userExcel");
    }

    /**
     * 导出表格模板
     * @param response
     */
    @RequestMapping("/export/UserExcelTemplate")
    public void getExcelTemplate(HttpServletResponse response){
        User1ExcelTemplate user1ExcelTemplate = new User1ExcelTemplate();
        user1ExcelTemplate.write(response,"userExcel");
    }

    /**
     *
     * @param multipartFile 文件
     */
    @RequestMapping("/export/importUserExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile multipartFile){
        LOGGER.info("用户Excel导入,params {}", multipartFile);
        return user1Service.parseExcel(multipartFile);
    }

}
