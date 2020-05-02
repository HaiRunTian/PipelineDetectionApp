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

import com.me.pipelinedetectionapp.R;
import com.me.pipelinedetectionapp.bean.DetectionDb;
import com.me.pipelinedetectionapp.config.MyApplication;
import com.me.pipelinedetectionapp.ui.TestingTableActivity;
import com.me.pipelinedetectionapp.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author linshen on 2019-08-28.
 * 邮箱: 18475453284@163.com
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private List<DetectionDb> _arrayList;
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS);

    public FunctionAdapter(Context context, List<DetectionDb> arrayList) {
        this.context = context;
        this._arrayList = arrayList;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setContentList(List<DetectionDb> arrayList) {
        this._arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View _view = mInflater.inflate(R.layout.function_item, parent, false);
        ViewHolder _holder = new ViewHolder(_view);
        return _holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (_arrayList.size() != 0) {
            String areaName = _arrayList.get(position).getImageName();
            String mans = _arrayList.get(position).getLineDot() + "-" + _arrayList.get(position).getConnectionPoint();
            String inspectDate = simpleDateFormat.format(_arrayList.get(position).getInspectDate());
            String id = String.valueOf(_arrayList.get(position).getId());
            String projectName = _arrayList.get(position).getProjectName();
            viewHolder.tvAreaName.setText(_arrayList.get(position).getId() + " -- " + areaName);
            viewHolder.tvMans.setText(mans);
            viewHolder.tvDate.setText(inspectDate);
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TestingTableActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("projectName", projectName);
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
                            DetectionDb detectionDb = new DetectionDb();
                            detectionDb.setId(Long.valueOf(id));
                            MyApplication.getApplication().getDaoSession().getDetectionDbDao().delete(detectionDb);
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
