package com.example.demo.test;

import com.example.demo.entry.Person;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create on 2022/5/24
 *
 * @author 周志文
 */
public class ExcelConversion {


    //分割字符串
    public static String[] splitByNumber(String s, int chunkSize){
        int chunkCount = (s.length() / chunkSize) + (s.length() % chunkSize == 0 ? 0 : 1);
        String[] returnVal = new String[chunkCount];
        for(int i=0;i<chunkCount;i++){
            returnVal[i] = s.substring(i*chunkSize, Math.min((i+1)*chunkSize, s.length()));
        }
        return returnVal;
    }


    //读取Excel文件
    public static HashMap<String, List> readExcel(InputStream in) throws IOException, InvalidFormatException, ParseException {

        XSSFWorkbook xWorkbook = new XSSFWorkbook(in);
        // Read the Sheet
        XSSFSheet xssfSheet = xWorkbook.getSheetAt(0);

        HashMap<String,List> map = new HashMap();

        // Read the Row
        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null) {

                List<String> list1 = new ArrayList<>();

                //单数行且不为标题
                if(rowNum %2==1&&rowNum!=1){
                    //获取上一行的姓名
                    String name = xssfSheet.getRow(rowNum-1).getCell(10).getStringCellValue();
                    //遍历出打卡时间
                    for(int i= 1;i<32;i++){
                        String cell = xssfRow.getCell(i-1).getStringCellValue();
                        if(cell !=""){
                            String[] time = splitByNumber(cell,5);
                            for(int j=0;j<time.length; j++){
                                list1.add("2022-05-"+i+" "+time[j]+":00");
                            }
                        }
                    }
                    map.put(name, list1);
                }

            }

        }

        xWorkbook.close();
        return map;
    }

    //生成excel
    private static void export(Map<String,List> map) {
        Workbook wb = new HSSFWorkbook();
        int rowSize = 0;
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(rowSize);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("手机号");
        row.createCell(2).setCellValue("时间");

        try {
            for (int x = 0; x < map.size(); x++) {
                rowSize = 1;
                Row rowNew = sheet.createRow(rowSize + x);
                List list = map.get(x);
                for (int i = 0; i < list.size(); i++) {


                }
            }
        } catch (Exception e) {

        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("aaa.xls");
            wb.write(outputStream);
        } catch (Exception e) {

        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {

            }
            try{
                if(wb != null){
                    wb.close();
                }
            } catch (Exception e){

            }
        }

    }

    public static void main(String[] args) throws IOException, ParseException, InvalidFormatException {

        String fullFileName = "/Users/macbook/Desktop/work/副本办公大楼(2).xlsx";
        InputStream is = new FileInputStream(fullFileName);
        HashMap<String,List> map = readExcel(is);
        System.out.println(map.toString());
    }
}
