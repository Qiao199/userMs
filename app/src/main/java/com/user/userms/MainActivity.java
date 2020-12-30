package com.user.userms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.user.userms.app.UserApplication;
import com.user.userms.base.BaseActivity;
import com.user.userms.base.CustomShortDialog;
import com.user.userms.bean.ViewUser;
import com.user.userms.dao.UserDao;
import com.user.userms.dao.po.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int ADD_END_UPDATE = 0;

    Button btn_add;
    Button btn_del;
    Button btn_send;
    Button btn_clear;
    ListView list_view;
    List<ViewUser> listData;
    MainListViewAdapter adapter;
    UserDao userDao;//数据表操作DAO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = findViewById(R.id.btn_add);
        btn_del = findViewById(R.id.btn_del);
        btn_send = findViewById(R.id.btn_send);
        btn_clear = findViewById(R.id.btn_clear);
        list_view = findViewById(R.id.list_view);

        //绑定事件
        btn_add.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        userDao = ((UserApplication) getApplication()).userDao;//得到全局dao接口
        initData();// 初始数据
    }

    private void initData() {
        listData = new ArrayList<>();
        loadAll();
        adapter = new MainListViewAdapter(MainActivity.this, R.layout.listview_main_user, listData);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void loadAll() {
        List<User> userList = userDao.selectAll();
        listData.clear();
        for (User user : userList) {
            listData.add(new ViewUser(user));
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_del:
                del();
                break;
            case R.id.btn_send:
                send();
                break;
            case R.id.btn_clear:
                clear();
                break;
        }
    }


    //处理增加按钮事件
    private void add() {
        Intent intent = new Intent(this, AddActivity.class);
        //第一个参数是Intent对象，第二个requestCode指定我们的一个启动标志值，因为我们可能有多个按钮，如果都是跳转到同一个Activity对象上，
        //我们需要对其进行标志，才知道是哪个Activity对象跳转过来的。
        startActivityForResult(intent, ADD_END_UPDATE);

    }


    /**
     * 所有的Activity对象的返回值都是由这个方法来接收
     * 当AddActivity操作完成并退出，会由此方法接收并处理后续操作
     *
     * @param requestCode 表示的是启动一个Activity时传过去的requestCode值
     * @param resultCode  表示的是启动后的Activity回传值时的resultCode值
     * @param data        表示的是启动后的Activity回传过来的Intent对象
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_END_UPDATE && resultCode == RESULT_OK) {
            Toast.makeText(this, "正在更新列表...", Toast.LENGTH_LONG).show();
            loadAll();
            adapter.notifyDataSetChanged();//通知数据变化，更新UI

        }
    }


    //处理删除按钮事件
    private void del() {
        for (int i = listData.size() - 1; i >= 0; i--) {
            if (listData.get(i).isChecked()) {
                ViewUser user = listData.remove(i);
                userDao.delete(user);

            }
        }
        adapter.notifyDataSetChanged();//通知数据变化，更新UI
    }

    //处理群发按钮事件
    //先判断是否有勾选至少一个用户，如果没有则提示用户勾选。
    private void send() {
        boolean ised = false;//是否有选择用户标识
        for (ViewUser listDatum : listData) {//判断是否至少勾选了一个
            if (listDatum.isChecked()) {
                ised = true;
                break;
            }
        }
        if (ised) {
            //创建消息编辑Dialog
            final CustomShortDialog dialog = new CustomShortDialog(this, R.layout.msg_create);
            //设置Dialog的取消按钮事件
            dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            //设置Dialog的发送事件
            dialog.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText msg = dialog.findViewById(R.id.et_msg);
                    String msgStr = msg.getText().toString();
                    //判断消息是否为空
                    if (msgStr != null && msgStr.trim().length() != 0) {
                        // 获取短信管理器
                        android.telephony.SmsManager smsManager = android.telephony.SmsManager
                                .getDefault();
                        // 拆分短信内容（手机短信长度限制）
                        List<String> divideContents = smsManager.divideMessage(msgStr.trim());
                        for (ViewUser vu : listData) {
                            if (vu.isChecked()) {
                                for (String text : divideContents) {
                                    smsManager.sendTextMessage(vu.getPhone().trim(), null, text, null, null);
                                }
                            }
                        }
                        Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "要发送的消息不能为空，发送失败", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();

        } else {
            Toast.makeText(MainActivity.this, "请先选择要发送的用户", Toast.LENGTH_SHORT).show();
        }
    }

    //处理清除按钮事件
    private void clear() {
        for (ViewUser vu : listData) {
            vu.setChecked(false);
        }
        adapter.notifyDataSetChanged();//通知数据变化，更新UI
    }
}
