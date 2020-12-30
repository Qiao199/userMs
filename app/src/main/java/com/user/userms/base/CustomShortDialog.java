package com.user.userms.base;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;

import com.user.userms.R;


public class CustomShortDialog extends Dialog {


    public CustomShortDialog(Activity activity, @LayoutRes int resource, int style) {
        super(activity, style);
        View view = getLayoutInflater().inflate(resource, null);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = (int) (dm.widthPixels * 0.8);

        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCancelable(false);

    }



    public CustomShortDialog(Activity activity, @LayoutRes int resource) {

        this(activity,  resource, R.style.DialogTheme);

    }


}
