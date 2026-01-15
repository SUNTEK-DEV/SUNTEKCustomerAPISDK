package com.suntek.mylibrary;

import android.content.Context;

import com.sdkapi.api.SdkApi;

/**
 * 统一SDK API接口
 * 封装所有设备的公共功能，对外提供统一接口
 * @author Xie Daning
 * @date 2025/9/26 14:54
 */
public interface IsdkAPI {
    
    /**
     * 初始化SDK
     * @param context 上下文
     */
    void init(Context context);
    
    /**
     * 检查系统状态
     * @return 系统是否正常
     */
    boolean checkSystemStatus();
    
    /**
     * 获取设备信息
     * @return 设备序列号等信息
     */
    String getDeviceInfo();
    
    /**
     * 获取设备型号
     * @return 设备型号
     */
    String getDeviceModel();
    
    /**
     * 获取设备序列号
     * @return 设备序列号
     */
    String getSerialNumber();
    
    /**
     * 获取固件版本
     * @return 固件版本
     */
    String getFirmwareVersion();
    
    /**
     * 检查相机是否可用
     * @return 相机是否可用
     */
    boolean isCameraAvailable();
    
    /**
     * 开启相机
     * @return 是否成功开启
     */
    boolean openCamera();
    
    /**
     * 关闭相机
     * @return 是否成功关闭
     */
    boolean closeCamera();
    
    /**
     * 拍照
     * @param callback 拍照结果回调
     */
    void takePicture(CaptureCallback callback);
    
    /**
     * 设置自动对焦
     * @param enable 是否启用自动对焦
     * @return 是否设置成功
     */
    boolean setAutoFocus(boolean enable);
    
    /**
     * 开始二维码扫描
     * @param callback 扫描结果回调
     */
    void startQRCodeScan(QRCodeCallback callback);
    
    /**
     * 停止二维码扫描
     */
    void stopQRCodeScan();
    
    /**
     * 释放资源
     */
    void release();
    
    /**
     * 获取当前支持的功能列表
     * @return 功能列表
     */
    String[] getSupportedFeatures();
    
    /**
     * 拍照结果回调接口
     */
    interface CaptureCallback {
        void onCaptureSuccess(String imagePath);
        void onCaptureFailure(String error);
    }
    
    /**
     * 二维码扫描结果回调接口
     */
    interface QRCodeCallback {
        void onQRCodeDetected(String result);
        void onScanError(String error);
    }

    /**
     * 或缺MAC地址回调接口
     */
    String getMACAddress();
    /**
     * 设置屏幕亮度回调接口
     */
    boolean setBrightness(int brightness);

}
/**
 *
 *
 */
