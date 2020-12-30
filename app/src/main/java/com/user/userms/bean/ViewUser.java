package com.user.userms.bean;

import com.user.userms.dao.po.User;

/**
 * 与界面显示
 */
public class ViewUser extends User {

    public ViewUser() {

    }

    public ViewUser(User user) {
        setName(user.getName());
        setPhone(user.getPhone());
        setId(user.getId());
    }



    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
