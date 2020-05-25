package com.panda.android.research.mvp.componet;

import androidx.fragment.app.Fragment;

import com.panda.mvp.design.pattern.base.module.FragmentModule;
import com.panda.mvp.design.pattern.base.module.scope.FragmentScope;

import dagger.Component;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {

    Fragment getFragment();
}