package com.user.userms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.user.userms.bean.ViewUser;

import java.util.List;

public class MainListViewAdapter extends ArrayAdapter {

    private int resource;

    public MainListViewAdapter(@NonNull Context context, int resource, List<ViewUser> listData) {
        super(context, resource, listData);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewUser vu = (ViewUser) getItem(position); // 获取当前项的ViewUser实例
        View view = LayoutInflater.from(getContext()).inflate(resource, null);//实例化一个对象
        TextView vu_name = view.findViewById(R.id.vu_name);//获取该布局内的文本视图
        TextView vu_phone = view.findViewById(R.id.vu_phone);//获取该布局内的文本视图
        final CheckBox vu_check = view.findViewById(R.id.vu_check);//获取该布局内的单选框视图
        vu_name.setText(vu.getName());
        vu_phone.setText(vu.getPhone());
        vu_check.setChecked(vu.isChecked());
        vu_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vu.setChecked(vu_check.isChecked());
            }
        });
        return view;
    }


}
