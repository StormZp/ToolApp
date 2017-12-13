package com.storm.toolapp.api

import com.storm.toolapp.api.bean.BaseResponse
import rx.Observer

/**
 * Created by jiangruicheng on 2017/7/5.
 */
open class BaseObservable : Observer<BaseResponse> {
    override fun onNext(t: BaseResponse?) {
      //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(e: Throwable?) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCompleted() {
      //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}