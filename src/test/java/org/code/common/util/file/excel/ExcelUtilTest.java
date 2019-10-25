package org.code.common.util.file.excel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.FileWriter;

public class ExcelUtilTest {

    @Test
    public void getHSSFWorkbook() {

        String[][] strings = new String[4][4];
        strings[0][0] = "a";
        strings[0][1] = "b";
        strings[0][2] = "c";
        strings[0][3] = "d";

        strings[1][0] = "a";
        strings[1][1] = "b";
        strings[1][2] = "c";
        strings[1][3] = "d";

        strings[2][0] = "a";
        strings[2][1] = "b";
        strings[2][2] = "c";
        strings[2][3] = "d";

        strings[3][0] = "a";
        strings[3][1] = "b";
        strings[3][2] = "c";
        strings[3][3] = "d";
        HSSFWorkbook wb = ExcelUtil.generateWorkbook("hello",
                new String[]{"第一个title", "第二个title","第3个title","第4个title"},
                0,0,
                strings,null);
        ExcelUtil.createExcel("d://temp//cccc//ddd//","aaa.xls",wb);

    }

    @Test
    public void test2() throws Exception{
        int times = 10;
        Object[] cells = {"满100减15元", "100011", 15};

        //  导出为CSV文件
        long t1 = System.currentTimeMillis();
        FileWriter writer = new FileWriter("d:/test1.csv");
        CSVPrinter printer = CSVFormat.EXCEL.print(writer);
        for (int i = 0; i < times; i++) {
            printer.printRecord(cells);
        }
        printer.flush();
        printer.close();
        long t2 = System.currentTimeMillis();
        System.out.println("CSV: " + (t2 - t1));


    }
}