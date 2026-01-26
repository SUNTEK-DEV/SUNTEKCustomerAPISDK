package com.suntek.mylibrary;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * SDK Unified Entry Class
 * Provide a simple SDK acquisition interface for external applications
 * @author Xie Daning
 * @date 2025/9/26 14:09
 */
public class InitSDK {

    /**
     * 获取SDK实例（自动检测设备型号）
     * @param context 上下文
     * @return SDK实例
     */
    private static volatile IsdkAPI sInstance;

    public static IsdkAPI getSDK(Context context) {
        if (sInstance == null) {
            synchronized (InitSDK.class) {
                if (sInstance == null) {
                    try {
                        sInstance = SDKFactory.createSDK(context.getApplicationContext());
                    } catch (Exception e) {
                        android.util.Log.e("InitSDK", "SDK初始化失败: " + e.getMessage());
                        // 创建一个默认的降级SDK实现
                        sInstance = createFallbackSDK(context);
                    }
                }
            }
        }
        return sInstance;
    }

    /**
     * 创建降级SDK实现（当SDK初始化失败时使用）
     * @param context 上下文
     * @return 降级SDK实例
     */
    private static IsdkAPI createFallbackSDK(Context context) {
        android.util.Log.w("InitSDK", "使用降级SDK实现");
        return new IsdkAPI() {
            @Override
            public void init(Context context) {
                // 不做任何操作
            }

            @Override
            public boolean checkSystemStatus() {
                return false;
            }

            @Override
            public String getDeviceInfo() {
                return "SDK不可用";
            }

            @Override
            public String getDeviceModel() {
                return android.os.Build.MODEL;
            }

            @Override
            public String getSerialNumber() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return Build.getSerial();
                }
                return "";
            }

            @Override
            public String getFirmwareVersion() {
                return android.os.Build.VERSION.RELEASE;
            }

            @Override
            public boolean isCameraAvailable() {
                return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
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
                return "未知";
            }
        };
    }

    /**
     * 根据指定设备类型获取SDK实例
     * @param context 上下文
     * @param deviceType 设备类型
     * @return SDK实例
     */
    public static IsdkAPI getSDK(Context context, DeviceType deviceType) {
        IsdkAPI sdk = SDKFactory.createSDKByType(deviceType);
        if (sdk != null) {
            sdk.init(context);
        }
        return sdk;
    }

    /**
     * 检查当前设备是否受支持
     * @return 是否支持
     */
    public static boolean isCurrentDeviceSupported() {
        DeviceType currentDeviceType = DeviceType.fromModel(android.os.Build.MODEL);
        return SDKFactory.isDeviceSupported(currentDeviceType);
    }

    /**
     * 获取当前设备类型
     * @return 当前设备类型
     */
    public static DeviceType getCurrentDeviceType() {
        return DeviceType.fromModel(android.os.Build.MODEL);
    }

    /**
     * 获取所有支持的设备类型
     * @return 支持的设备类型数组
     */
    public static DeviceType[] getSupportedDevices() {
        return SDKFactory.getSupportedDevices();
    }
}
