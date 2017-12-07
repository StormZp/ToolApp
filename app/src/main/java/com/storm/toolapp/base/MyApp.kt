package com.storm.toolapp.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.storm.developapp.tools.AppManager
import com.storm.tool.utils.CrashHandler

/**
 * @创建作者 Storm
 * *
 * @创建时间 2017-11-29 23:34
 * *
 * @创建描述 ${app}
 */

class MyApp : Application() {

    lateinit var mContext: Context


    companion object{
        open lateinit var app: MyApp
    }

    override fun onCreate() {
        super.onCreate()

        mContext = this.applicationContext
        app = this


        //分包
        MultiDex.install(this)
        //崩溃处理
        CrashHandler.getInstance().init(mContext)
        //图片框架加载
        Fresco.initialize(this)
    }

    open val getInstance: MyApp
        get() {
            if (app == null) {
                app = MyApp()
            }
            return app
        }

    fun logoutApp() {
        AppManager.appManager.AppExit(mContext)
    }
}
