package com.me.pipelinedetectionapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.me.pipelinedetectionapp.R;
import com.me.pipelinedetectionapp.bean.ProjectDb;
import com.me.pipelinedetectionapp.config.MyApplication;
import com.me.pipelinedetectionapp.utils.DateTimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author linshen
 */
public class ProjectActivity extends AppCompatActivity {

    @BindView(R.id.projectName)
    EditText projectName;
    @BindView(R.id.projectNumber)
    EditText projectNumber;
    @BindView(R.id.areaName)
    EditText areaName;
    @BindView(R.id.inspectorName)
    EditText inspectorName;
    @BindView(R.id.registrarName)
    EditText registrarName;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.return_tv)
    TextView returnTv;
    @BindView(R.id.dataTime)
    EditText dataTime;
    @BindView(R.id.inspector)
    Spinner inspector;
    @BindView(R.id.spCheckMode)
    Spinner spCheckMode;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        unbinder = ButterKnife.bind(this);
        String time = DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);
        dataTime.setText(time);
    }

    @OnClick({R.id.tvSubmit, R.id.return_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSubmit:
                if (TextUtils.isEmpty(projectName.getText()) || TextUtils.isEmpty(projectNumber.getText()) || TextUtils.isEmpty(inspector.getSelectedItem().toString()) ||
                        TextUtils.isEmpty(inspectorName.getText()) || TextUtils.isEmpty(registrarName.getText()) || TextUtils.isEmpty(areaName.getText())) {
                    Toast.makeText(this, "不可为空", Toast.LENGTH_SHORT).show();
                } else {
                    ProjectDb projectDb = new ProjectDb();
                    projectDb.setProjectName(projectName.getText().toString());
                    projectDb.setProjectNumber(projectNumber.getText().toString());
                    projectDb.setInspectorName(inspector.getSelectedItem().toString());
                    projectDb.setRegistrarName(inspectorName.getText().toString());
                    projectDb.setAreaName(registrarName.getText().toString());
                    projectDb.setTestMethod(areaName.getText().toString());
                    projectDb.setInspectDate(dataTime.getText().toString());
                    projectDb.setProjectMode(spCheckMode.getSelectedItem().toString());
                    try {
                        //插入表格
                        MyApplication.getApplication().getDaoSession().getProjectDbDao().insert(projectDb);
                    } catch (Exception e) {
                        Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.return_tv:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
