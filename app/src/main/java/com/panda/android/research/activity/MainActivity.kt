package com.panda.android.research.activity

import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.panda.android.research.R
import com.panda.android.research.mvp.base.BaseMvpActivity
import com.panda.android.research.mvp.prensenter.MainActivityPresenter
import com.panda.android.research.mvp.view.MainActivityView
import kotlinx.android.synthetic.main.layout_activity_main.*

class MainActivity : BaseMvpActivity<MainActivityPresenter>(), MainActivityView {

    override fun initData() {
        mPresenter.setAdapter(id_layout_activity_main_topics_list)
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_activity_main
    }

    override fun inject() {
        activityComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initToolbar(javaClass.simpleName)
        mTitle.setTitleGravity(Gravity.LEFT or Gravity.CENTER_VERTICAL)
        mTitle.setTitleTextColor(R.color.color_000000)
        mTitle.setTitleTextSize(resources.getDimension(R.dimen.dimens_20sp))

        id_layout_activity_main_topics_list.layoutManager = LinearLayoutManager(this)
        // 添加Android自带的分割线
        id_layout_activity_main_topics_list.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}