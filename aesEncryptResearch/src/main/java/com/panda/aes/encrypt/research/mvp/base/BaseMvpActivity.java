package com.panda.aes.encrypt.research.mvp.base;

import com.panda.aes.encrypt.research.AesEncryptResearchApplication;
import com.panda.aes.encrypt.research.R;
import com.panda.aes.encrypt.research.mvp.componet.ActivityComponent;
import com.panda.aes.encrypt.research.mvp.componet.DaggerActivityComponent;
import com.panda.mvp.design.pattern.base.AbstractMvpActivity;
import com.panda.mvp.design.pattern.base.presenter.BasePresenter;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends AbstractMvpActivity<T> {

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(AesEncryptResearchApplication.Companion.getApplication().getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    protected void initToolbar(String title) {
        mTitle = findViewById(R.id.id_aes_encrypt_research_title_view);
        setTitle(title);
    }
}