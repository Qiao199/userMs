package com.user.userms.dao.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.user.userms.dao.UserDao;
import com.user.userms.dao.po.User;

import java.util.ArrayList;
import java.util.List;

public class UserSQLite extends SQLiteOpenHelper implements UserDao {


    private final static String DATABASE_NAME = "user.db";//数据库名
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "users";//表名


    //构造函数，创建数据库
    public UserSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表SQL语句
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(id INTEGER PRIMARY KEY,"
                + " name VARCHAR(20),"
                + " phone VARCHAR(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<User> selectAll() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null); //查询全部数据SQL
        //将游标移到第一行
        cursor.moveToFirst();
        //循环读取数据
        while (!cursor.isAfterLast()) {
            //获得当前行的标签
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            list.add(new User(id, name, phone));
            //游标移到下一行
            cursor.moveToNext();

        }
        return list;
    }

    @Override
    public long insert(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", user.getName());
        cv.put("phone", user.getPhone());
        long row = db.insert(TABLE_NAME, null, cv);
        return row;

    }

    @Override
    public void delete(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "id = ?";
        String[] whereValue = {Integer.toString(user.getId())};
        db.delete(TABLE_NAME, where, whereValue);
    }
}
