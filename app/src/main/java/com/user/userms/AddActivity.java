package com.user.userms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.user.userms.app.UserApplication;
import com.user.userms.dao.UserDao;
import com.user.userms.dao.po.User;

public class AddActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_phone;
    Button btn_add;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        userDao = ((UserApplication) getApplication()).userDao;

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setName(et_name.getText().toString());
                user.setPhone(et_phone.getText().toString());
                long i = userDao.insert(user);
                if (i != 0) {
                    added();
                } else {
                    Toast.makeText(AddActivity.this, "插入失败！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void added() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
