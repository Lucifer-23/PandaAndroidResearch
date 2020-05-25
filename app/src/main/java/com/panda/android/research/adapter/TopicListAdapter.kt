package com.panda.android.research.adapter

import android.content.Context
import android.graphics.Typeface
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.panda.android.research.R
import com.panda.android.research.bean.TopicBean

class TopicListAdapter(context: Context) :
    BaseQuickAdapter<TopicBean, BaseViewHolder>(R.layout.layout_item_topic_list) {
    private val mRobotoBold =
        Typeface.createFromAsset(context.assets, "fonts/Roboto-Bold.ttf")

    override fun convert(helper: BaseViewHolder?, item: TopicBean?) {
        item?.let {
            helper?.setTypeface(R.id.id_layout_item_topic_list_topic, mRobotoBold)
            helper?.setText(R.id.id_layout_item_topic_list_topic, item.topic)
        }
    }
}