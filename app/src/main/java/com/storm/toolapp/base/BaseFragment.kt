package com.storm.toolapp.base


import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager


/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var binding: T
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mBundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
//            mBundle = arguments.getBundle()
        }
    }

    fun initBinding(layout: Int, view: ViewGroup?) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), layout, view, false)
        initData()
        initListener()
    }

    abstract fun initListener()

    abstract fun initData()

    lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return mView
    }

    fun setAlpha(float: Float) {
        val wl = activity.window.attributes
        wl.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        wl.alpha = float
        activity.window.setAttributes(wl)
    }

    override fun onResume() {
        super.onResume()
    }


    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

    }

}
