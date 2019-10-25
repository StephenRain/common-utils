package org.code.common.util.file.excel;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.code.common.util.file.FileUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author yaotianchi
 * @date 2019/10/25
 */
public class ExcelUtil {



        /**
         * 生成一个HSSFWorkbook,是从表格的第一行第一列开始。
         * @param sheetName sheet名称
         * @param title 标题
         * @param startRowIdx 从第几行开始，0表示第一行
         * @param startColIdx 从第几列开始，0表示第一列
         * @param values 内容
         * @param wb HSSFWorkbook对象
         * @return
         */
        public static HSSFWorkbook generateWorkbook(String sheetName,
                                                    String[] title,
                                                    int startRowIdx,
                                                    int startColIdx,
                                                    String[][] values,
                                                    HSSFWorkbook wb){

            // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
            if(wb == null){
                wb = new HSSFWorkbook();
            }

            // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(startRowIdx);


            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

            //声明列对象
            HSSFCell cell = null;

            //创建标题
            for(int i=0;i<title.length;i++){
                cell = row.createCell(i+ startColIdx);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }

            //创建内容
            for(int i=0;i<values.length;i++){
                row = sheet.createRow(i + 1 + startRowIdx);
                for(int j=0;j<values[i].length;j++){
                    //将内容按顺序赋给对应的列对象
                    row.createCell(j + startColIdx).setCellValue(values[i][j]);
                }
            }
            return wb;
        }


        @SneakyThrows
        public static void createExcel(String dirPath,String fileName,HSSFWorkbook workbook){
            FileUtil.createDirsIfNotExists(dirPath);
            File file = new File(dirPath + File.separator + fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();

        }





}
