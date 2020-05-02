package com.me.pipelinedetectionapp.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.me.pipelinedetectionapp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author linshen on 2019-09-21.
 * 邮箱: 18475453284@163.com
 */
public class PersonalFragment extends Fragment {
    private View view;
    private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_personage, container, false);
            unbinder = ButterKnife.bind(this, view);

        }
        return view;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
