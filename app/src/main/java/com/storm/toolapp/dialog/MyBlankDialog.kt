package com.storm.toolapp.dialog

import android.os.Bundle
import android.view.Window
import com.storm.toolapp.R
import com.storm.toolapp.base.BaseActivity
import com.storm.toolapp.databinding.AlertextMyBlankBinding

/**
 * @创建作者 Storm
 * @创建时间 2017-12-13 22:56
 * @创建描述 ${提示框}
 */

class MyBlankDialog : BaseActivity<AlertextMyBlankBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //去除这个Activity的标题栏
        initBinding(R.layout.alertext_my_blank)
//        val contentView = LayoutInflater.from(context).inflate(R.layout.alertext_add_note, null)
    }

    override fun initData() {

    }

    override fun initTitle() {

    }

    override fun initListener() {
//        binding.cancel.setOnClickListener {
//            finish()
//        }
    }
}
