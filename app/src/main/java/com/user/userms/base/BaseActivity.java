package com.user.userms.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions();
    }


    private void permissions() {
        XXPermissions.with(this)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.constantRequest()
                // 支持请求6.0悬浮窗权限8.0请求安装权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 不指定权限则自动获取清单中的危险权限
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {

                        if (isAll) {


                        } else {
                            Toast.makeText(BaseActivity.this, "部分权限获取失败", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                        if (quick) {
                            String no = "";
                            for (String s : denied) {
                                Log.e("err", "noPermission: " + s);
                                no = no + s + "\n";
                            }
                            Toast.makeText(BaseActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BaseActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}
