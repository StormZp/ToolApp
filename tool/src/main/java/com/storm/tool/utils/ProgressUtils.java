package com.storm.tool.utils;

import android.content.Context;

import com.storm.tool.view.CustomProgressDialog;


/**
 * Package com.meten.plantbox.view
 * 创 建 人：wxianing
 * 作 用：
 * 时 间：2016/5/22
 * 修 改 人：
 * 时 间：
 */
public class ProgressUtils {

    private static CustomProgressDialog progressDialog = null;

    public static void showProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(context);
        }
        progressDialog.show();
    }

    public static void cannelProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog.cancel();
            }
            progressDialog = null;
        }
    }
}
