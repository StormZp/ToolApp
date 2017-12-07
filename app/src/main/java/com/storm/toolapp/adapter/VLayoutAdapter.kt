package com.storm.toolapp.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper

open class VLayoutAdapter(context: Context, layoutHelper: LayoutHelper, count: Int) : DelegateAdapter.Adapter<VLayoutAdapter.BannerViewHolder>() {
    constructor(context: Context, layoutHelper: LayoutHelper, count: Int, layoutParams: ViewGroup.LayoutParams) : this(context, layoutHelper, count) {
        mLayoutParams = layoutParams
    }

    lateinit var mContext: Context
    lateinit var mLayoutHelper: LayoutHelper
    var mCount = 0
    var mLayoutParams: ViewGroup.LayoutParams? = null

    init {
        mContext = context
        mLayoutHelper = layoutHelper
        mCount = count

        if (mLayoutParams == null) {
            val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val height = windowManager.defaultDisplay.height / 2
            mLayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        return BannerViewHolder(null)
    }

    override fun onBindViewHolder(holder: BannerViewHolder?, position: Int) {
    }

    override fun getItemCount(): Int {
        return mCount
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return mLayoutHelper
    }

    inner class BannerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var dataBinding: ViewDataBinding? = null
    }
}