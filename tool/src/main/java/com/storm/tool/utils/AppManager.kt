package com.storm.developapp.tools

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import java.util.*

/**
 * Created by Administrator on 2017/5/25 0025.
 */

class AppManager public constructor() {

    /**
     * 添加Activity到堆中
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    fun removeActivity(activity: Activity){
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.remove(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后后一个压入的类
     */
    fun currentActivity(): Activity {

        return activityStack!!.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的类
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity == cls) {
                finishActivity(activity)
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        try {
            if (activityStack != null) {
                for (i in activityStack!!) {
                    if (i != null && !i.isFinishing) {
                        i.finish()
                    }
                }
                activityStack!!.clear()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 退出应用程
     */
    fun AppExit(context: Context) {
        try {
            finishAllActivity()
            val activityMgr = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//            activityMgr.restartPackage(context.packageName)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                activityMgr.restartPackage(context.packageName)
            }
            System.exit(0)
        } catch (e: Exception) {
        }
    }


    companion object {
        private var activityStack: Stack<Activity>? = null
        private var instance: AppManager? = null
        /**
         * 单一实例
         */
        val appManager: AppManager get() {
            if (instance == null) {
                instance = AppManager()
            }
            return instance as AppManager
        }
    }

    /**
     * 获取activity的列表
     * @return
     */
    fun getActTask(): Stack<Activity> {
        if (activityStack == null) {
            activityStack = Stack<Activity>()
        }
        return activityStack!!
    }

}
