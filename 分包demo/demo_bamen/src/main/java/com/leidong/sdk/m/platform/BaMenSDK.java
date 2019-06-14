package com.leidong.sdk.m.platform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.joke.sdk.BMApi;
import com.joke.sdk.BMError;
import com.joke.sdk.BMUser;
import com.joke.sdk.CallbackListener;
import com.leidong.sdk.framework.http.HttpCallBack;
import com.leidong.sdk.m.controller.PlatformCore;
import com.leidong.sdk.m.http.MReqPublic;
import com.leidong.sdk.m.interfaces.MLoginCallback;
import com.leidong.sdk.m.model.constant.MsdkConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BaMenSDK extends PlatformCore {


    private static final String PlatformName = "BaMen_SDK";
    private boolean isPlatformSwitch = false;
    private Context mContext;
    private Activity mActivity;
    private BMApi bmApi;


    @Override
    public void mInit(Context paramContext) {
        super.mInit(paramContext);
        sendLog(PlatformName + "初始化 ");
        mContext = paramContext;
        mActivity = (Activity) paramContext;

        int appId = Integer.parseInt(getPlatformConfig().getmAppId());


        bmApi = BMApi.newInstance(mActivity, appId, mActivity.getPackageName());
        bmApi.CLOSE_CHANGEUSER = true;
        getPlatformCallBack().onInitSuccess();


    }

    @Override
    public void mUserLogin(Context paramContext) {
        super.mUserLogin(paramContext);
        sendLog(PlatformName + "登录");
        isPlatformSwitch = false;
        bmApi.openBmLoginDialog(callback, mActivity);

    }

    @Override
    public void mUserSwitch(Context paramContext) {
        super.mUserSwitch(paramContext);
        // 使用如下方法调用登录接口
        sendLog(PlatformName + "切换账号");
        isPlatformSwitch = true;
        bmApi.openBmLoginDialog(callback, mActivity);
    }

    @Override
    public void mUserPay(Context paramContext, HashMap<String, String> paramHashMap) {
        super.mUserPay(paramContext, paramHashMap);
        detailUserPay(paramHashMap);
    }


    @Override
    public void mExitGame(Context arg0) {
        super.mExitGame(arg0);
        // 退出游戏


    }


    @Override
    public void mUserLogout(Context context) {
        super.mUserLogout(context);


    }


    @Override
    public void mRoleCreate(HashMap<String, String> hashMap) {
        super.mRoleCreate(hashMap);
        sendLog(PlatformName + "mRoleCreate");


    }

    @Override
    public void mRoleEnterGame(final HashMap<String, String> paramHashMap) {
        super.mRoleEnterGame(paramHashMap);
        sendLog(PlatformName + "mRoleEnterGame");



    }

    @Override
    public void mRoleUpgrade(HashMap<String, String> hashMap) {
        super.mRoleUpgrade(hashMap);
        //角色升级
        sendLog(PlatformName + "mRoleUpgrade");


    }

    @Override
    public void mRoleUpdate(HashMap<String, String> hashMap) {
        super.mRoleUpdate(hashMap);
        //角色换名
        sendLog(PlatformName + "mRoleUpdate");

    }


    @Override
    public void mOnPause() {
        super.mOnPause();

    }


    @Override
    public void mOnStop() {
        super.mOnStop();


    }


    @Override
    public void mOnResume() {
        super.mOnResume();


    }


    @Override
    public void mOnStart() {
        super.mOnStart();

    }

    @Override
    public void mOnRestart() {
        super.mOnRestart();

    }

    @Override
    public void mOnNewIntent(Intent intent) {
        super.mOnNewIntent(intent);


    }


    @Override
    public void mOnDestroy() {
        if (bmApi != null) {
            bmApi.onDestroy();
        }

        super.mOnDestroy();


    }


    @Override
    public void mOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.mOnActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void mOnConfigurationChanged(Configuration configuration) {
        super.mOnConfigurationChanged(configuration);


    }



    private void detailUserLogin(String uid) {

        HashMap<String, String> login = new HashMap<String, String>();
        login.put("uid",uid );


        MReqPublic.mLogin(mContext, mapToJson(login), new HttpCallBack() {

            @Override public void onSuccess(String arg0) {

                mLoginSuccess(arg0, new MLoginCallback() {

                    @Override public void onSwitch() {

                    }

                    @Override public void onSuccess(Bundle bundle) {

                        Bundle result = new Bundle();
                        result.putString("token", bundle.getString("token"));
                        handleLoginAndSwitchCallbackBundle(isPlatformSwitch, result);
                    }

                    @Override public void onFail(String msg) {
                        sendLog("失败信息：" + msg);
                        handleLoginAndSwitchCallbackFail(isPlatformSwitch, msg);
                    }
                });
            }

            @Override public void onFail(int arg0, String arg1) {
                handleLoginAndSwitchCallbackFail(isPlatformSwitch, arg1);
            }
        }, false);



    }

    private void detailUserPay(HashMap<String, String> paramHashMap) {

        sendLog(PlatformName + " 支付" + paramHashMap.toString());

        // 必填参数
        String orderNo ; // 订单号

        // 获取必要参数
        String payData = paramHashMap.get(MsdkConstant.PAY_ORDER_CCH_DATA);
        if (payData != null) {
            try {
                JSONObject payJson = new JSONObject(payData);
                orderNo = payJson.getString("orderNo");

            } catch (JSONException e) {
                e.printStackTrace();
                getPlatformCallBack().onPayFail("支付参数解析失败");
                return;
            }


            long mymoney = Long.parseLong(paramHashMap.get(MsdkConstant.PAY_MONEY));
            long server_id = Long.parseLong(paramHashMap.get(MsdkConstant.PAY_SERVER_ID));
            String server_name = paramHashMap.get(MsdkConstant.PAY_SERVER_NAME);
            String role_name = paramHashMap.get(MsdkConstant.PAY_ROLE_NAME);
            String order_name = paramHashMap.get(MsdkConstant.PAY_ORDER_NAME);


            Bundle params = new Bundle();
            // 以下为必填项。
            // 商品名称：例如“充值”，“购买VIP”等,不超过64个字符
            params.putString(BMApi.PRODUCT_NAME, order_name);
            // 商品价格： 单位为分（￥）。
            params.putLong(BMApi.TOTAL_AMOUNT, mymoney*100);
            // 订单号  不超过100个字符
            params.putString(BMApi.GAME_ORDER_NO, orderNo);
            // 支付者的游戏角色名称,不超过80字符
            params.putString(BMApi.USER_ROLE_NAME, role_name);

            //选填
            // 支付者的游戏角色所在区（服务器/板块/ 等）
            params.putString(BMApi.USER_SEVER_NAME, server_name);
            // 支付者的游戏角色所在区的ID（服务器ID/板块ID/等）
            params.putLong(BMApi.USER_SEVER_ID, server_id);

            bmApi.openPayActivity(params,bmPayCallback);

        } else {
            // 渠道支付参数获取失败
            getPlatformCallBack().onPayFail("渠道支付参数获取失败.");
        }

    }



    CallbackListener callback = new CallbackListener() {
        @Override
        public void onError(Throwable error) {
            super.onError(error);
            getPlatformCallBack().onLoginFail(error.getMessage());
        }

        @Override
        public void onLogoutSuccess() {
            super.onLogoutSuccess();

        }

        @Override
        public void onLoginError(BMError error) {
            super.onLoginError(error);
            getPlatformCallBack().onLoginFail(error.getMessage());
        }

        @Override
        public void onLoginSuccess(Bundle bundle) {
            super.onLoginSuccess(bundle);
            int id = bundle.getInt(BMUser.UID);
            String token = bundle.getString(BMUser.TOKEN);
            sendLog(PlatformName+"  " + " uid = " + id + "        token = " + token);
            detailUserLogin(String.valueOf(id));
        }
    };



    static CallbackListener bmPayCallback= new CallbackListener() {
        @Override
        public void onError(Throwable error) {
            super.onError(error);
        }
        @Override
        public void onPaymentSuccess(int code, String message, String orderNo) {
            super.onPaymentSuccess(code, message, orderNo);
        }
        @Override
        public void onPaymentError(int code, String message, String orderNo) {
            super.onPaymentError(code, message, orderNo);
        }
        @Override
        public void onOpenError(BMError error) {
            super.onOpenError(error);
        }
    };




}