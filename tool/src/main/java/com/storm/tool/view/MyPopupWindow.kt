package com.storm.tool.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.meidp.tool.R
import com.storm.tool.utils.AppUtil

/**
 * @创建作者 Storm
 * @创建时间 2017-12-14 23:22
 * @创建描述 ${控件下面的弹框}
 */

class MyPopupWindow(context: Context, arrayList: ArrayList<String>, listener: OnMenuItemClickListener?) : PopupWindow() {
    private val HEIGHT_MENU = 160

    var context = context
    var list = arrayList
    var listener = listener

    init {
        initView()
    }

    open fun onMenuItemClickListener( listener: OnMenuItemClickListener){
        this.listener = listener
    }

    private fun initView() {
        val scroll = ScrollView(context)
        scroll.setBackgroundColor(Color.WHITE)
        val group = RadioGroup(context)

        for (i in list.indices) {
            val sort = list[i]
            val rb = Button(context)
            rb.setText(sort)
            rb.setTextColor(Color.parseColor("#404040"))
//            rb.buttonDrawable = ColorDrawable(Color.TRANSPARENT)
            rb.setBackgroundResource(R.drawable.abc_ab_share_pack_mtrl_alpha)
            rb.gravity = Gravity.CENTER_VERTICAL
            rb.setPadding(AppUtil.dip2Px(context, 10F), 0, 0, 0)
//            rb.setOnCheckedChangeListener { button, isChecked ->
//                if (isChecked) {
//                    button.setTextColor(Color.parseColor("#0FAEAA"))
//                    if (null != listener) {
//                        listener!!.onItemClick(i, list[i])
//                        dismiss()
//                    }
//                } else {
//                    button.setTextColor(Color.parseColor("#404040"))
//
//                }
//            }
            rb.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    if (null != listener) {
                        listener!!.onItemClick(i, list[i])
                        dismiss()
                    }
                }
            })

            val layoutParams = RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.dip2Px(context, 35F))
            group.addView(rb, layoutParams)

        }
        scroll.addView(group)

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(scroll, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.dip2Px(context, HEIGHT_MENU.toFloat())))

        val frameLayout = FrameLayout(context)
        frameLayout.setBackgroundColor(Color.parseColor("#99666666"))
        frameLayout.setOnTouchListener { view, motionEvent ->
            dismiss()
            false
        }

        layout.addView(frameLayout, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        contentView = layout

        //setHeight(AppUtil.getScreenHeight((Activity) context) - AppUtil.dip2Px(context, 88) - AppUtil.getNavigationBarHeight(context) + AppUtil.getStatusBarHeight(context));
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(BitmapDrawable())
        animationStyle = R.style.popmenu_anim_style
    }


    open interface OnMenuItemClickListener {
        fun onItemClick(position: Int, src: String)
    }
}
