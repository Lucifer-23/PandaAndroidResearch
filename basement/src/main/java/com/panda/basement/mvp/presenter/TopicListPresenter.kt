package com.panda.basement.mvp.presenter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.panda.basement.adapter.TopicListAdapter
import com.panda.basement.bean.TopicBean
import com.panda.basement.utils.GsonTools
import com.panda.mvp.design.pattern.base.BaseActivity
import com.panda.mvp.design.pattern.base.presenter.BaseRxPresenter
import com.panda.mvp.design.pattern.base.view.BaseView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

open class TopicListPresenter<T : BaseView>(context: Context) :
    BaseRxPresenter<T>() {
    private val mContext: Context = context
    private val mAdapter: TopicListAdapter? =
        TopicListAdapter(mContext)

    fun setAdapter(recyclerView: RecyclerView, topicListFie: String) {
        recyclerView.adapter = mAdapter
        mAdapter?.setOnItemClickListener() { adapter, view, position ->
            val item = adapter.getItem(position) as TopicBean?
            BaseActivity.startActivity(mContext, item?.componentName, null)
        }

        parseTopics(topicListFie)
    }

    private fun parseTopics(fileName: String?) {
        val stringBuilder = StringBuilder()
        try {
            // 通过管理器打开文件并读取
            val bf = BufferedReader(InputStreamReader(mContext.assets.open(fileName)))

            var line: String? = ""

            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            val topics = GsonTools.changeGsonToList(
                stringBuilder.toString(),
                Array<TopicBean>::class.java
            )
            mAdapter?.setNewData(topics)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}