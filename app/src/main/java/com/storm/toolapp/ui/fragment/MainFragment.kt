package com.storm.toolapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storm.toolapp.R
import com.storm.toolapp.base.BaseFragment
import com.storm.toolapp.databinding.FragmentMainBinding

/**
 * Created Storm
 * Time    2017/12/13 15:38
 * Message {首页}
 */

class MainFragment : BaseFragment<FragmentMainBinding>() {
    companion object {
        private var ARG_PARAM1: String? = "param1"
        private var ARG_PARAM2: String? = "param2"
        fun newInstance(param1: String, param2: String): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        initBinding(R.layout.fragment_main, container)
        mView = binding.root
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initData() {
        binding.titlebar.title.text = mParam1
        binding.titlebar.back.visibility = View.GONE
    }

    override fun initListener() {
    }

}