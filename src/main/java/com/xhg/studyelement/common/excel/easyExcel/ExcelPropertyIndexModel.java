package com.xhg.studyelement.common.excel.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ExcelPropertyIndexModel extends BaseRowModel {

    @ExcelProperty(value = "姓名" ,index = 0)
    private String name;

    @ExcelProperty(value = "年龄",index = 1)
    private Integer age;

    @ExcelProperty(value = "邮箱",index = 2)
    private String email;

    @ExcelProperty(value = "地址",index = 3)
    private String address;

    @ExcelProperty(value = "性别",index = 4)
    private String sax;

    @ExcelProperty(value = "高度",index = 5)
    private String height;

    @ExcelProperty(value = "备注",index = 6)
    private String last;

    public ExcelPropertyIndexModel() {
    }

    public ExcelPropertyIndexModel(String name, Integer age, String email, String address, String sax, String height, String last) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.sax = sax;
        this.height = height;
        this.last = last;
    }

    public static List<ExcelPropertyIndexModel> getData(){
        List<ExcelPropertyIndexModel> excelPropertyIndexModels = new ArrayList<>();

        excelPropertyIndexModels.add(new ExcelPropertyIndexModel("xhg1",24,"123@qq.com","江苏","男","172","阿达"));
        excelPropertyIndexModels.add(new ExcelPropertyIndexModel("xhg2",24,"123@qq.com","江苏","男","172","阿达"));
        excelPropertyIndexModels.add(new ExcelPropertyIndexModel("xhg4",24,"123@qq.com","江苏","男","172","阿达"));

        return excelPropertyIndexModels;
    }
}