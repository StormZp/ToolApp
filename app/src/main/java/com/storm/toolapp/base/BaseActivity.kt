package com.storm.toolapp.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.storm.developapp.tools.AppManager
import com.storm.tool.utils.ProgressUtils

/**
 * @创建作者    Storm
 * @创建时间    2017-09-18 23:29
 * @创建描述    ${activity 基类}
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: T
    lateinit var context: Context
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AppManager.appManager.getActTask().contains(this))
            AppManager.appManager.addActivity(this)
        context = this
        activity = this
    }

    override fun onResume() {
        super.onResume()

    }

    abstract fun initData()

    fun showProgress() {
        ProgressUtils.showProgressDialog(context)

    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.appManager.removeActivity(this)
    }

    fun hideProgress() {
        ProgressUtils.cannelProgressDialog()
    }

    fun initBinding(layout: Int) {
        binding = DataBindingUtil.setContentView<T>(this, layout)!!
        initData()
        initListener()
        initTitle()
    }

    open fun jump(t: Class<T>) {
        startActivity(Intent(activity, t))
    }

    open fun jump(t: Class<T>, bundle: Bundle) {
        startActivity(Intent(activity, t).putExtras(bundle))
    }

    abstract fun initTitle()
    abstract fun initListener()
}