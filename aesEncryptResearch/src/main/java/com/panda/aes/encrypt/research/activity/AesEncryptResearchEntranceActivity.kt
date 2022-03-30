package com.panda.aes.encrypt.research.activity

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.panda.aes.encrypt.research.R
import com.panda.aes.encrypt.research.mvp.base.BaseMvpActivity
import com.panda.aes.encrypt.research.mvp.prensenter.AesEncryptResearchEntranceActivityPresenter
import com.panda.aes.encrypt.research.mvp.view.AesEncryptResearchEntranceActivityView
import kotlinx.android.synthetic.main.layout_activity_aes_encrypt_research_entrance.*

class AesEncryptResearchEntranceActivity :
    BaseMvpActivity<AesEncryptResearchEntranceActivityPresenter>(),
    AesEncryptResearchEntranceActivityView {

    override fun inject() {
        activityComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(javaClass.simpleName)
        mTitle.setTitleTextColor(R.color.color_000000)
        mTitle.setTitleTextSize(resources.getDimension(R.dimen.dimens_14sp))
        mTitle.setLeftIconClickListener { finish() }

        id_layout_activity_aes_encrypt_research_entrance_list.layoutManager =
            LinearLayoutManager(this)
        // 添加Android自带的分割线
        id_layout_activity_aes_encrypt_research_entrance_list.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun initData() {
        mPresenter.setAdapter(
            id_layout_activity_aes_encrypt_research_entrance_list,
            "customViewTopicList.json"
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_activity_aes_encrypt_research_entrance
    }
}