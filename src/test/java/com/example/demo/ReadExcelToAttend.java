package com.example.demo;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create on 2023/1/12
 * 按顺序找到姓名所对应的电话号码（登录账号）
 * @author 周志文
 */
public class ReadExcelToAttend {

    public static void main(String[] args) throws Exception {
        readExcel("/Users/macbook/Desktop/姓名.xlsx");
    }

    public static void readExcel(String url) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(url);//开启文件读取流
        XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);//读取文件
        //获取sheet
        XSSFSheet sheet1 = sheets.getSheetAt(0);
        XSSFSheet sheet2 = sheets.getSheetAt(1);
        //存放姓名
        List<String> list = new ArrayList<>();
        int rows = sheet1.getPhysicalNumberOfRows();
        for(int i=0;i<rows;i++){
            String name = sheet1.getRow(i).getCell(0).toString();
            list.add(name);
        }

        HashMap<String, String> map = new HashMap<>();
        int rows2 = sheet2.getPhysicalNumberOfRows();
        for (int j=0;j<rows2;j++){
            String name = sheet2.getRow(j).getCell(0).toString();
            String tel = sheet2.getRow(j).getCell(1).toString();
            map.put(name, tel);
        }

        //循环遍历list
        for(String name:list){
            String tel = map.get(name);
            System.out.println(tel);
        }

    }
}
