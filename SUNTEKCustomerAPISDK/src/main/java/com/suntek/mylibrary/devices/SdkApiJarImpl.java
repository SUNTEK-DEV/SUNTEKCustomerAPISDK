package com.suntek.mylibrary.devices;

import android.content.Context;

import com.suntek.mylibrary.IsdkAPI;

/**
 * @author Xie Daning
 * @date 2025/12/17 08:47
 */
public class SdkApiJarImpl implements IsdkAPI {
    @Override
    public void init(Context context) {

    }

    @Override
    public boolean checkSystemStatus() {
        return false;
    }

    @Override
    public String getDeviceInfo() {
        return "";
    }

    @Override
    public String getDeviceModel() {
        return "";
    }

    @Override
    public String getSerialNumber() {
        return "";
    }

    @Override
    public String getFirmwareVersion() {
        return "";
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

//    @Override
//    public boolean setBrightness(int brightness) {
//        return false;
//    }
}
