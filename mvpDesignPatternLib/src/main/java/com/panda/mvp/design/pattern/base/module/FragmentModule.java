package com.panda.mvp.design.pattern.base.module;

import androidx.fragment.app.Fragment;

import com.panda.mvp.design.pattern.base.module.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
@Module
public class FragmentModule {
    private Fragment mFragment;
    private int mType;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    public FragmentModule(Fragment fragment, int type) {
        mFragment = fragment;
        mType = type;
    }

    @FragmentScope
    @Provides
    Fragment provideFragment() {
        return mFragment;
    }
}