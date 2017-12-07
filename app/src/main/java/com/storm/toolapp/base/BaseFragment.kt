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
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    fun initBinding(layout: Int, view: ViewGroup?) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), layout, view, false)
    }

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
        initBindingVar()
    }

    abstract fun initBindingVar()

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment BaseFragment.
         */
        // TODO: Rename and change types and number of parameters
    }

}// Required empty public constructor
