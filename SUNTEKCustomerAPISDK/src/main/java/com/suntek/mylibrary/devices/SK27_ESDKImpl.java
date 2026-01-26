package com.suntek.mylibrary.devices;

import android.content.Context;

import com.gki.GkiManager;
import com.suntek.mylibrary.IsdkAPI;

/**
 * @author Xie Daning
 * @date 2026/1/26 16:36
 */
public class SK27_ESDKImpl implements IsdkAPI {
    private GkiManager gki;
    private boolean gkiAvailable = false;
    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
        try {
            gki = GkiManager.getInstance(context);
            gkiAvailable = true;
            android.util.Log.i("SK27_ESDKImpl", "GkiManager初始化成功");
        } catch (Exception e) {
            gkiAvailable = false;
            android.util.Log.e("SK27_ESDKImpl", "GkiManager初始化失败: " + e.getMessage());
            // 不抛出异常，允许应用继续运行
        }
    }

    @Override
    public boolean checkSystemStatus() {
        return gkiAvailable;
    }

    @Override
    public String getDeviceInfo() {
        if (gkiAvailable && gki != null) {
            try {
               // return gki;
            } catch (Exception e) {
                android.util.Log.e("SK27_ESDKImpl", "获取设备信息失败: " + e.getMessage());
            }
        }
        return "设备信息不可用";
    }

    @Override
    public String getDeviceModel() {
        if (gkiAvailable && gki != null) {
            try {
                return gki.getDeviceModel();
            } catch (Exception e) {
                android.util.Log.e("SK27_ESDKImpl", "获取设备型号失败: " + e.getMessage());
            }
        }
        return android.os.Build.MODEL;
    }

    @Override
    public String getSerialNumber() {
        if (gkiAvailable && gki != null) {
            try {
                return gki.getDeviceSN();
            } catch (Exception e) {
                android.util.Log.e("SK27_ESDKImpl", "获取序列号失败: " + e.getMessage());
            }
        }
        return "未知";
    }

    @Override
    public String getFirmwareVersion() {
        if (gkiAvailable && gki != null) {
            try {
                return gki.getKernelVersion();
            } catch (Exception e) {
                android.util.Log.e("SK27_ESDKImpl", "获取固件版本失败: " + e.getMessage());
            }
        }
        return android.os.Build.VERSION.RELEASE;
    }

    @Override
    public boolean isCameraAvailable() {
        return false;
    }

    @Override
    public boolean openCamera() {
        return false;
    }

    @Override
    public boolean closeCamera() {
        return false;
    }

    @Override
    public void takePicture(CaptureCallback callback) {

    }

    @Override
    public boolean setAutoFocus(boolean enable) {
        return false;
    }

    @Override
    public void startQRCodeScan(QRCodeCallback callback) {

    }

    @Override
    public void stopQRCodeScan() {

    }

    @Override
    public void release() {

    }

    @Override
    public String[] getSupportedFeatures() {
        return new String[0];
    }

    @Override
    public String getMACAddress() {
        return "";
    }
}
