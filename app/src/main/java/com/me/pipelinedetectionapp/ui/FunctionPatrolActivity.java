package com.me.pipelinedetectionapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.drainagemonitoring.greendao.DetectionDbDao;
import com.example.drainagemonitoring.greendao.PipePSCheckDbDao;
import com.me.pipelinedetectionapp.R;
import com.me.pipelinedetectionapp.adapter.FunctionAdapter;
import com.me.pipelinedetectionapp.adapter.FunctionAdapter2;
import com.me.pipelinedetectionapp.bean.DetectionDb;
import com.me.pipelinedetectionapp.bean.PipePSCheckDb;
import com.me.pipelinedetectionapp.config.MyApplication;
import com.me.pipelinedetectionapp.utils.DateTimeUtil;
import com.me.pipelinedetectionapp.utils.ExcelUtil;
import com.me.pipelinedetectionapp.utils.Folders;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author on 2019年8月27日14:55:25
 * 巡查
 */
public class FunctionPatrolActivity extends AppCompatActivity {
    @BindView(R.id.patrol_return)
    TextView patrolReturn;
    @BindView(R.id.function_excel)
    TextView functionExcel;

    @BindView(R.id.function_addLog)
    TextView functionAddLog;
    @BindView(R.id.function_rl)
    RecyclerView functionRl;
    @BindView(R.id.function_tv)
    TextView functionTv;
    private Unbinder unbinder;
    private String[] title = {"序号", "工程名称", "项目编号", "区域", "检测方法", "检测员", "记录员","影像编号",
            "道路名称", "管线点号", "连接点号", "起点埋深", "终点埋深", "流向", "管材", "管径", "管类", "日期", "备注"};

    private String[] title2 = {"序号", "工程名称", "项目编号", "区域", "检测方法", "检测员", "记录员","影像编号",
            "道路名称", "起始设施编号", "终止设施编号", "检测长度", "流向", "充满度(%)", "管材", "管渠规格(mm)", "管类",
            "缺陷距离", "缺陷代码", "缺陷等级", "雨污混接情况", "检查井问题", "雨水井问题","其他问题","照片编号", "位置", "备注"};

    private String fileName;
    private List<DetectionDb> detectionDbList;
    private List<PipePSCheckDb> pipePSCheckDbList;

    private FunctionAdapter functionAdapter;
    private FunctionAdapter2 functionAdapter2;
    private DetectionDbDao detectionDbDao;
    private PipePSCheckDbDao pipePSCheckDbDao;
    private String projectName;
    private String projectMode;
    private int counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_patrol);
        unbinder = ButterKnife.bind(this);
        projectName = getIntent().getStringExtra("projectName");
        projectMode = getIntent().getStringExtra("checkMode");
        functionTv.setText(projectName);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        List<DetectionDb> list = null;
        List<PipePSCheckDb> list2 = null;
        if (projectMode.equals(Folders.PRJ_MODE1)) {
            list2 = pipePSCheckDbDao.queryBuilder().orderDesc(PipePSCheckDbDao.Properties.Id).list();
            if (list2.size() != 0) {
                counts = list2.size();
                pipePSCheckDbList = pipePSCheckDbDao.queryBuilder().where(PipePSCheckDbDao.Properties.ProjectName.eq(projectName)).orderDesc(PipePSCheckDbDao.Properties.Id).build().list();
            }

            functionAdapter2 = new FunctionAdapter2(this, pipePSCheckDbList);
            functionRl.setAdapter(functionAdapter2);
            functionAdapter2.notifyDataSetChanged();

        } else if (projectMode.equals(Folders.PRJ_MODE2)) {
            list = detectionDbDao.queryBuilder().orderDesc(DetectionDbDao.Properties.Id).list();
            if (list.size() != 0) {
                counts = list.size();
                detectionDbList = detectionDbDao.queryBuilder().where(DetectionDbDao.Properties.ProjectName.eq(projectName)).orderDesc(DetectionDbDao.Properties.Id).build().list();
            }
            functionAdapter = new FunctionAdapter(this, detectionDbList);
            functionRl.setAdapter(functionAdapter);
            functionAdapter.notifyDataSetChanged();
        }

        functionTv.setText(projectName + "(" + counts + ")");
    }

    private void initView() {
        if (projectMode.equals(Folders.PRJ_MODE1)) {
            pipePSCheckDbDao = MyApplication.getApplication().getDaoSession().getPipePSCheckDbDao();
            pipePSCheckDbList = new ArrayList<>();
        } else {
            detectionDbDao = MyApplication.getApplication().getDaoSession().getDetectionDbDao();
            detectionDbList = new ArrayList<>();
        }
        LinearLayoutManager _linearLayoutManager = new LinearLayoutManager(this);
        _linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        functionRl.setLayoutManager(_linearLayoutManager);
    }

    @OnClick({R.id.patrol_return, R.id.function_excel, R.id.function_addLog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.patrol_return:
                finish();
                break;
            case R.id.function_addLog:
                if (projectMode.equals(Folders.PRJ_MODE1)) {
                    Intent intent = new Intent(this, CollecDataActivity.class);
                    intent.putExtra("projectName", projectName);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, TestingTableActivity.class);
                    intent.putExtra("projectName", projectName);
                    startActivity(intent);
                }

                break;

            //导出excel
            case R.id.function_excel:
                String projectNumber = getIntent().getStringExtra("projectNumber");
                String inspectorName = getIntent().getStringExtra("inspectorName");
                String registrarName = getIntent().getStringExtra("registrarName");
                String areaName = getIntent().getStringExtra("areaName");
                String testMethod = getIntent().getStringExtra("testMethod");
                String time = DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);

                File file = new File(Folders.APP_PATH +"/" +projectName + "/" + Folders.EXCEL_DATA);
                ExcelUtil.makeDir(file);
                //excel表名
                fileName = file.toString() +"/"+ projectName + "-" + time + ".xls";
                if (projectMode.equals(Folders.PRJ_MODE1)) {
                    ExcelUtil.initExcel(fileName, title2, projectName + "表");
                    List<PipePSCheckDb> list = pipePSCheckDbDao.queryBuilder().where(PipePSCheckDbDao.Properties.ProjectName.eq(projectName)).build().list();
                    ExcelUtil.QvListToExcel2(list, fileName, projectName, projectNumber, inspectorName, registrarName, areaName, testMethod, this);
                } else {
                    //初始化表格
                    ExcelUtil.initExcel(fileName, title, projectName + "表");
                    List<DetectionDb> list = detectionDbDao.queryBuilder().where(DetectionDbDao.Properties.ProjectName.eq(projectName)).build().list();
                    ExcelUtil.QvListToExcel(list, fileName, projectName, projectNumber, inspectorName, registrarName, areaName, testMethod, this);
                }


                break;
            default:
                break;
        }
    }

    /**
     * 更新数据
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/5/2  15:33
     */
    private void update() {
        if (projectMode.equals(Folders.PRJ_MODE1)) {
            pipePSCheckDbList = pipePSCheckDbDao.queryBuilder().where(PipePSCheckDbDao.Properties.ProjectName.eq(projectName)).orderDesc(PipePSCheckDbDao.Properties.Id).build().list();
            if (pipePSCheckDbList.size() != 0) {
                counts = pipePSCheckDbList.size();
                functionAdapter2.setContentList(pipePSCheckDbList);
                functionAdapter2.notifyDataSetChanged();
            }
        } else {
            detectionDbList = detectionDbDao.queryBuilder().where(DetectionDbDao.Properties.ProjectName.eq(projectName)).orderDesc(DetectionDbDao.Properties.Id).build().list();
            if (detectionDbList.size() != 0) {
                counts = detectionDbList.size();
                functionAdapter.setContentList(detectionDbList);
                functionAdapter.notifyDataSetChanged();
            }
        }
        functionTv.setText(projectName + "(" + counts + ")");

    }

    /**
     * 设置表格
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/5/2  15:47
     */
    public void setExcel(String[] title, String name) {
        File file = new File(Folders.APP_PATH + projectName + "/" + Folders.PICTURE_DATA);
        ExcelUtil.makeDir(file);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        fileName = file.toString() + name + "-" + time + ".xls";
        ExcelUtil.initExcel(fileName, title, name + "表");
    }

    /**
     * 设置表格
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/5/2  15:47
     */
    public void setExcel2(String[] title, String name) {

        ExcelUtil.initExcel(fileName, title, name + "表");
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
