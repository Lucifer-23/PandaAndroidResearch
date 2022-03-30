package com.panda.aes.encrypt.research.mvp.componet;

import android.content.Context;

import com.panda.mvp.design.pattern.base.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Context getContext();
}