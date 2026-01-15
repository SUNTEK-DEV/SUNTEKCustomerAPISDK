package com.suntek.mylibrary.devices;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.sdkapi.api.SdkApi;
import com.sdkapi.common.ApiTool;
import com.suntek.mylibrary.IsdkAPI;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Xie Daning
 * @date 2025/10/23 08:41
 */
public class SC1SdkImpl implements IsdkAPI {
    private static final String TAG = "SC1SdkImpl";
    private static final long READY_TIMEOUT_MS = 5000; // 等待最大 5 秒，可调
    private SdkApi sdkApi;
    private Context context;
    private boolean isInitialized = false;
    private boolean isInitializing = false; // 标记是否正在初始化中

    private volatile boolean isReady = false;        // 设备服务真正 read

    private final CountDownLatch readyLatch = new CountDownLatch(1);



    @Override
    public void init(Context context) {
        this.context = context.getApplicationContext();

        if (isInitialized || isInitializing) {
            Log.w(TAG, "SDK已初始化或正在初始化中");
            return;
        }

        isInitializing = true;
        try {
            SdkApi.newInstance(context);
            sdkApi = SdkApi.getInstance();
            isInitialized = true;

            // 在后台线程里等一小段时间再标记 ready（避免阻塞主线程）
            new Thread(() -> {
                try {
                    // 这里可以简单 sleep 一段时间，也可以轮询 getServiceConnectionStatus()
                    Thread.sleep(3000); // 你之前经验上要等 3 秒

                    // 这里可以加个判断，例如：
                    boolean status = sdkApi.getServiceConnectionStatus();
                    if (status ) { isReady = true;  }
                    Log.i(TAG, "SC1 SDK 已准备就绪");
                } catch (Exception e) {
                    Log.e(TAG, "等待 SC1 SDK 准备就绪失败: " + e.getMessage(), e);
                    isReady = false;
                } finally {
                    // 不管成功失败，都释放等待
                    readyLatch.countDown();
                }
            }, "SC1-Init-Wait-Thread").start();

        } catch (Exception e) {
            Log.e(TAG, "SC1 SDK初始化失败: " + e.getMessage(), e);
            isInitialized = false;
            // 初始化直接失败，也要释放等待，让 await 的线程不要一直卡死
            readyLatch.countDown();
        } finally {
            isInitializing = false;
        }
    }

    private boolean checkReady() {
        if (!isInitialized || sdkApi == null || !isReady) {
            Log.w(TAG, "SC1 SDK未就绪: isInitialized=" + isInitialized + ", isReady=" + isReady);
            return false;
        }
        return true;
    }

    @Override
    public boolean checkSystemStatus() {
        return false;
    }

    @Override
    public String getDeviceInfo() {

        return  sdkApi.DeviceInfo().getSerialNumber();
    }

    @Override
    public String getDeviceModel() {
        return "SC1";
    }

    @Override
    public String getSerialNumber() {
        //awaitReady();  // 在这里阻塞等待就绪（非 UI 线程调用）
        if (!checkReady()) {
            return "未知";   // 或者 "未就绪" / "获取失败"
        }
        try {
            String sn = sdkApi.DeviceInfo().getSerialNumber();
            return sn != null ? sn : "未知";
        } catch (Exception e) {
            Log.e(TAG, "获取序列号失败: " + e.getMessage(), e);
            return "获取失败";
        }
    }

    @Override
    public String getFirmwareVersion() {
        //awaitReady();  // 在这里阻塞等待就绪（非 UI 线程调用）
        if (!isInitialized || sdkApi == null || sdkApi.DeviceInfo() == null) {
            return "未知";
        }
        try {
            String fw = sdkApi.DeviceInfo().getFirmwareVersion();
            return fw != null ? fw : "未知";
        } catch (Exception e) {
            Log.e(TAG, "获取固件版本失败", e);
            return "获取失败";
        }

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
        return sdkApi.DeviceInfo().getEthMac();
    }

    /**
     * 设置屏幕亮度
     * @param brightness（0-100）
     * @return
     */
    @Override
    public boolean setBrightness(int brightness) {
        boolean b = sdkApi.Display().setBrightness(ApiTool.Screen.ALL, brightness);
        return b;
    }
}
