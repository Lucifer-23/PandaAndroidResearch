package com.panda.mvp.design.pattern.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public abstract class AbstractMvpApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            initInMainProcess();
        }

        init();
    }

    abstract protected void initInMainProcess();

    abstract protected void init();

    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }
}