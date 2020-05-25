package com.panda.mvp.design.pattern.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.gyf.barlibrary.ImmersionBar;
import com.panda.mvp.design.pattern.utils.AppManager;
import com.panda.mvp.design.pattern.utils.ToastHelper;
import com.panda.mvp.design.pattern.widget.AppMsg;
import com.panda.mvp.design.pattern.widget.TitleView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.Gravity.BOTTOM;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    protected TitleView mTitle;
    protected BaseActivity mActivity;
    protected ImmersionBar mImmersionBar;
    protected Logger mLogger;

    /**
     * 用于判断是否从后台唤醒的标识
     */
    public static boolean sIsActive;

    @Override
    public void onTrimMemory(int level) {
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).onTrimMemory(level);
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        ToastHelper.showShort(getApplicationContext(), "内存不足,请清理内存");
        super.onLowMemory();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mActivity = this;
        mUnbinder = ButterKnife.bind((Activity) mActivity);
        mLogger = XLog
                .b()
                .st(2)
                .t()
                .tag(getClass().getSimpleName())
                .build();

        doRegisterEvent();

        // 初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }

        initView(savedInstanceState);
        initData();

        // 将Activity实例添加到AppManager的堆栈
        AppManager.getAppManager().addActivity(new WeakReference<>(this).get());
    }

    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
        if (!sIsActive) {
            // app从后台唤醒，进入前台
            sIsActive = true;
            mLogger.i("程序从后台唤醒");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            // app进入后台
            // 记录当前已经进入后台
            sIsActive = false;
            mLogger.i("程序进入后台");
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        if (mImmersionBar != null) {
            // 必须调用该方法，防止内存泄漏
            mImmersionBar.destroy();
        }
        // 将Activity实例从AppManager的堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    protected abstract void initToolbar(String title);

    protected void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setTitle(title);
        }
    }

    protected void toast(String msg) {
        if (mActivity == null) {
            return;
        }
        ToastHelper.showLong(mActivity, msg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * APP是否处于前台唤醒状态
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = null;
        if (activityManager != null) {
            appProcesses = activityManager.getRunningAppProcesses();
        }

        if (appProcesses == null) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void showAppMsg(String msg) {
        AppMsg appMsg = AppMsg.makeText(this, msg, AppMsg.STYLE_ALERT);
        appMsg.setLayoutGravity(BOTTOM);
        appMsg.show();
        appMsg.getView().setOnClickListener(v -> {
            appMsg.cancel();
        });
    }

    /**
     * 反注册事件
     */
    protected void unregisterEvent() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    protected void doRegisterEvent() {
    }

    /**
     * 注册事件
     */
    protected void registerEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public static void startActivity(Context context, Class destination, Bundle datas) {
        Intent intent = new Intent(context, destination);
        if (datas != null) {
            intent.putExtras(datas);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String componentName, Bundle datas) {
        Intent intent = new Intent();
        intent.setClassName(context, componentName);
        if (datas != null) {
            intent.putExtras(datas);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}