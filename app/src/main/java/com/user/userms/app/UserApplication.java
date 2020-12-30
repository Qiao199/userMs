package com.user.userms.app;

import android.app.Application;

import com.user.userms.dao.UserDao;
import com.user.userms.dao.db.UserSQLite;

public class UserApplication extends Application {
    public  UserDao userDao;//数据表操作DAO

    @Override
    public void onCreate() {
        super.onCreate();
        userDao = new UserSQLite(this);
    }
}
