package com.storm.toolapp.ui.fragment

import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.storm.toolapp.R
import com.storm.toolapp.base.BaseVlayoutFragment
import com.storm.toolapp.databinding.ItemBlankBinding
import com.storm.toolapp.helper.VLayoutHelper

/**
 * Created by Storm on 2017/12/13.
 */

class BlankVlayoutFragment : BaseVlayoutFragment() {
    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        fun newInstance(param1: String, param2: String): BlankVlayoutFragment {
            val fragment = BlankVlayoutFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun setVLayout() {
        super.setVLayout()
        //type 从2 开始

//        val mScrollFixLayoutHelperB = ScrollFixLayoutHelper(ScrollFixLayoutHelper.BOTTOM_RIGHT, 0, 0)
//        mScrollFixLayoutHelperB.showType = ScrollFixLayoutHelper.SHOW_ALWAYS
        /*val l4 = LinearLayoutHelper()
        l4.setDividerHeight(2)
        l4.setMargin(0, 12, 0, 12)
        l4.setPadding(12, 0, 12, 0)*/
        adapter.addAdapter(object : VLayoutHelper.Builder() {}.
                setContext(context).
                setCount(1).
                setLayoutHelper(LinearLayoutHelper()).
                setViewType(2).
                setRes(R.layout.item_blank_title).
                setParams(ViewGroup.LayoutParams(windowManager.defaultDisplay.width,
                        windowManager.defaultDisplay.height * 81 / 1000)).
                setOnBindView({ itemView, position ->
                }).creatAdapter())
        adapter.addAdapter(object : VLayoutHelper.Builder() {}.
                setContext(context).
                setCount(20).
                setLayoutHelper(LinearLayoutHelper()).
                setViewType(3).
                setRes(R.layout.item_blank).
                setParams(ViewGroup.LayoutParams(windowManager.defaultDisplay.width,
                        windowManager.defaultDisplay.height / 14)).
                setOnBindView({ itemView, position ->
                    var blankBinding = itemView.dataBinding as ItemBlankBinding
                    blankBinding.text.text = "条目----" + position

                }).creatAdapter())
    }
}
