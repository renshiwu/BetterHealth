package com.rsw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

public class BDJPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        System.out.println("BDJPushReceiver====onReceive=============" + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            System.out.println("BDJPushReceiver====RegistrationId=============" + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            System.out.println("BDJPushReceiver====接收到推送下来的自定义消息=============" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            System.out.println("BDJPushReceiver====接收到推送下来的通知的ID=============" + notifactionId);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
            String notificationTitle = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            //alert:滴滴滴滴滴滴啦啦啦啦啦什么什么测试11111 notificationTitle:BDHR
            System.out.println("BDJPushReceiver====message=============" + message + " title:" + title + " extras:" + extras + " alert:" + alert + " notificationTitle:" + notificationTitle);

            //查接口，通过Main更新接口数据

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("BDJPushReceiver====用户点击打开了通知=============");
            //打开自定义的Activity
            String msgType = "";
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            try {
                JSONObject jsonObject = new JSONObject(extras);
                if (jsonObject.has("extrasKey")) {
                    msgType = jsonObject.getString("extrasKey");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            System.out.println("BDJPushReceiver====用户收到到RICH PUSH CALLBACK:=============" + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            if (!connected) {

            }
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity2
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        System.out.println("message=============" + message);
        System.out.println("extras=============" + extras);
    }
}