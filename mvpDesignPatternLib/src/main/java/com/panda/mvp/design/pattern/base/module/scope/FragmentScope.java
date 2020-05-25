package com.panda.mvp.design.pattern.base.module.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}