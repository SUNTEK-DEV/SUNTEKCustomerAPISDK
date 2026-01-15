package com.suntek.mylibrary;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.suntek.mylibrary.devices.FS8SdkImpl;
import com.suntek.mylibrary.devices.SC1SdkImpl;
import com.suntek.mylibrary.devices.SK8SdkImpl;
import com.suntek.mylibrary.devices.C9DSdkImpl;

/**
 * SDK工厂类
 * 负责根据设备型号自动选择并初始化对应的SDK实现
 */
public class SDKFactory {
    private static final String TAG = "SDKFactory";
    
    /**
     * 根据设备型号创建对应的SDK实例
     * @param context 上下文
     * @return SDK实例
     */
    public static IsdkAPI createSDK(Context context) {
        String deviceModel = Build.MODEL;

        DeviceType deviceType = DeviceType.fromModel(deviceModel);
        
        Log.i(TAG, "检测到设备型号:~~~~~ " + deviceModel + ", 设备类型:~~~~~~ " + deviceType.getDescription());
        
        IsdkAPI sdk = createSDKByType(deviceType);
        if (sdk != null) {
            sdk.init(context);
            Log.i(TAG, "SDK初始化完成: " + deviceType.getDescription());
            return sdk;
        } else {
            Log.w(TAG, "未找到对应的SDK实现: " + deviceModel);
            return sdk;
        }
        

    }
    
    /**
     * 根据设备类型创建SDK实例
     * @param deviceType 设备类型
     * @return SDK实例
     */
    public static IsdkAPI createSDKByType(DeviceType deviceType) {
        switch (deviceType.getModel()) {
            case "FS8":
            case "FS10":
                Log.i(TAG, "创建FS8 FS10 通用实例");
                return new FS8SdkImpl();
            case "SK8":
            case "LP":
                Log.i(TAG, "创建SC1 SDK实例......");
                return new SC1SdkImpl();
            default:
                Log.w(TAG, "未知设备类型，返回默认SDK实现");
                // 为未知设备返回一个默认实现或者null
                return createDefaultSDK();
        }
    }
    
    /**
     * 创建默认SDK实现（用于未知设备）
     * @return 默认SDK实例
     */
    private static IsdkAPI createDefaultSDK() {
        // 可以返回一个基础的默认实现，或者返回null
        // 这里返回FS8实现作为默认值
        Log.i(TAG, "使用FS8 SDK作为默认实现");
        //return new FS8SdkImpl();
        return new C9DSdkImpl();
    }
    
    /**
     * 检查指定设备类型是否受支持
     * @param deviceType 设备类型
     * @return 是否支持
     */
    public static boolean isDeviceSupported(DeviceType deviceType) {
        return deviceType != DeviceType.UNKNOWN;
    }
    
    /**
     * 获取所有支持的设备类型
     * @return 支持的设备类型数组
     */
    public static DeviceType[] getSupportedDevices() {
        return new DeviceType[]{
            DeviceType.FS8,
            DeviceType.SK8,

        };
    }
} 