package com.me.pipelinedetectionapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.drainagemonitoring.greendao.DaoSession;
import com.example.drainagemonitoring.greendao.DetectionDbDao;
import com.me.pipelinedetectionapp.R;
import com.me.pipelinedetectionapp.bean.DetectionDb;
import com.me.pipelinedetectionapp.config.MyApplication;
import com.me.pipelinedetectionapp.utils.DateTimeUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author linshen
 */
public class TestingTableActivity extends AppCompatActivity {

    @BindView(R.id.patrol_return)
    TextView patrolReturn;
    @BindView(R.id.roadName)
    EditText roadName;
    @BindView(R.id.lineDot)
    EditText lineDot;
    @BindView(R.id.connectionPoint)
    EditText connectionPoint;
    @BindView(R.id.startingPointOrigin)
    EditText startingPointOrigin;
    @BindView(R.id.startingPointEnd)
    EditText startingPointEnd;
    @BindView(R.id.folwDirection)
    Spinner folwDirection;
    @BindView(R.id.pipeDiameter)
    EditText pipeDiameter;
    @BindView(R.id.pipe)
    Spinner pipe;
    @BindView(R.id.type)
    Spinner type;
    @BindView(R.id.inspectDate)
    EditText inspectDate;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.imageName)
    EditText imageName;
    private Unbinder bundle;
    private String id;
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
    private DaoSession daoSession;
    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_tabel);
        daoSession = MyApplication.getApplication().getDaoSession();
        bundle = ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        projectName = getIntent().getStringExtra("projectName");
        if (id != null) {
            List<DetectionDb> list = daoSession.getDetectionDbDao().queryBuilder().where(DetectionDbDao.Properties.Id.eq(id)).build().list();
            initSql(list);
        } else {
            String time = DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
            inspectDate.setText(time);
            List<DetectionDb> list = daoSession.getDetectionDbDao().queryBuilder().where(DetectionDbDao.Properties.ProjectName.eq(projectName)).orderDesc(DetectionDbDao.Properties.Id).build().list();
            if (list.size() != 0) {
                roadName.setText(list.get(list.size() - 1).getRoadName());
                initSql(list);
                imageName.setText("");
                lineDot.setText("");
                connectionPoint.setText("");

            }
        }
    }

    private void initSql(List<DetectionDb> list) {
        imageName.setText(list.get(0).getImageName() + "");
        roadName.setText(list.get(0).getRoadName() + "");
        lineDot.setText(list.get(0).getLineDot() + "");
        connectionPoint.setText(list.get(0).getConnectionPoint() + "");
        startingPointOrigin.setText(list.get(0).getStartingPointOrigin() + "");
        startingPointEnd.setText(list.get(0).getStartingPointEnd() + "");
        switch (list.get(0).getType()) {
            case "玻璃钢管":
                folwDirection.setSelection(0, true);
                break;
            case "钢管":
                folwDirection.setSelection(1, true);
                break;
            case "钢筋混凝土管":
                folwDirection.setSelection(2, true);
                break;
            case "双壁波纹管":
                folwDirection.setSelection(3, true);
                break;
            case "混凝土管":
                folwDirection.setSelection(4, true);
                break;
            case "塑料管":
                folwDirection.setSelection(5, true);
                break;
            case "石棉水泥管":
                folwDirection.setSelection(6, true);
                break;
            case "陶瓷管":
                folwDirection.setSelection(7, true);
                break;
            case "陶土管":
                folwDirection.setSelection(8, true);
                break;
            case "砖石沟":
                folwDirection.setSelection(9, true);
                break;
            case "铸铁管":
                folwDirection.setSelection(10, true);
                break;
            case "玻璃钢夹沙":
                folwDirection.setSelection(11, true);
                break;
            default:
                break;
        }

        switch (list.get(0).getType()) {
            case "顺流":
                pipe.setSelection(0, true);
                break;
            case "逆流":
                pipe.setSelection(1, true);
                break;
            default:
                break;
        }

        pipeDiameter.setText(list.get(0).getPipeDiameter() + "");
        inspectDate.setText(simpleDateFormat.format(list.get(0).getInspectDate()) + "");
        switch (list.get(0).getType()) {
            case "雨水管道":
                type.setSelection(0, true);
                break;
            case "污水管道":
                type.setSelection(1, true);
                break;
            case "雨污合流":
                type.setSelection(2, true);
                break;
            default:
                break;
        }

        remark.setText(list.get(0).getRemark() + "");
    }

    @OnClick({R.id.save, R.id.patrol_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.patrol_return:
                finish();
                break;
            case R.id.save:
                setSql();
                finish();
                break;
            default:
                break;
        }
    }

    private void setSql() {
        DetectionDb detectionDb = new DetectionDb();
        detectionDb.setProjectName(projectName);
        detectionDb.setImageName(imageName.getText().toString());
        detectionDb.setRoadName(roadName.getText().toString());
        detectionDb.setLineDot(lineDot.getText().toString());
        detectionDb.setConnectionPoint(connectionPoint.getText().toString());
        detectionDb.setStartingPointOrigin(startingPointOrigin.getText().toString());
        detectionDb.setStartingPointEnd(startingPointEnd.getText().toString());
        detectionDb.setFolwDirection(folwDirection.getSelectedItem().toString());
        detectionDb.setPipe(pipe.getSelectedItem().toString());
        detectionDb.setPipeDiameter(pipeDiameter.getText().toString());
        try {
            detectionDb.setInspectDate(simpleDateFormat.parse(inspectDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detectionDb.setType(type.getSelectedItem().toString());
        detectionDb.setRemark(remark.getText().toString());
        try {
            if (id == null) {
                daoSession.getDetectionDbDao().insert(detectionDb);
            } else {
                detectionDb.setId(Long.valueOf(id));
                daoSession.getDetectionDbDao().update(detectionDb);
            }

        } catch (Exception e) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bundle.unbind();
    }
}
