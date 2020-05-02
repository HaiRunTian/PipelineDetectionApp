package com.me.pipelinedetectionapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drainagemonitoring.greendao.DaoSession;
import com.example.drainagemonitoring.greendao.PipePSCheckDbDao;
import com.me.pipelinedetectionapp.R;
import com.me.pipelinedetectionapp.bean.PipePSCheckDb;
import com.me.pipelinedetectionapp.config.MyApplication;
import com.me.pipelinedetectionapp.utils.DateTimeUtil;
import com.me.pipelinedetectionapp.utils.SpinnerDropdownListManager;
import com.me.pipelinedetectionapp.view.DataInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author HaiRun
 * @time 2020/5/2.12:00
 */
public class CollecDataActivity extends AppCompatActivity implements DataInterface {

    @BindView(R.id.patrol_return)
    TextView patrolReturn;
    @BindView(R.id.imageName)
    EditText imageName;
    @BindView(R.id.roadName)
    EditText roadName;
    @BindView(R.id.lineDot)
    EditText lineDot;
    @BindView(R.id.connectionPoint)
    EditText connectionPoint;
    @BindView(R.id.edtCheckLength)
    EditText edtCheckLength;
    @BindView(R.id.folwDirection)
    Spinner folwDirection;
    @BindView(R.id.edtConcentration)
    EditText edtConcentration;
    @BindView(R.id.pipe)
    Spinner pipe;
    @BindView(R.id.pipeDiameter)
    EditText pipeDiameter;
    @BindView(R.id.defectLength)
    EditText defectLength;
    @BindView(R.id.sp_code)
    Spinner spCode;
    @BindView(R.id.sp_grade)
    Spinner spGrade;
    @BindView(R.id.edt_hybrid)
    EditText edtHybrid;
    @BindView(R.id.sp_well)
    Spinner spWell;
    @BindView(R.id.sp_water)
    Spinner spWater;
    @BindView(R.id.sp_about)
    Spinner spAbout;
    @BindView(R.id.edt_locat)
    EditText edtLocat;
    @BindView(R.id.inspectDate)
    EditText inspectDate;
    @BindView(R.id.btnAddPic)
    Button btnAddPic;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.save)
    Button save;
    Unbinder bundle;
    @BindView(R.id.edtPipeType)
    Spinner edtPipeType;
    private String id;
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
    private DaoSession daoSession;
    private String projectName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        bundle = ButterKnife.bind(this);
        daoSession = MyApplication.getApplication().getDaoSession();
        id = getIntent().getStringExtra("id");
        projectName = getIntent().getStringExtra("projectName");
        initSpinnerAdapter();
        //查询
        if (id != null) {
            List<PipePSCheckDb> list = daoSession.getPipePSCheckDbDao().queryBuilder().where(PipePSCheckDbDao.Properties.Id.eq(id)).build().list();
            initViewBySql(list);
        } else {
            //新增
            //设置时间
            String time = DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
            inspectDate.setText(time);
            List<PipePSCheckDb> list = daoSession.getPipePSCheckDbDao().queryBuilder().where(PipePSCheckDbDao.Properties.ProjectName.eq(projectName)).orderDesc(PipePSCheckDbDao.Properties.Id).build().list();
            if (list.size() != 0) {
                roadName.setText(list.get(0).getRoadName());
                setFlow(list.get(0).getFlow());
                setPipeMaterials(list.get(0).getPipeMaterials());
                setPipeSize(list.get(0).getPipeSize());
                setFullness(list.get(0).getFullness());
                setChenkLength(list.get(0).getCheckLength());
            }
        }

    }

    /**
     * 查询初始化数据
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/5/2  16:12
     */
    private void initViewBySql(List<PipePSCheckDb> list) {
        try {
            setImaNum(list.get(0).getImageName());
            setRoad(list.get(0).getRoadName());
            setStartPoint(list.get(0).getLineDot());
            setEndPoint(list.get(0).getConnectionPoint());
            setChenkLength(list.get(0).getCheckLength());
            setFlow(list.get(0).getFlow());
            setFullness(list.get(0).getFullness());
            setPipeMaterials(list.get(0).getPipeMaterials());
            setPipeSize(list.get(0).getPipeSize());
            setDefectLength(list.get(0).getDefectLength());
            setDefectCode(list.get(0).getDefectCode());
            setDefectGrade(list.get(0).getDefectGrade());
            setHybrid(list.get(0).getHybrid());
            setWell(list.get(0).getWellQuestion());
            setWater(list.get(0).getWaterQuestion());
            setAbout(list.get(0).getAboutQuestion());
            setImg(list.get(0).getPicture());
            setLocal(list.get(0).getLocal());
            setRemark(list.get(0).getRemark());
            inspectDate.setText(simpleDateFormat.format(list.get(0).getInspectDate()));
        } catch (Exception e) {
            Toast.makeText(CollecDataActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 初始化Adapter数据
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/5/2  15:57
     */
    private void initSpinnerAdapter() {
        ArrayAdapter<String> adapter = null;
        List<String> list = new ArrayList<>();
        //流向
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.pipe));
        folwDirection.setAdapter(adapter);
        //管材
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.folwDirection));
        pipe.setAdapter(adapter);
        //代码
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.defect));
        pipe.setAdapter(adapter);
        //代码
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.defect));
        spCode.setAdapter(adapter);
        //等级
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.grade));
        spGrade.setAdapter(adapter);
        //检查井问题
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.well));
        spWell.setAdapter(adapter);
        //雨水口问题
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.water));
        spWater.setAdapter(adapter);
        //其他问题
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.about));
        spAbout.setAdapter(adapter);
        //管类
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.type));
        edtPipeType.setAdapter(adapter);

    }

    @OnClick({R.id.save, R.id.patrol_return, R.id.btnAddPic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.patrol_return:
                break;
            case R.id.save:
                insertSql();
                break;

            case R.id.btnAddPic:

                break;
            default:
                break;
        }
    }

    /**
     * 保存数据到数据库
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/5/2  16:38
     */
    private void insertSql() {
        PipePSCheckDb pipePSCheckDb = new PipePSCheckDb();
        pipePSCheckDb.setProjectName(projectName);
        pipePSCheckDb.setImageName(getImaNum());
        pipePSCheckDb.setRoadName(getRoad());
        pipePSCheckDb.setLineDot(getStartPoint());
        pipePSCheckDb.setConnectionPoint(getEndtPoint());
        pipePSCheckDb.setCheckLength(getCheckLength());
        pipePSCheckDb.setFlow(getFlow());
        pipePSCheckDb.setFullness(getFullness());
        pipePSCheckDb.setPipeMaterials(getPipeMaterials());
        pipePSCheckDb.setPipeSize(getPipeSize());
        pipePSCheckDb.setDefectLength(getDefectLength());
        pipePSCheckDb.setDefectCode(getDefectCode());
        pipePSCheckDb.setDefectGrade(getDefectGrade());
        pipePSCheckDb.setHybrid(getHybrid());
        pipePSCheckDb.setWellQuestion(getWell());
        pipePSCheckDb.setWaterQuestion(getWater());
        pipePSCheckDb.setAboutQuestion(getAbout());
        pipePSCheckDb.setPicture(getImg());
        pipePSCheckDb.setLocal(getLocal());
        pipePSCheckDb.setRemark(getRemark());
        pipePSCheckDb.setPipeType(getPipeType());
        try {
            pipePSCheckDb.setInspectDate(simpleDateFormat.parse(inspectDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            if (id == null) {
                daoSession.getPipePSCheckDbDao().insert(pipePSCheckDb);
            } else {
                pipePSCheckDb.setId(Long.valueOf(id));
                daoSession.getPipePSCheckDbDao().update(pipePSCheckDb);
            }
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bundle.unbind();
    }

    /**
     * 获取影像编号
     *
     * @return
     */
    @Override
    public String getImaNum() {
        return imageName.getText().toString() + "";
    }

    /**
     * 设置影像编号
     *
     * @param imaNum
     */
    @Override
    public void setImaNum(String imaNum) {
        imageName.setText(imaNum);
    }

    /**
     * 获取区域
     *
     * @return
     */
    @Override
    public String getArea() {
        return null;
    }

    /**
     * 设置区域
     *
     * @param area
     */
    @Override
    public void setArea(String area) {

    }

    /**
     * 获取道路
     *
     * @return
     */
    @Override
    public String getRoad() {
        return roadName.getText().toString() + "";
    }

    /**
     * 设置道路
     *
     * @param road
     */
    @Override
    public void setRoad(String road) {
        roadName.setText(road);
    }

    /**
     * 获取起点编号
     *
     * @return
     */
    @Override
    public String getStartPoint() {
        return lineDot.getText().toString() + "";
    }

    /**
     * 设置起点编号
     *
     * @param point
     */
    @Override
    public void setStartPoint(String point) {
        lineDot.setText(point);
    }

    /**
     * 获取终点编号
     *
     * @return
     */
    @Override
    public String getEndtPoint() {
        return connectionPoint.getText().toString() + "";
    }

    /**
     * 设置终点编号
     *
     * @param point
     */
    @Override
    public void setEndPoint(String point) {
        connectionPoint.setText(point);
    }

    /**
     * 获取检测长度
     *
     * @return
     */
    @Override
    public String getCheckLength() {
        return edtCheckLength.getText().toString() + "";
    }

    /**
     * 设置检测长度
     *
     * @param length
     */
    @Override
    public void setChenkLength(String length) {
        edtCheckLength.setText(length);
    }

    /**
     * 获取流向
     *
     * @return
     */
    @Override
    public String getFlow() {
        return folwDirection.getSelectedItem().toString();
    }

    /**
     * 设置流向
     *
     * @param flow
     */
    @Override
    public void setFlow(String flow) {
        SpinnerDropdownListManager.setSpinnerItemSelectedByValue(folwDirection, flow);
    }

    /**
     * 获取充满度
     *
     * @return
     */
    @Override
    public String getFullness() {
        return edtConcentration.getText().toString() + "";
    }

    /**
     * 设置充满度
     *
     * @param fullness
     */
    @Override
    public void setFullness(String fullness) {
        edtConcentration.setText(fullness);
    }

    /**
     * 获取管类
     *
     * @return
     */
    @Override
    public String getPipeType() {
        return edtPipeType.getSelectedItem().toString();
    }

    /**
     * 设置管类
     *
     * @param type
     */
    @Override
    public void setPipeType(String type) {
        SpinnerDropdownListManager.setSpinnerItemSelectedByValue(edtPipeType, type);
    }

    /**
     * 获取管材
     *
     * @return
     */
    @Override
    public String getPipeMaterials() {
        return pipe.getSelectedItem().toString();
    }

    /**
     * 设置管材
     *
     * @param pipeMaterials
     */
    @Override
    public void setPipeMaterials(String pipeMaterials) {
        pipeDiameter.setText(pipeMaterials);
    }

    /**
     * 获取管渠规格
     *
     * @return
     */
    @Override
    public String getPipeSize() {
        return pipeDiameter.getText().toString() + "";
    }

    /**
     * 设置管渠规格
     *
     * @param pipeSize
     */
    @Override
    public void setPipeSize(String pipeSize) {
        pipeDiameter.setText(pipeSize);
    }

    /**
     * 获取缺陷距离
     *
     * @return
     */
    @Override
    public String getDefectLength() {
        return defectLength.getText().toString() + "";
    }

    /**
     * 设置缺陷距离
     *
     * @param length
     */
    @Override
    public void setDefectLength(String length) {
        defectLength.setText(length);
    }

    /**
     * 获取缺陷代码
     *
     * @return
     */
    @Override
    public String getDefectCode() {
        return spCode.getSelectedItem().toString();
    }

    /**
     * 设置缺陷代码
     *
     * @param code
     */
    @Override
    public void setDefectCode(String code) {
        SpinnerDropdownListManager.setSpinnerItemSelectedByValue(spCode, code);
    }

    /**
     * 获取缺陷等级
     *
     * @return
     */
    @Override
    public String getDefectGrade() {
        return spGrade.getSelectedItem().toString() + "";
    }

    /**
     * 设置缺陷等级
     *
     * @param grade
     */
    @Override
    public void setDefectGrade(String grade) {
        SpinnerDropdownListManager.setSpinnerItemSelectedByValue(spGrade, grade);
    }

    /**
     * 获取雨污混接情况
     *
     * @return
     */
    @Override
    public String getHybrid() {
        return edtHybrid.getText().toString() + "";
    }

    /**
     * 设置污混接情况
     *
     * @param hybrid
     */
    @Override
    public void setHybrid(String hybrid) {
        edtHybrid.setText(hybrid);
    }

    /**
     * 获取检查井问题
     *
     * @return
     */
    @Override
    public String getWell() {
        return spWell.getSelectedItem().toString();
    }

    /**
     * 设置检查井问题
     *
     * @param well
     */
    @Override
    public void setWell(String well) {
        SpinnerDropdownListManager.setSpinnerItemSelectedByValue(spWell, well);
    }

    /**
     * 获取雨水口问题
     *
     * @return
     */
    @Override
    public String getWater() {
        return spWater.getSelectedItem().toString();
    }

    /**
     * 设置雨水口问题
     *
     * @param water
     */
    @Override
    public void setWater(String water) {
        SpinnerDropdownListManager.setSpinnerItemSelectedByValue(spWell, water);
    }

    /**
     * 获取其他问题
     *
     * @return
     */
    @Override
    public String getAbout() {
        return spAbout.getSelectedItem().toString();
    }

    /**
     * 设置雨水口问题
     *
     * @param about
     */
    @Override
    public void setAbout(String about) {
        SpinnerDropdownListManager.setSpinnerItemSelectedByValue(spAbout, about);
    }

    /**
     * 获取照片
     *
     * @return
     */
    @Override
    public String getImg() {
        return null;
    }

    /**
     * 设置照片
     *
     * @param img
     */
    @Override
    public void setImg(String img) {

    }

    /**
     * 获取位置
     *
     * @return
     */
    @Override
    public String getLocal() {
        return edtLocat.getText().toString() + "";
    }

    /**
     * 设置位置
     *
     * @param local
     */
    @Override
    public void setLocal(String local) {
        edtLocat.setText(local);
    }

    /**
     * 获取备注
     *
     * @return
     */
    @Override
    public String getRemark() {
        return remark.getText().toString();
    }

    /**
     * 设置备注
     *
     * @param s
     */
    @Override
    public void setRemark(String s) {
        remark.setText(s);
    }
}
