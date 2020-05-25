package com.panda.mvp.design.pattern.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.WeakHashMap;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import static com.panda.mvp.design.pattern.widget.AppMsg.LENGTH_STICKY;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
class AppMsgManager extends Handler implements Comparator<AppMsg> {

    private static final int MESSAGE_DISPLAY      = 0xc2007;
    private static final int MESSAGE_ADD_VIEW     = 0xc20074dd;
    private static final int MESSAGE_REMOVE       = 0xc2007de1;

    private static WeakHashMap<Activity, AppMsgManager> sManagers;
    private static ReleaseCallbacks sReleaseCallbacks;

    private final Queue<AppMsg> mMsgQueue;
    private final Queue<AppMsg> mStickyQueue;

    private AppMsgManager() {
        mMsgQueue = new PriorityQueue(1, this);
        mStickyQueue = new LinkedList();
    }

    /**
     * @return A {@link AppMsgManager} instance to be used for given {@link Activity}.
     */
    static synchronized AppMsgManager obtain(Activity activity) {
        if (sManagers == null) {
            sManagers = new WeakHashMap(1);
        }
        AppMsgManager manager = sManagers.get(activity);
        if (manager == null) {
            manager = new AppMsgManager();
            ensureReleaseOnDestroy(activity);
            sManagers.put(activity, manager);
        }

        return manager;
    }

    static void ensureReleaseOnDestroy(Activity activity) {
        if (SDK_INT < ICE_CREAM_SANDWICH) {
            return;
        }
        if (sReleaseCallbacks == null) {
            sReleaseCallbacks = new ReleaseCallbacksIcs();
        }
        sReleaseCallbacks.register(activity.getApplication());
    }


    static synchronized void release(Activity activity) {
        if (sManagers != null) {
            final AppMsgManager manager = sManagers.remove(activity);
            if (manager != null) {
                manager.clearAllMsg();
            }
        }
    }

    static synchronized void clearAll() {
        if (sManagers != null) {
            final Iterator<AppMsgManager> iterator = sManagers.values().iterator();
            while (iterator.hasNext()) {
                final AppMsgManager manager = iterator.next();
                if (manager != null) {
                    manager.clearAllMsg();
                }
                iterator.remove();
            }
            sManagers.clear();
        }
    }

    /**
     * Inserts a {@link AppMsg} to be displayed.
     *
     * @param appMsg
     */
    void add(AppMsg appMsg) {
        mMsgQueue.add(appMsg);
        if (appMsg.mInAnimation == null) {
            appMsg.mInAnimation = AnimationUtils.loadAnimation(appMsg.getActivity(),
                    android.R.anim.fade_in);
        }
        if (appMsg.mOutAnimation == null) {
            appMsg.mOutAnimation = AnimationUtils.loadAnimation(appMsg.getActivity(),
                    android.R.anim.fade_out);
        }
        displayMsg();
    }

    /**
     * Removes all {@link AppMsg} from the queue.
     */
    void clearMsg(AppMsg appMsg) {
        if (mMsgQueue.contains(appMsg) || mStickyQueue.contains(appMsg)) {
            // Avoid the message from being removed twice.
            removeMessages(MESSAGE_DISPLAY, appMsg);
            removeMessages(MESSAGE_ADD_VIEW, appMsg);
            removeMessages(MESSAGE_REMOVE, appMsg);
            mMsgQueue.remove(appMsg);
            mStickyQueue.remove(appMsg);
            removeMsg(appMsg);
        }
    }

    /**
     * Removes all {@link AppMsg} from the queue.
     */
    void clearAllMsg() {
        removeMessages(MESSAGE_DISPLAY);
        removeMessages(MESSAGE_ADD_VIEW);
        removeMessages(MESSAGE_REMOVE);
        clearShowing();
        mMsgQueue.clear();
        mStickyQueue.clear();
    }

    void clearShowing() {
        final Collection<AppMsg> showing = new HashSet();
        obtainShowing(mMsgQueue, showing);
        obtainShowing(mStickyQueue, showing);
        for (AppMsg msg : showing) {
            clearMsg(msg);
        }
    }

    static void obtainShowing(Collection<AppMsg> from, Collection<AppMsg> appendTo) {
        for (AppMsg msg : from) {
            if (msg.isShowing()) {
                appendTo.add(msg);
            }
        }
    }

    /**
     * Displays the next {@link AppMsg} within the queue.
     */
    private void displayMsg() {
        if (mMsgQueue.isEmpty()) {
            return;
        }
        // First peek whether the AppMsg is being displayed.
        final AppMsg appMsg = mMsgQueue.peek();
        final Message msg;
        if (!appMsg.isShowing()) {
            // Display the AppMsg
            msg = obtainMessage(MESSAGE_ADD_VIEW);
            msg.obj = appMsg;
            sendMessage(msg);
        } else if (appMsg.getDuration() != LENGTH_STICKY) {
            msg = obtainMessage(MESSAGE_DISPLAY);
            sendMessageDelayed(msg, appMsg.getDuration()
                    + appMsg.mInAnimation.getDuration() + appMsg.mOutAnimation.getDuration());
        }
    }

    /**
     * Removes the {@link AppMsg}'s view after it's display duration.
     *
     * @param appMsg The {@link AppMsg} added to a {@link ViewGroup} and should be removed.s
     */
    private void removeMsg(final AppMsg appMsg) {
        clearMsg(appMsg);
        final View view = appMsg.getView();
        ViewGroup parent = ((ViewGroup) view.getParent());
        if (parent != null) {
            appMsg.mOutAnimation.setAnimationListener(new OutAnimationListener(appMsg));
            view.clearAnimation();
            view.startAnimation(appMsg.mOutAnimation);
        }

        Message msg = obtainMessage(MESSAGE_DISPLAY);
        sendMessage(msg);
    }

    private void addMsgToView(AppMsg appMsg) {
        View view = appMsg.getView();
        if (view.getParent() == null) { // Not added yet
            final ViewGroup targetParent = appMsg.getParent();
            final ViewGroup.LayoutParams params = appMsg.getLayoutParams();
            if (targetParent != null) {
                targetParent.addView(view, params);
            } else {
                appMsg.getActivity().addContentView(view, params);
            }
        }
        view.clearAnimation();
        view.startAnimation(appMsg.mInAnimation);
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }

        final int duration = appMsg.getDuration();
        if (duration != LENGTH_STICKY) {
            final Message msg = obtainMessage(MESSAGE_REMOVE);
            msg.obj = appMsg;
            sendMessageDelayed(msg, duration);
        } else { // We are sticky, we don't get removed just yet
            mStickyQueue.add(mMsgQueue.poll());
        }
    }

    @Override
    public void handleMessage(Message msg) {
        final AppMsg appMsg;
        switch (msg.what) {
            case MESSAGE_DISPLAY:
                displayMsg();
                break;
            case MESSAGE_ADD_VIEW:
                appMsg = (AppMsg) msg.obj;
                addMsgToView(appMsg);
                break;
            case MESSAGE_REMOVE:
                appMsg = (AppMsg) msg.obj;
                removeMsg(appMsg);
                break;
            default:
                super.handleMessage(msg);
                break;
        }
    }

    @Override
    public int compare(AppMsg lhs, AppMsg rhs) {
        return inverseCompareInt(lhs.mPriority, rhs.mPriority);
    }

    static int inverseCompareInt(int lhs, int rhs) {
        return lhs < rhs ? 1 : (lhs == rhs ? 0 : -1);
    }

    private static class OutAnimationListener implements Animation.AnimationListener {

        private final AppMsg mAppMsg;

        private OutAnimationListener(AppMsg appMsg) {
            mAppMsg = appMsg;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            final View view = mAppMsg.getView();
            if (mAppMsg.isFloating()) {
                final ViewGroup parent = ((ViewGroup) view.getParent());
                if (parent != null) {
                    parent.post(new Runnable() { // One does not simply removeView
                        @Override
                        public void run() {
                            parent.removeView(view);
                        }
                    });
                }
            } else {
                view.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    interface ReleaseCallbacks {
        void register(Application application);
    }

    @TargetApi(ICE_CREAM_SANDWICH)
    static class ReleaseCallbacksIcs implements Application.ActivityLifecycleCallbacks, ReleaseCallbacks {
        private WeakReference<Application> mLastApp;

        public void register(Application app) {
            if (mLastApp != null && mLastApp.get() == app) {
                return; // Already registered with this app
            } else {
                mLastApp = new WeakReference(app);
            }
            app.registerActivityLifecycleCallbacks(this);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            release(activity);
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
    }
}