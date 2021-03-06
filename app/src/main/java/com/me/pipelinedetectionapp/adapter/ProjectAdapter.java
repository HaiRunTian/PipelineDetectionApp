package com.me.pipelinedetectionapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drainagemonitoring.greendao.DetectionDbDao;
import com.example.drainagemonitoring.greendao.PipePSCheckDbDao;
import com.example.drainagemonitoring.greendao.ProjectDbDao;
import com.me.pipelinedetectionapp.R;
import com.me.pipelinedetectionapp.bean.PipePSCheckDb;
import com.me.pipelinedetectionapp.bean.ProjectDb;
import com.me.pipelinedetectionapp.config.MyApplication;
import com.me.pipelinedetectionapp.ui.FunctionPatrolActivity;
import com.me.pipelinedetectionapp.utils.Folders;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author linshen on 2019-09-23.
 * 邮箱: 18475453284@163.com
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<ProjectDb> _arrayList;

    public ProjectAdapter(Context context, List<ProjectDb> arrayList) {
        this.context = context;
        this._arrayList = arrayList;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setContentList(List<ProjectDb> arrayList) {
        _arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View _view = mInflater.inflate(R.layout.project_item, parent, false);
        ViewHolder _holder = new ViewHolder(_view);
        return _holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.ViewHolder viewHolder, int position) {
        if (_arrayList.size() != 0) {
            String projectName = _arrayList.get(position).getProjectName();
            String projectNumber = _arrayList.get(position).getProjectNumber();
            String inspectDate = _arrayList.get(position).getInspectDate();
            String inspectorName = _arrayList.get(position).getInspectorName();
            String registrarName = _arrayList.get(position).getRegistrarName();
            String areaName = _arrayList.get(position).getAreaName();
            String testMethod = _arrayList.get(position).getTestMethod();
            String checkMode = _arrayList.get(position).getProjectMode();
            String id = String.valueOf(_arrayList.get(position).getId());
            viewHolder.tvAreaName.setText( projectName);
            viewHolder.tvMans.setText(projectNumber);
            viewHolder.tvDate.setText(inspectDate);
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FunctionPatrolActivity.class);
                    intent.putExtra("projectName", projectName);
                    intent.putExtra("projectNumber", projectNumber);
                    intent.putExtra("inspectorName", inspectorName);
                    intent.putExtra("registrarName", registrarName);
                    intent.putExtra("areaName", areaName);
                    intent.putExtra("testMethod", testMethod);
                    intent.putExtra("checkMode",checkMode);
                    context.startActivity(intent);
                }
            });

            viewHolder.ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("弹出警告框");
                    builder.setMessage("确定删除吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyApplication.getApplication().getDaoSession().getProjectDbDao().queryBuilder().
                                    where(ProjectDbDao.Properties.ProjectName.eq(projectName)).buildDelete().executeDeleteWithoutDetachingEntities();
                            if (checkMode.equals(Folders.PRJ_MODE1)){
                                MyApplication.getApplication().getDaoSession().getPipePSCheckDbDao().queryBuilder().
                                        where(PipePSCheckDbDao.Properties.ProjectName.eq(projectName)).buildDelete().executeDeleteWithoutDetachingEntities();
                            }else {
                                MyApplication.getApplication().getDaoSession().getDetectionDbDao().queryBuilder().
                                        where(DetectionDbDao.Properties.ProjectName.eq(projectName)).buildDelete().executeDeleteWithoutDetachingEntities();
                            }
                            _arrayList.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (_arrayList != null) {
            return _arrayList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_areaName)
        TextView tvAreaName;
        @BindView(R.id.tv_mans)
        TextView tvMans;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.ll)
        LinearLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
