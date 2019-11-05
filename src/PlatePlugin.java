package com.zsoftware;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.talkweb.cameraarea.LicensePlatePermissionsActivity;
import com.talkweb.cameraarea.LicensePlateUtils;
import com.talkweb.cameraarea.bean.ResResultBean;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlatePlugin extends CordovaPlugin {
    private Activity activity;
    private CallbackContext _callbackContext;
    private final static int cameraAreaCode = 1231;
    /**
     * 重写方法
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return
     * @throws JSONException
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        activity = this.cordova.getActivity();
        this._callbackContext = callbackContext;
        if ("show".equals(action)) {
            try {
                JSONObject obj = args.getJSONObject(0);
                if (obj.has("url")) {
                    String url = obj.getString("url");
                    LicensePlateUtils.setFileUpdateUrl(url);
                }

                if (obj.has("autoSubmitScore")) {
                    double val = obj.getDouble("autoSubmitScore");
                    //大于xx才提交  IsAutoSub=true 才有效果
                    LicensePlateUtils.setAutoSubVal(val);
                }

                //文件缓存地址
                LicensePlateUtils.setSdcardPath("plateTemp");
                //识别结果列表显示多少个
                LicensePlateUtils.setShowDataSize(3);
                //日志不输出
                LicensePlateUtils.setLog(false);
                //是否自动提交
                LicensePlateUtils.setIsAutoSub(true);

                //是否操播放音乐 IsAutoSub=true 才有效果
                LicensePlateUtils.setPlayMusic(true);
                //是否震动  IsAutoSub=true 才有效果
                LicensePlateUtils.setVibrator(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(activity, LicensePlatePermissionsActivity.class);
                    if (Build.VERSION.SDK_INT >= 20) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }

                    cordova.startActivityForResult(PlatePlugin.this, intent, cameraAreaCode);
                }
            });
            return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == cameraAreaCode) {
            if (resultCode == Activity.RESULT_OK) {
                ResResultBean bean = (ResResultBean) data.getSerializableExtra(LicensePlateUtils.RESULT_NAME);
                JSONObject ret = new JSONObject();
                try {
                    ret.put("code", bean.getCode());
                    ret.put("desc", bean.getDesc());
                    ret.put("plate", bean.getPlateBean().getPlate());
                    ret.put("colorDesc",bean.getPlateBean().getColorTypeDesc());
                    ret.put("score",bean.getPlateBean().getScore());
                    _callbackContext.success(ret);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                JSONObject ret = new JSONObject();
                try {
                    ret.put("code", -99);
                    ret.put("desc", "用户取消");
                    ret.put("plate","");
                    ret.put("colorDesc","");
                    ret.put("score","");
                    _callbackContext.success(ret);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



    }



}
