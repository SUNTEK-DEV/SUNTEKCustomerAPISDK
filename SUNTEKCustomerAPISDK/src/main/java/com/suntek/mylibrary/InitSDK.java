package com.suntek.mylibrary;

import android.content.Context;

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
                    sInstance = SDKFactory.createSDK(context.getApplicationContext());
                }
            }
        }
        return sInstance;
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
