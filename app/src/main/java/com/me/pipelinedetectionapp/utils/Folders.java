package com.me.pipelinedetectionapp.utils;

import java.io.File;

/**
 * 文件路径管理
 * @author HaiRun
 * @time 2019/7/22.13:34
 */
public class Folders {
    /**
     *手机路径
     */
    public static final String PATH = android.os.Environment.getExternalStorageDirectory() + File.separator;
    public static final String APP_PATH = PATH + "A排水检测";
    public static final String EXCEL_DATA = "数据/";
    public static final String PICTURE_DATA = "照片/";
    public static final String PRJ_MODE1 = "模式一";
    public static final String PRJ_MODE2 = "模式二";

    /**
     * 创建文件夹
     */
    public static void createFolder(){
        String[] folder = new String[]{PATH};

        for (int i = 0; i < folder.length; i++) {
            File file = new File(folder[i]);
            if (!file.exists()){
                file.mkdirs();
                System.out.println("创建了文件");
            }
        }
    }


}
