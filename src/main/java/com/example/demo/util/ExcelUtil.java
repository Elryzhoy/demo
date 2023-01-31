package com.example.demo.util;

import com.example.demo.common.annotation.ExcelExport;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Create on 2022/12/19
 *
 * @author 周志文
 */
public class ExcelUtil {

    public static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static <T> void exportExcel(HttpServletResponse response, List<T> list, String filename, String sheetName) throws InstantiationException {
        HSSFWorkbook workBook = getWorkBook(list, sheetName);
        try {
            response.setHeader("Content_Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8") + ".xls");
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            OutputStream out = response.getOutputStream();
            workBook.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    private static <T> HSSFWorkbook getWorkBook(List<T> list, String sheetName) throws InstantiationException {
        HSSFWorkbook w = new HSSFWorkbook();
        //创建sheet
        HSSFSheet sheet = w.createSheet(sheetName);
        //创建行
        HSSFRow row = sheet.createRow(0);
        //创建列
        HSSFCell cell;
        //将查询出来的数据判空
        if (CollectionUtils.isEmpty(list)) {
            return w;
        }
        //list里面存的是对象,getDeclaredFields()利用反射得到对象里面的所有的变量,getFields()得到被public修饰的变量
        System.out.println(list.get(0).getClass().getName());
        Field[] declaredFields = list.get(0).getClass().getFields();
        for (Field field : declaredFields) {
            //得到该注解作用在某对象的变量上的改注解的属性值,如果该对象的变量都没有使用该注解,则返回null
            /**
             * 某个对象的变量名 name
             * @ExcelExport(cell=0,title="名字")
             *  private String name;
             *  只是获取属性name,并没有获取name的值
             *  此时的annotion={cell=0,title="名字"}
             **/
            ExcelExport annotation = field.getAnnotation(ExcelExport.class);
            //写表头
            if (annotation != null) {
                //第一行第某列
                cell = row.createCell(annotation.cell());
                //给第一行第某列赋值
                cell.setCellValue(annotation.title());
            }
        }
        //写数据
        for (int i = 0; i < list.size(); i++) {
            //第一行是标题,所以数据是从第二行开始
            row = sheet.createRow(i + 1);
            T obj = list.get(i);
            Field[] fileds = obj.getClass().getDeclaredFields();
            for (Field filed : fileds) {
                ExcelExport annotation = filed.getAnnotation(ExcelExport.class);
                //另一种,只是判断该变量是否有ExcelExport注解修饰,返回布尔类型
                // boolean annotationPresent = filed.isAnnotationPresent(ExcelExport.class);
                if (annotation != null) {
                    //想要获取变量的值,需要设置对象的访问权限,保证对private的属性的访问
                    filed.setAccessible(true);

                    //创建单元格,并设置值
                    String value = "";
                    try {
                        //得到变量的值,就是这么写的,变量.get(对象)得到变量的值
                        if (null != filed.get(obj)) {
                            switch (annotation.dataType()) {
                                case DATE:
                                    value = new SimpleDateFormat(annotation.dateFormat()).format(filed.get(obj));
                                    break;
                                case AMOUNT:
                                    value = new BigDecimal(filed.get(obj).toString()).divide(new BigDecimal("100")).toPlainString();
                                    break;
                                default:
                                    value = filed.get(obj) == null ? "" : filed.get(obj).toString();
                                    break;
                            }
                        }
                        row.createCell(annotation.cell()).setCellValue(value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return w;
    }
}
