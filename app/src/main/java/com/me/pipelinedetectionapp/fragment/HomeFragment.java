package com.me.pipelinedetectionapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.drainagemonitoring.greendao.ProjectDbDao;
import com.me.pipelinedetectionapp.R;
import com.me.pipelinedetectionapp.adapter.ProjectAdapter;
import com.me.pipelinedetectionapp.bean.ProjectDb;
import com.me.pipelinedetectionapp.config.MyApplication;
import com.me.pipelinedetectionapp.ui.ProjectActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: linshen on 2019-06-20.
 * 邮箱: 18475453284@163.com
 * 主页
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.monitor)
    LinearLayout monitor;
    @BindView(R.id.sign)
    LinearLayout sign;
    @BindView(R.id.manage)
    LinearLayout manage;
    @BindView(R.id.project_rv)
    RecyclerView projectRv;
    private View view;
    private Unbinder unbinder;
    private ProjectAdapter projectAdapter;
    private List<ProjectDb> arrayList;
    private ProjectDbDao projectDbDao = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            unbinder = ButterKnife.bind(this, view);
            projectDbDao = MyApplication.getApplication().getDaoSession().getProjectDbDao();
            initView();
            initData();
        }
        return view;
    }

    private void initData() {
        arrayList = projectDbDao.queryBuilder().orderDesc(ProjectDbDao.Properties.Id).build().list();
        projectAdapter = new ProjectAdapter(getActivity(),arrayList);
        projectRv.setAdapter(projectAdapter);
        projectAdapter.notifyDataSetChanged();
    }

    private void initView() {
        arrayList = new ArrayList<>();
        LinearLayoutManager _linearLayoutManager = new LinearLayoutManager(getActivity());
        _linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        projectRv.setLayoutManager(_linearLayoutManager);
    }
    @OnClick({R.id.monitor, R.id.manage, R.id.sign})
    public void onClick(View v) {
        switch (v.getId()) {
            //新建工程
            case R.id.monitor:
                startActivity(new Intent(getActivity(), ProjectActivity.class));
                break;
            case R.id.manage:

                break;
            case R.id.sign:
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    @Override
    public void onStart() {
        super.onStart();
        arrayList = projectDbDao.queryBuilder().orderDesc(ProjectDbDao.Properties.InspectDate).list();
        if (arrayList.size()!=0) {
            projectAdapter.setContentList(arrayList);
            projectAdapter.notifyDataSetChanged();
        }
    }
}
