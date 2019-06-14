package com.leidong.sdk.m;


import com.leidong.sdk.m.controller.MsdkCore;
import com.leidong.sdk.m.controller.PlatformCore;
import com.leidong.sdk.m.platform.BaMenSDK;

public class LeiMsdk extends MsdkCore {

    public static LeiMsdk instance;
    public static PlatformCore platform;

    protected static byte[] lock = new byte[0];
    protected static byte[] lock2 = new byte[0];

    public LeiMsdk() {
        super();
    }

    public static LeiMsdk getInstance() {

        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new LeiMsdk();
                }
            }
        }
        return instance;
    }

    public static PlatformCore getPlatform() {
        if (platform == null) {
            synchronized (lock2) {
                if (platform == null) {
                    platform = new BaMenSDK();
                }
            }
        }
        return platform;
    }

}
