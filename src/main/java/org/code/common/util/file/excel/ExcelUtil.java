package org.code.common.util.file.excel;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.code.common.util.file.FileUtil;

import java.io.*;

/**
 * @author yaotianchi
 * @date 2019/10/25
 */
public class ExcelUtil {



    /**
     * 生成一个HSSFWorkbook
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
                                                HSSFWorkbook wb) throws Exception{

        if(wb == null){
            wb = new HSSFWorkbook();
        }
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow row = sheet.createRow(startRowIdx);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        style.setFont(font);
        row.setRowStyle(style); //设置标题样式
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
                row.createCell(j + startColIdx).setCellValue(values[i][j]);
                if(i == values.length-1){
                    sheet.autoSizeColumn((short)(j + startColIdx));
                    sheet.setColumnWidth(j + startColIdx,sheet.getColumnWidth(j + startColIdx) + 255*5 );
                }
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

    /*
     * 生成一个HSSFWorkbook的输入流
     */
    public static InputStream generateInputStream(String sheetName,
                                                  String[] title,
                                                  int startRowIdx,
                                                  int startColIdx,
                                                  String[][] values,
                                                  HSSFWorkbook wb) throws Exception{
        HSSFWorkbook workbook = generateWorkbook(sheetName, title, startRowIdx, startColIdx, values, wb);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }





}
