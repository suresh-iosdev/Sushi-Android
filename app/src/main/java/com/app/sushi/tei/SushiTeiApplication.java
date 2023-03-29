package com.app.sushi.tei;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import androidx.multidex.MultiDex;

import com.nets.nofsdk.exception.ServiceAlreadyInitializedException;
import com.nets.nofsdk.model.PublicKeyComponent;
import com.nets.nofsdk.model.PublicKeySet;
import com.nets.nofsdk.request.NofService;
import com.nets.nofsdk.request.StatusCallback;
import com.onesignal.OneSignal;
import com.vkey.android.support.permission.PermissionResponse;
import com.vkey.android.support.permission.PermissionResultCallback;
import com.vkey.android.vguard.VGExceptionHandler;
import com.vkey.android.vguard.VGuardBroadcastReceiver;

import java.util.Hashtable;


public class SushiTeiApplication extends Application implements Application.ActivityLifecycleCallbacks, VGExceptionHandler, PermissionResultCallback {
    private Hashtable<Integer, VGuardBroadcastReceiver> activityBroadcastReceivers;
    @Override
    public void onCreate() {
        super.onCreate();

     /*   OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();*/

        if (NofService.isMainProcess(getApplicationContext())) {

        } else {

            return;
        }

        WebView.setWebContentsDebuggingEnabled(false);
        activityBroadcastReceivers = new Hashtable<Integer, VGuardBroadcastReceiver>();
        registerActivityLifecycleCallbacks(this);


    }
    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        } catch (RuntimeException multiDexException) {
            multiDexException.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onResult(PermissionResponse permissionResponse) {

    }

    @Override
    public void onError(String s) {

    }

    @Override
    public void handleException(Exception e) {

    }
}

