package com.storm.toolapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storm.toolapp.R
import com.storm.toolapp.base.BaseFragment
import com.storm.toolapp.databinding.FragmentBlankBinding

/**
 * Created by Storm on 2017/12/13.
 */

class BlankFragment : BaseFragment<FragmentBlankBinding>() {
    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        fun newInstance(param1: String, param2: String): BlankFragment {
            val fragment = BlankFragment()
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

        initBinding(R.layout.fragment_blank, container)
        mView = binding.root
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun initData() {

    }

    override fun initListener() {
    }

}
