package com.me.pipelinedetectionapp.config;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.drainagemonitoring.greendao.DaoMaster;
import com.example.drainagemonitoring.greendao.DaoSession;

/**
 * @author linshen on 2019-06-28.
 * 邮箱: 18475453284@163.com
 */
public class MyApplication extends Application {
    private static DaoSession daoSession;
    private static MyApplication application;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initGreenDao();
    }
    public static MyApplication getApplication() {
        return application;
    }
    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "qv-db", null);
        db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public  DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
