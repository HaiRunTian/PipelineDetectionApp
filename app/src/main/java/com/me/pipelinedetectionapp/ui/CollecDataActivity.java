package com.me.pipelinedetectionapp.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drainagemonitoring.greendao.DaoSession;
import com.example.drainagemonitoring.greendao.PipePSCheckDbDao;
import com.me.pipelinedetectionapp.R;
import com.me.pipelinedetectionapp.bean.PipePSCheckDb;
import com.me.pipelinedetectionapp.config.MyApplication;
import com.me.pipelinedetectionapp.utils.CameraUtils;
import com.me.pipelinedetectionapp.utils.DateTimeUtil;
import com.me.pipelinedetectionapp.utils.FileUtils;
import com.me.pipelinedetectionapp.utils.Folders;
import com.me.pipelinedetectionapp.utils.MyAlertDialog;
import com.me.pipelinedetectionapp.utils.SpinnerDropdownListManager;
import com.me.pipelinedetectionapp.view.DataInterface;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author HaiRun
 * @time 2020/5/2.12:00
 */
public class CollecDataActivity extends AppCompatActivity implements DataInterface, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

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
    @BindView(R.id.sp_grade)
    Spinner spGrade;
    @BindView(R.id.edt_hybrid)
    EditText edtHybrid;
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
    @BindView(R.id.edtDefecCode)
    EditText edtDefecCode;
    @BindView(R.id.edtwell)
    EditText edtwell;
    @BindView(R.id.edtwater)
    EditText edtwater;
    private String id;
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
    private DaoSession daoSession;
    private String projectName;
    //拍照相关 全部照片名字
    private String mPictureName = "";
    private int m_picIndex = 0;
    private File m_pictureName;
    private Uri fileUri;
    private Bitmap picBitmap;
    //单张照片名字
    private String pictureName;
    /**
     * 临时图片文件名数组
     */
    private List<String> picNames;
    /**
     * 临时图片文件数组
     */
    private List<File> picFiles;
    private ArrayList<HashMap<String, Object>> imageItem;
    //照片
    private List<String> m_listPicName;
    /**
     * //适配器
     */
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        bundle = ButterKnife.bind(this);
        daoSession = MyApplication.getApplication().getDaoSession();
        id = getIntent().getStringExtra("id");
        projectName = getIntent().getStringExtra("projectName");
        initView();
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
//                setChenkLength(list.get(0).getCheckLength());
            }
        }

    }

    /**
     * 初始化拍照区域
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/3/14  12:58
     */
    private void initView() {
        picFiles = new ArrayList<>();
        picNames = new ArrayList<>();
        imageItem = new ArrayList<>();
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        MyAlertDialog.showAlertDialog(this, "删除提示", "确定删除改照片？", "确定", "取消", true,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //从手机卡删除
                        FileUtils.getInstance().deleteFile(picFiles.get(position));
                        imageItem.remove(position);
                        picNames.remove(position);
                        picFiles.remove(position);
                        refreshGridviewAdapter();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (view.getId() != -1) {
        } else {
            viewPicture(position);
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
            setPipeType(list.get(0).getPipeType());
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
       /* //代码
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.defect));
        spCode.setAdapter(adapter);*/
        //等级
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.grade));
        spGrade.setAdapter(adapter);
      /*  //检查井问题
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.well));
        spWell.setAdapter(adapter);
        //雨水口问题
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.water));
        spWater.setAdapter(adapter);*/
        //其他问题
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.about));
        spAbout.setAdapter(adapter);
        //管类
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.type));
        edtPipeType.setAdapter(adapter);

    }

    @OnClick({R.id.save, R.id.patrol_return, R.id.btnAddPic, R.id.ibCode, R.id.ibwater, R.id.ibwell})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.patrol_return:
                finish();
                break;
            case R.id.save:
                insertSql();
                break;

            case R.id.btnAddPic:
                //拍照逻辑
                openCamera();
                break;

            case R.id.ibCode:
                showDialog(getResources().getStringArray(R.array.defect),edtDefecCode,"缺陷代码");
                break;

            case R.id.ibwell:
                showDialog(getResources().getStringArray(R.array.well),edtwell,"检查井问题");
                break;

            case R.id.ibwater:
                showDialog(getResources().getStringArray(R.array.water),edtwater,"雨水口问题");
                break;
            default:
                break;
        }
    }

    /**
     * 拍照
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/3/18  16:29
     */
    private void openCamera() {
        if (!hasPermission()) {
            return;
        }
        if (id != null) {
            //照片转成
            List<String> list = Arrays.asList(getImg().split("#"));
            if (list.size() != 0) {
                m_picIndex = list.size();
            }
        }
        m_picIndex++;
        //照片名字
        String name = Folders.APP_PATH + "/" + projectName + "/" + Folders.PICTURE_DATA + getStartPoint() + "_" + getEndtPoint() + "_" + m_picIndex + ".jpg";
        m_pictureName = new File(name);
        m_pictureName.getParentFile().mkdirs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //步骤二：Android 7.0及以上获取文件 Uri
            fileUri = FileProvider.getUriForFile(this, "com.me.pipelinedetectionapp", m_pictureName);
        } else {
            //步骤三：获取文件Uri
            fileUri = Uri.fromFile(m_pictureName);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CameraUtils.PHOTO_REQUEST_TAKEPHOTO);
    }

    /**
     * 检查权限
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/3/19  11:41
     */
    private boolean hasPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, CameraUtils.PHOTO_REQUEST_TAKEPHOTO);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 查看图片
     */
    private void viewPicture(int position) {
        if (picFiles.get(position) != null) {
            File file = picFiles.get(position);
            //打开照片查看
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri _uri;
            if (Build.VERSION.SDK_INT >= 24) {
                _uri = FileProvider.getUriForFile(getApplicationContext(), "com.me.pipelinedetectionapp", file);
            } else {
                _uri = Uri.fromFile(file);
            }
            intent.setDataAndType(_uri, CameraUtils.IMAGE_UNSPECIFIED);
            startActivity(intent);
        }
    }


    //手机拍照回调
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case CameraUtils.PHOTO_REQUEST_TAKEPHOTO:
                    picBitmap = BitmapFactory.decodeFile(m_pictureName.getAbsolutePath());
                    picBitmap = CameraUtils.comp(picBitmap);
                    if (picBitmap != null) {
                        //拍摄返回的图片name
                        pictureName = m_pictureName.getName();
                        picNames.add(pictureName);
                        picFiles.add(m_pictureName);
                        HashMap<String, Object> _map = new HashMap<>();
                        _map.put("itemImage", picBitmap);
                        _map.put("picName", pictureName);
                        imageItem.add(_map);
                        refreshGridviewAdapter();
                    } else {
                        Toast.makeText(this, "图片名不允许带特殊符号", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 更新照片
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/5/3  10:56
     */
    private void refreshGridviewAdapter() {
        if (simpleAdapter == null) {
            simpleAdapter = new SimpleAdapter(this, imageItem, R.layout.layout_griditem_addpic,
                    new String[]{"itemImage", "picName"}, new int[]{R.id.imageView1, R.id.tvPicName});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(final View view, final Object data, String textRepresentation) {
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {//绑定视图
                                ImageView i = (ImageView) view;
                                i.setImageBitmap((Bitmap) data);
                            }
                        });
                        return true;
                    } else if (view instanceof TextView) {
                        TextView tv = (TextView) view;
                        tv.setText(textRepresentation);
                        return true;
                    }

                    return false;
                }
            });
        }
        //主线程绑定adapter刷新数据
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gridView.setAdapter(simpleAdapter);
                simpleAdapter.notifyDataSetChanged();
            }
        });
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
        return edtDefecCode.getText().toString();
    }

    /**
     * 设置缺陷代码
     *
     * @param code
     */
    @Override
    public void setDefectCode(String code) {
        edtDefecCode.setText(code);
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
        return edtwell.getText().toString();
    }

    /**
     * 设置检查井问题
     *
     * @param well
     */
    @Override
    public void setWell(String well) {
        edtwell.setText(well);
    }

    /**
     * 获取雨水口问题
     *
     * @return
     */
    @Override
    public String getWater() {
        return edtwater.getText().toString();
    }

    /**
     * 设置雨水口问题
     *
     * @param water
     */
    @Override
    public void setWater(String water) {
        edtwater.setText(water);
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
        String jointPictureName = "";
        if (picFiles.size() == 0) {
            jointPictureName = "";
        } else if (picFiles.size() == 1) {
            jointPictureName = picFiles.get(0).getName();
        } else {
            for (File _picFile : picFiles) {
                jointPictureName += _picFile.getName() + "#";
            }
            jointPictureName = jointPictureName.substring(0, jointPictureName.length() - 1);
        }
        return jointPictureName;
    }

    /**
     * 设置照片
     *
     * @param img
     */
    @Override
    public void setImg(String img) {
        if (img != null) {
            m_listPicName = getPicturefromReSet(img);
            if (m_listPicName.size() > 0) {
                m_picIndex = m_listPicName.size();
                for (String _s : m_listPicName) {
                    boolean isExsit = FileUtils.getInstance().isFileExsit(Folders.APP_PATH + "/" + projectName + "/" + Folders.PICTURE_DATA + _s);
                    if (!isExsit) {
                        Toast.makeText(this, "此图片不存在 =" + _s, Toast.LENGTH_SHORT).show();
                        continue;
                    }
                    picNames.add(_s);
                    picFiles.add(new File(Folders.APP_PATH + "/" + projectName + "/" + Folders.PICTURE_DATA + _s));
                    Bitmap _bitmap = CameraUtils.getimage(Folders.APP_PATH + "/" + projectName + "/" + Folders.PICTURE_DATA + _s);
                    HashMap<String, Object> _map = new HashMap<>();
                    _map.put("itemImage", _bitmap);
                    _map.put("picName", _s);
                    imageItem.add(_map);
                    refreshGridviewAdapter();
                }
            }
        }
    }

    //获取
    public List<String> getPicturefromReSet(String photoName) {
        List<String> _list = new ArrayList<>();
        if (!photoName.isEmpty()) {
            _list.addAll(Arrays.asList(photoName.split("#")));
        }
        return _list;
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

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 多选
     *
     * @Params :
     * @author :HaiRun
     * @date :2019/7/10  15:12
     */
    private void showDialog(final String[] data, final TextView textView, String title) {
        textView.setText("");
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(title);
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        final boolean[] selectItems = new boolean[data.length];
        for (int i = 0; i < data.length; i++) {
            selectItems[i] = false;
            map.put(i, false);
        }

        /**
         * 第一个参数指定我们要显示的一组下拉多选框的数据集合
         * 第二个参数代表哪几个选项被选择，如果是null，则表示一个都不选择，如果希望指定哪一个多选选项框被选择，
         * 需要传递一个boolean[]数组进去，其长度要和第一个参数的长度相同，例如 {true, false, false, true};
         * 第三个参数给每一个多选项绑定一个监听器
         */
        builder.setMultiChoiceItems(data, selectItems, new DialogInterface.OnMultiChoiceClickListener() {
            StringBuffer sb = new StringBuffer(100);

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    //选择的选项保存到sb中
//                    sb.append(data[which] + "+");
                    map.put(which, true);
                }
//                String s = sb.toString();
//                String data = s.substring(0, s.length() - 1);
//                textView.setText(data);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText("");
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer sb = new StringBuffer(100);
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i)) {
                        sb.append(data[i] + "+");
                    }
                }
                String s = sb.toString();
                String data = s.substring(0, s.length() - 1);
                textView.setText(data);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
