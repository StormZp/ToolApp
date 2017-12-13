package com.storm.toolapp.api

import com.storm.toolapp.api.bean.BaseResponse
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jiangruicheng on 2017/7/5.
 */
open class ApiUtill {
    val TEST = 0

    companion object {
        fun getInstance(): ApiUtill {
            return INIT.init
        }
    }

    private object INIT {
        val init = ApiUtill()
    }

    lateinit var api: HashMap<Int, Observable<out BaseResponse>>

    init {
        api = HashMap()
//        api.put(TEST, AppRequest.getAPI().test(BaseRequest()))
    }

    fun getApi(api: Observable<out BaseResponse>, next: (BaseResponse) -> Unit): Subscription? {
        return api.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(object : BaseObservable() {

                    override fun onNext(t: BaseResponse?) {
                        super.onNext(t)
                        if (t != null) {
                            next(t)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        super.onError(e)
                        e!!.printStackTrace()
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                    }
                })
    }

    fun getApi(flag: Int, next: (BaseResponse) -> Unit): Subscription? {
        if (api.get(flag) != null)
            return api.get(flag)!!.subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(object : BaseObservable() {

                        override fun onNext(t: BaseResponse?) {
                            super.onNext(t)
                        }

                        override fun onError(e: Throwable?) {
                            super.onError(e)
                        }

                        override fun onCompleted() {
                            super.onCompleted()
                        }
                    })
        return null
    }

}

