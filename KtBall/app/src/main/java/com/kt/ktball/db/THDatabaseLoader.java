package com.kt.ktball.db;

import android.database.sqlite.SQLiteDatabase;

import com.kt.ktball.App;
import com.ktfootball.www.dao.DaoMaster;
import com.ktfootball.www.dao.DaoSession;

/**
 * Created by jy on 16/4/16.
 */
public class THDatabaseLoader {
    private static final String DATABASE_NAME = "KTSchoolFootBall-db";
    private static DaoSession daoSession;
    // 虚方法，可以无实体内容
    public static void init() {
        THDevOpenHelper helper = new THDevOpenHelper(App.getInstance(), DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}