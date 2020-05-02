package com.me.pipelinedetectionapp.utils;

import android.content.Context;
import android.widget.Toast;
import com.me.pipelinedetectionapp.bean.DetectionDb;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author linshen on 2019-08-26.
 * 邮箱: 18475453284@163.com
 */
public class ExcelUtil {
    public static WritableFont arial14font = null;
    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(Colour.VERY_LIGHT_YELLOW);


            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial12format.setAlignment(jxl.format.Alignment.CENTRE);

            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     *
     * @param fileName 导出excel存放的地址（目录）
     * @param colName  excel中包含的列名（可以有多个）
     */
    public static void initExcel(String fileName, String[] colName, String name) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet(name + "的数据表", 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));

            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
                sheet.setRowView(col, 340);
            }
            //设置行高

            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 实时历史数据Excel导出
     * @param list  表的数据
     * @param time
     * @param fileName 表的名称
     * @param name
     * @param projectName
     * @param projectNumber
     * @param inspectorName
     * @param registrarName
     * @param areaName
     * @param c
     */
    public static void QvListToExcel(List<DetectionDb> list,String fileName, String name, String projectName, String projectNumber, String inspectorName, String registrarName, String areaName,  String time, Context c) {
        if (list != null && list.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int i = 0; i < list.size(); i++) {
                    DetectionDb detectionDb = list.get(i);
                    int j = i + 1;
                    sheet.addCell(new Label(0, j, name, arial12format));
                    sheet.addCell(new Label(1, j,projectName, arial12format));
                    sheet.addCell(new Label(2, j, projectNumber, arial12format));
                    sheet.addCell(new Label(3, j, inspectorName, arial12format));
                    sheet.addCell(new Label(4, j, registrarName, arial12format));
                    sheet.addCell(new Label(5, j,areaName, arial12format));
                    sheet.addCell(new Label(6, j, detectionDb.getRoadName(), arial12format));
                    sheet.addCell(new Label(7, j, detectionDb.getLineDot(), arial12format));
                    sheet.addCell(new Label(8, j,detectionDb.getConnectionPoint() , arial12format));
                    sheet.addCell(new Label(9, j,detectionDb.getStartingPointOrigin() , arial12format));
                    sheet.addCell(new Label(10,j, detectionDb.getStartingPointEnd(), arial12format));
                    sheet.addCell(new Label(11,j, detectionDb.getFolwDirection(), arial12format));
                    sheet.addCell(new Label(12,j, detectionDb.getPipe(), arial12format));
                    sheet.addCell(new Label(13,j, detectionDb.getPipeDiameter(), arial12format));
                    sheet.addCell(new Label(14,j, detectionDb.getType(), arial12format));
                    sheet.addCell(new Label(15,j, time));
                    sheet.addCell(new Label(16,j, detectionDb.getRemark(), arial12format));
                }
                for (int i = 0; i < sheet.getColumns(); i++) {
                    sheet.setColumnView(i, 25);
                }
                writebook.write();
                Toast.makeText(c, "导出Excel成功," + Folders.PATH, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }
}
