package com.me.pipelinedetectionapp.utils;

import android.database.Cursor;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairun
 */

public class SpinnerDropdownListManager {

    /**
     * 数组转list
     *
     * @param arrays
     * @return
     */
    public static List<String> getData(String[] arrays) {
        List<String> _list = new ArrayList<>();
        for (String item : arrays) {
            _list.add(item);
        }
        return _list;
    }


    /**
     * 根据值, 设置spinner默认选中:
     *
     * @param spinner 要设置的Spinner
     * @param value   要匹配的值
     */
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        //得到SpinnerAdapter对象
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
//                spinner.setSelection(i,true);// 默认选中项
                // 默认选中项
                spinner.setSelection(i);
                break;
            }
        }
    }
}
