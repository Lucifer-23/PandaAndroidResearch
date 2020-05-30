package com.panda.custom.view.research.activity

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.panda.custom.view.research.R
import com.panda.custom.view.research.mvp.base.BaseMvpActivity
import com.panda.custom.view.research.mvp.prensenter.CustomViewResearchEntranceActivityPresenter
import com.panda.custom.view.research.mvp.view.CustomViewResearchEntranceActivityView
import kotlinx.android.synthetic.main.layout_activity_custom_view_research_entrance.*

class CustomViewResearchEntranceActivity :
    BaseMvpActivity<CustomViewResearchEntranceActivityPresenter>(),
    CustomViewResearchEntranceActivityView {

    override fun inject() {
        activityComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(javaClass.simpleName)
        mTitle.setTitleTextColor(R.color.color_000000)
        mTitle.setTitleTextSize(resources.getDimension(R.dimen.dimens_14sp))
        mTitle.setLeftIconClickListener { finish() }

        id_layout_activity_custom_view_research_entrance_list.layoutManager =
            LinearLayoutManager(this)
        // 添加Android自带的分割线
        id_layout_activity_custom_view_research_entrance_list.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun initData() {
        mPresenter.setAdapter(
            id_layout_activity_custom_view_research_entrance_list,
            "customViewTopicList.json"
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_activity_custom_view_research_entrance
    }
}