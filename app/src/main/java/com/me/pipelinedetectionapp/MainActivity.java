package com.me.pipelinedetectionapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.me.pipelinedetectionapp.fragment.HomeFragment;
import com.me.pipelinedetectionapp.fragment.PersonalFragment;
import com.me.pipelinedetectionapp.utils.PermissionUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author linshen
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.main_fl)
    FrameLayout mainFl;
    @BindView(R.id.homo_rb)
    RadioButton homoRb;
    @BindView(R.id.requisition_rb)
    RadioButton requisitionRb;
    @BindView(R.id.requisition_rg)
    RadioGroup requisitionRg;
    private Unbinder unbinder;


    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private HomeFragment homeFragment;
    private PersonalFragment personalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.initPermission(this, new PermissionUtils.PermissionHolder());
        unbinder = ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        homoRb.setChecked(true);
        switchFragment(1);
        requisitionRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.requisition_rb:
                switchFragment(0);
                break;
            case R.id.homo_rb:
                switchFragment(1);
                break;
            default:
                break;
        }
    }

    private void switchFragment(int id) {
        if (mManager == null) {
            mManager = getSupportFragmentManager();
        }
        mTransaction = mManager.beginTransaction();
        hideFragment(mTransaction);
        showFragment(id);
        mTransaction.commit();
    }

    private void showFragment(int id) {
        switch (id) {
            case 0:
                if (personalFragment == null) {
                    personalFragment = new PersonalFragment();
                    mTransaction.add(R.id.main_fl, personalFragment);
                } else {
                    hideFragment(mTransaction);
                    mTransaction.show(personalFragment);
                }
                break;
            case 1:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    mTransaction.add(R.id.main_fl, homeFragment);
                } else {
                    hideFragment(mTransaction);
                    mTransaction.show(homeFragment);
                }
                break;
            default:
                break;
        }
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (personalFragment != null) {
            transaction.hide(personalFragment);
        }
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
