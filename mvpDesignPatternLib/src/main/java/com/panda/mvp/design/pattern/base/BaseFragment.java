package com.panda.mvp.design.pattern.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.gyf.barlibrary.ImmersionBar;
import com.panda.mvp.design.pattern.utils.ToastHelper;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.TreeMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public abstract class BaseFragment extends Fragment {

    protected View mLayout;
    protected Activity mActivity;
    protected Unbinder mUnbinder;
    protected Logger mLogger;
    protected static String TAG_LOG = null;
    protected ImmersionBar mImmersionBar;

    protected static Map<String, String> sStackFragment = new TreeMap<>();

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initInject();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TAG_LOG = this.getClass().getSimpleName();
        mLogger = XLog.st(0).t().b().tag(TAG_LOG)
                .build();
        doRegisterEvent();
        mActivity = getActivity();
        mLayout = inflater.inflate(getLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mLayout);
        initInject();

        return mLayout;
    }

    @Override
    public void onLowMemory() {
        ToastHelper.showShort(mActivity.getApplicationContext(), "内存不足");
        Glide.get(getContext()).onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        initData();

        if (isImmersionBarEnabled()) {
            // 初始化沉浸式
            initImmersionBar();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onPageStart();
    }

    /**
     * 上报友盟
     */
    private void onPageStart() {
        synchronized ("_onPageStart") {
            if (sStackFragment.size() > 0) {
                for (String key : sStackFragment.keySet()) {
                    onPageEnd(sStackFragment.get(key));
                    sStackFragment.remove(key);
                }
            }
            MobclickAgent.onPageStart(TAG_LOG);
            sStackFragment.put(TAG_LOG, TAG_LOG);
            Log.i(BaseFragment.class.getSimpleName(), TAG_LOG + "_onPageStart");
        }
    }

    private void onPageEnd(String tag) {
        synchronized ("_onPageEnd") {
            Log.i(BaseFragment.class.getSimpleName(), tag + "_onPageEnd");
            MobclickAgent.onPageEnd(tag);
            sStackFragment.remove(tag);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sStackFragment.get(TAG_LOG) != null) {
            onPageEnd(sStackFragment.get(TAG_LOG));
        }
    }

    /**
     * 普通文本消息提示
     *
     * @param text
     */
    public void toast(String text) {
        ToastHelper.showShort(mActivity.getApplicationContext(), text);
    }

    @Override
    public void onDestroyView() {
        unregisterEvent();
        mUnbinder.unbind();
        mUnbinder = null;

        if (mLayout != null) {
            mLayout = null;
        }

        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        super.onDestroyView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.init();
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 反注册事件
     */
    protected void unregisterEvent() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    /**
     * 注册事件
     */
    protected void registerEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void doRegisterEvent() {
    }
}