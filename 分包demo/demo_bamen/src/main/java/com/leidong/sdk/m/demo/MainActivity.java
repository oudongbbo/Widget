package com.leidong.sdk.m.demo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.leidong.sdk.m.LeiMsdk;
import com.leidong.sdk.m.LeiMsdkCallback;
import com.leidong.sdk.m.model.constant.MsdkConstant;
import com.yb.maya.sjjx.bm.R;


import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener {

    private String appkey = "JxMLf2gUPVwqvCXKDt1Qb3nOc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leidong_demo_activity);

        LeiMsdk.getInstance().doInit(this, appkey, new LeiMsdkCallback() {

            @Override
            public void onInitSuccess() {
                showToast("初始化成功，游戏中不需要此提示");
            }

            @Override
            public void onInitFail(String message) {
                //不需要提示失败的原因，CP自行处理失败逻辑，如重新进入游戏等
                showToast("初始化失败，提示玩家退出重新进入");
            }

            @Override
            public void onLoginSuccess(Bundle bundle) {
                //1.客户端拿到Token后，需要去我方服务器进行用户信息验证
                //2.拿到玩家信息后，进入游戏服务器
                showToast("登录成功:\n token:" + bundle.getString("token"));
            }

            @Override
            public void onLoginFail(String message) {
                //不需要提示失败的原因，CP自行处理失败逻辑，如重新登录等
                if (MsdkConstant.CALLBACK_LOGIN_CANCEL.equals(message)) {
                    showToast("取消登录:\n" + message);
                } else {
                    showToast("登录失败:\n" + message);
                }
            }

            @Override
            public void onUserSwitchSuccess(Bundle bundle) {
                //同登录
                showToast("切换帐号成功:\n token:" + bundle.getString("token"));
            }

            @Override
            public void onUserSwitchFail(String message) {
                //同登录
                if (MsdkConstant.CALLBACK_SWITCH_CANCEL.equals(message)) {
                    showToast("取消切换:\n" + message);
                } else {
                    showToast("切换失败:\n" + message);
                }
            }

            @Override
            public void onPaySuccess(Bundle bundle) {
                //请CP以服务端的信息为准
                showToast("支付成功，请在游戏内发货");
            }

            @Override
            public void onPayFail(String message) {
                //不需要提示失败的具体原因，CP自行处理即可
                if (MsdkConstant.CALLBACK_PAY_CANCEL.equals(message)) {
                    showToast("取消支付:\n" + message);
                } else {
                    showToast("支付失败:\n" + message);
                }
            }

            @Override
            public void onExitGameSuccess() {
                //退出游戏，CP需要进行游戏退出的操作
                showToast("请CP进行游戏内退出操作");
                System.exit(1);
            }

            @Override
            public void onExitGameFail() {
                //暂不需要操作
                showToast("用户取消退出");
            }

            @Override
            public void onLogoutSuccess() {
                //SDK用户退出监听
                //需要CP方先退出当前游戏角色，并回到游戏登录界面，并调用我方SDK的userLogin
                LeiMsdk.getInstance().userLogin(MainActivity.this);
            }

            @Override
            public void onLogoutFail(String message) {
                //暂不需要操作
            }
        });

        findViewById(R.id.userLogin).setOnClickListener(this);
        findViewById(R.id.userLogout).setOnClickListener(this);
        findViewById(R.id.userSwitch).setOnClickListener(this);
        findViewById(R.id.userPay).setOnClickListener(this);
        findViewById(R.id.userPay2).setOnClickListener(this);
        findViewById(R.id.showExit).setOnClickListener(this);
        findViewById(R.id.creatRoleBtn).setOnClickListener(this);
        findViewById(R.id.submitDataBtn).setOnClickListener(this);
        findViewById(R.id.upgradeDataBtn).setOnClickListener(this);
        findViewById(R.id.updateDataBtn).setOnClickListener(this);
        findViewById(R.id.getConfig).setOnClickListener(this);

        findViewById(R.id.openCustomerServiceActivity).setOnClickListener(this);
        findViewById(R.id.openUserAuthWeb).setOnClickListener(this);
        findViewById(R.id.openUserSuperVip).setOnClickListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        LeiMsdk.getInstance().onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LeiMsdk.getInstance().onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LeiMsdk.getInstance().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LeiMsdk.getInstance().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LeiMsdk.getInstance().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeiMsdk.getInstance().onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LeiMsdk.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LeiMsdk.getInstance().onNewIntent(intent);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.userLogin:
                LeiMsdk.getInstance().userLogin(MainActivity.this);
                break;

            case R.id.userLogout:
                LeiMsdk.getInstance().userLogout(MainActivity.this);
                break;

            case R.id.userSwitch:
                LeiMsdk.getInstance().userSwitch(MainActivity.this);
                break;

            case R.id.userPay:
                HashMap<String, String> payinfos1 = new HashMap<String, String>();
                payinfos1.put(MsdkConstant.PAY_MONEY, "1");//充值金额
                payinfos1.put(MsdkConstant.PAY_ORDER_NO, "order" + System.currentTimeMillis() / 1000);//CP订单号
                payinfos1.put(MsdkConstant.PAY_ORDER_NAME, "一快钱的商品");//商品名称
                payinfos1.put(MsdkConstant.PAY_ORDER_EXTRA, "测试数据");//商品拓展数据
                payinfos1.put(MsdkConstant.PAY_ROLE_ID, "007");//角色ID
                payinfos1.put(MsdkConstant.PAY_ROLE_NAME, "詹姆斯邦德");//角色名称
                payinfos1.put(MsdkConstant.PAY_ROLE_LEVEL, "88");//角色等级
                payinfos1.put(MsdkConstant.PAY_SERVER_ID, "001");//服务器ID
                payinfos1.put(MsdkConstant.PAY_SERVER_NAME, "开天辟地");//服务器名称

                LeiMsdk.getInstance().userPay(MainActivity.this, payinfos1);

                break;


            case R.id.userPay2:
                HashMap<String, String> payinfos11 = new HashMap<String, String>();
                payinfos11.put(MsdkConstant.PAY_MONEY, "0");
                payinfos11.put(MsdkConstant.PAY_ORDER_NO, "order" + System.currentTimeMillis() / 1000);
                payinfos11.put(MsdkConstant.PAY_ORDER_NAME, "不定额充值");
                payinfos11.put(MsdkConstant.PAY_ORDER_EXTRA, "测试数据");
                payinfos11.put(MsdkConstant.PAY_ROLE_ID, "007");
                payinfos11.put(MsdkConstant.PAY_ROLE_NAME, "詹姆斯邦德");
                payinfos11.put(MsdkConstant.PAY_ROLE_LEVEL, "88");
                payinfos11.put(MsdkConstant.PAY_SERVER_ID, "001");
                payinfos11.put(MsdkConstant.PAY_SERVER_NAME, "开天辟地");

                LeiMsdk.getInstance().userPay(MainActivity.this, payinfos11);
                break;

            case R.id.showExit:
                LeiMsdk.getInstance().doExitGame(MainActivity.this);

                break;


            case R.id.creatRoleBtn:

                //
                //----------------------- 【创建角色接口】-----------------------
                //创建角色的时候调用
                // 1.创建角色的时间，务必从服务器获取，保证不可变，单位秒
                // 2.如果游戏是自动创建角色的，角色名是默认的，比如和游戏ID一样；请务必在后续角色有机会输入角色名的时候，调用【角色改名接口】
                //
                //
                HashMap<String, String> infos = new HashMap<String, String>();
                infos.put(MsdkConstant.SUBMIT_SERVER_ID, "001");// 服务器ID，数字，不得超过32个字符，数字
                infos.put(MsdkConstant.SUBMIT_SERVER_NAME, "华夏一服");// 服务器名称
                infos.put(MsdkConstant.SUBMIT_ROLE_ID, "1");// 角色ID，数字，不得超过32个字符，非平台用户id
                infos.put(MsdkConstant.SUBMIT_ROLE_LEVEL, "1");// 角色等级
                infos.put(MsdkConstant.SUBMIT_ROLE_NAME, "孙悟空");// 角色名称
                infos.put(MsdkConstant.SUBMIT_ROLE_NAME_OLD, "无");//玩家改名时必传，传玩家旧名字
                infos.put(MsdkConstant.SUBMIT_TIME_CREATE, "" + (System.currentTimeMillis() / 1000));// 角色创建时间，单位：秒，获取服务器存储的时间，默认-1
                infos.put(MsdkConstant.SUBMIT_ROLE_GENDER, "男");// 角色性别，“男”“女”
                infos.put(MsdkConstant.SUBMIT_ROLE_VIP, "0");// 玩家VIP等级，默认0
                infos.put(MsdkConstant.SUBMIT_ROLE_BALANCE, "0");// 玩家余额，默认0
                infos.put(MsdkConstant.SUBMIT_ROLE_FIGHTVALUE, "0");// 玩家战斗力,或者可以体现战斗力的数值，默认0
                infos.put(MsdkConstant.SUBMIT_ROLE_PROFESSION, "战士");// 玩家职业,没有传“无”
                infos.put(MsdkConstant.SUBMIT_ROLE_PARTYNAME, "无");// 玩家帮派，没有传“无”
                infos.put(MsdkConstant.SUBMIT_EXTRA, "无");// 拓展字段

                LeiMsdk.getInstance().roleCreate(infos);
                showToast("创建角色:" + infos.toString());

                break;

            case R.id.submitDataBtn:

                //
                // -----------------------【角色进入游戏接口】.角色进入服务器的时候调用-----------------------
                //
                //
                HashMap<String, String> infos2 = new HashMap<String, String>();
                infos2.put(MsdkConstant.SUBMIT_SERVER_ID, "001");// 服务器ID，数字，不得超过32个字符，数字
                infos2.put(MsdkConstant.SUBMIT_SERVER_NAME, "华夏一服");// 服务器名称
                infos2.put(MsdkConstant.SUBMIT_ROLE_ID, "1");// 角色ID，数字，不得超过32个字符，非平台用户id
                infos2.put(MsdkConstant.SUBMIT_ROLE_LEVEL, "1");// 角色等级
                infos2.put(MsdkConstant.SUBMIT_ROLE_NAME, "孙悟空");// 角色名称
                infos2.put(MsdkConstant.SUBMIT_ROLE_NAME_OLD, "无");//玩家改名时必传，传玩家旧名字
                infos2.put(MsdkConstant.SUBMIT_TIME_CREATE, "" + (System.currentTimeMillis() / 1000));// 角色创建时间，单位：秒，获取服务器存储的时间，默认-1
                infos2.put(MsdkConstant.SUBMIT_ROLE_GENDER, "男");// 角色性别，“男”“女”，默认“无”
                infos2.put(MsdkConstant.SUBMIT_ROLE_VIP, "1");// 玩家VIP等级，默认0
                infos2.put(MsdkConstant.SUBMIT_ROLE_BALANCE, "10");// 玩家余额，默认0
                infos2.put(MsdkConstant.SUBMIT_ROLE_FIGHTVALUE, "100");// 玩家战斗力,或者可以体现战斗力的数值，默认0
                infos2.put(MsdkConstant.SUBMIT_ROLE_PROFESSION, "战士");// 玩家职业,没有传“无”
                infos2.put(MsdkConstant.SUBMIT_ROLE_PARTYNAME, "花果山");// 玩家帮派，没有传“无”
                infos2.put(MsdkConstant.SUBMIT_EXTRA, "无");// 拓展字段


                LeiMsdk.getInstance().roleEnterGame(infos2);
                showToast("进入游戏:" + infos2.toString());

                break;

            case R.id.upgradeDataBtn:

                //
                //-----------------------【角色升级接口】，角色升级的时候调用-----------------------
                //
                //
                HashMap<String, String> info3 = new HashMap<String, String>();
                info3.put(MsdkConstant.SUBMIT_SERVER_ID, "001");// 服务器ID，数字，不得超过32个字符，数字
                info3.put(MsdkConstant.SUBMIT_SERVER_NAME, "华夏一服");// 服务器名称
                info3.put(MsdkConstant.SUBMIT_ROLE_ID, "1");// 角色ID，数字，不得超过32个字符，非平台用户id
                info3.put(MsdkConstant.SUBMIT_ROLE_LEVEL, "1");// 角色等级
                info3.put(MsdkConstant.SUBMIT_ROLE_NAME, "孙悟空");//玩家改名时必传，传玩家旧名字
                info3.put(MsdkConstant.SUBMIT_ROLE_NAME_OLD, "无");// 角色名称
                info3.put(MsdkConstant.SUBMIT_TIME_CREATE, "" + (System.currentTimeMillis() / 1000));// 角色创建时间，单位：秒，获取服务器存储的时间，默认-1
                info3.put(MsdkConstant.SUBMIT_ROLE_GENDER, "无");// 角色性别，“男”“女”
                info3.put(MsdkConstant.SUBMIT_ROLE_VIP, "7");// 玩家VIP等级，默认0
                info3.put(MsdkConstant.SUBMIT_ROLE_BALANCE, "10000");// 玩家余额，默认0
                info3.put(MsdkConstant.SUBMIT_ROLE_FIGHTVALUE, "1000");// 玩家战斗力,或者可以体现战斗力的数值，默认0
                info3.put(MsdkConstant.SUBMIT_ROLE_PROFESSION, "战士");// 玩家职业,没有传“无”
                info3.put(MsdkConstant.SUBMIT_ROLE_PARTYNAME, "花果山");// 玩家帮派，没有传“无”
                info3.put(MsdkConstant.SUBMIT_EXTRA, "无");// 拓展字段


                LeiMsdk.getInstance().roleLevelUp(info3);
                showToast("角色升级:" + info3.toString());
                break;

            case R.id.updateDataBtn:
                //
                // -----------------------【角色改名接口】，游戏内有角色改名的时候调用-----------------------
                // 为了数据统计准确，如果游戏内可以角色改名，务必调用
                //
                HashMap<String, String> info4 = new HashMap<String, String>();
                info4.put(MsdkConstant.SUBMIT_SERVER_ID, "001");// 服务器ID，数字，不得超过32个字符，数字
                info4.put(MsdkConstant.SUBMIT_SERVER_NAME, "华夏一服");// 服务器名称

                info4.put(MsdkConstant.SUBMIT_ROLE_ID, "1");// 角色ID，数字，不得超过32个字符，非平台用户id
                info4.put(MsdkConstant.SUBMIT_ROLE_LEVEL, "1");// 角色等级
                info4.put(MsdkConstant.SUBMIT_ROLE_NAME, "齐天大圣");// 角色名称
                info4.put(MsdkConstant.SUBMIT_ROLE_NAME_OLD, "孙悟空");// 角色的旧名称

                info4.put(MsdkConstant.SUBMIT_TIME_CREATE, "" + (System.currentTimeMillis() / 1000));// 角色创建时间，单位：秒，获取服务器存储的时间
                info4.put(MsdkConstant.SUBMIT_ROLE_GENDER, "男");// 角色性别，“男”“女”
                info4.put(MsdkConstant.SUBMIT_ROLE_VIP, "10");// 玩家VIP等级，默认0
                info4.put(MsdkConstant.SUBMIT_ROLE_BALANCE, "10000");// 玩家余额，默认0
                info4.put(MsdkConstant.SUBMIT_ROLE_FIGHTVALUE, "999");// 玩家战斗力,或者可以体现战斗力的数值，默认0
                info4.put(MsdkConstant.SUBMIT_ROLE_PROFESSION, "战士");// 玩家职业,没有传“无”
                info4.put(MsdkConstant.SUBMIT_ROLE_PARTYNAME, "花果山");// 玩家帮派，没有传“无”
                info4.put(MsdkConstant.SUBMIT_EXTRA, "无");// 拓展字段

                LeiMsdk.getInstance().roleChangeName(info4);
                showToast("角色换名:" + info4.toString());
                break;

            case R.id.getConfig:
                //
                // -----------------------【获取游戏参数接口】-----------------------
                // 务必动态获取相关参数，不要写死在客户端
                //

                String gid = LeiMsdk.getInstance().getGid(MainActivity.this);
                String pid = LeiMsdk.getInstance().getPid(MainActivity.this);
                String mid = LeiMsdk.getInstance().getMid(MainActivity.this);
                String sdkVer = LeiMsdk.getInstance().getSdkVer(MainActivity.this);

                showToast("当前游戏参数：\n gid=" + gid + "\n pid=" + pid + "\n mid=" + mid + "\n sdkver=" + sdkVer);
                break;


            case R.id.openCustomerServiceActivity:
                //
                // -----------------------打开独立的客服中心，可以嵌入在游戏设置界面中-----------------------
                //
                LeiMsdk.getInstance().openSdkCustomerService(MainActivity.this);

                break;

            case R.id.openUserAuthWeb:
                //
                //实名认证接口，第二字段，表示，是否可返回键关闭页面
                //
                LeiMsdk.getInstance().openUserNameAuth(MainActivity.this, true);

                break;
            case R.id.openUserSuperVip:
                //
                //打开超级会员接口，VIP用户的专属福利，一对一的客服服务
                //
                LeiMsdk.getInstance().openUserSuperVip(MainActivity.this);

                break;

        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LeiMsdk.getInstance().doExitGame(MainActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
