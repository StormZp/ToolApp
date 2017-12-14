package com.storm.toolapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.storm.tool.utils.AppUtil
import com.storm.tool.view.MyPopupWindow
import com.storm.toolapp.R
import com.storm.toolapp.base.BaseFragment
import com.storm.toolapp.databinding.FragmentMineBinding

/**
 * Created Storm
 * Time    2017/12/13 15:38
 * Message {首页}
 */

class MineFragment : BaseFragment<FragmentMineBinding>() {
    private var list = arrayListOf<String>()
    private var menu: MyPopupWindow? = null

    companion object {
        private var ARG_PARAM1: String? = "param1"
        private var ARG_PARAM2: String? = "param2"
        fun newInstance(param1: String, param2: String): MineFragment {
            val fragment = MineFragment()
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

        initBinding(R.layout.fragment_mine, container)
        mView = binding.root
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initData() {
        binding.titlebar.title.text = mParam1
        binding.titlebar.back.visibility = View.GONE


        for (i in 0..20) {
            list.add("条目:" + i)
        }
        menu = MyPopupWindow(context, list, object : MyPopupWindow.OnMenuItemClickListener {
            override fun onItemClick(position: Int, src: String) {
                Toast.makeText(context, position.toString() + "----" + src, Toast.LENGTH_SHORT).show()
            }
        })
        menu!!.setOnDismissListener { Toast.makeText(context, "menu消失了", Toast.LENGTH_SHORT).show() }

        menu!!.height = AppUtil.getScreenHeight(activity) - AppUtil.dip2Px(activity, 88F) - AppUtil.getNavigationBarHeight(activity) + AppUtil.getStatusBarHeight(activity) - 1

    }

    override fun initListener() {
        binding.submit.setOnClickListener {
            //            activity.startActivity(Intent(activity, MyBlankDialog::class.java))
            menu!!.showAsDropDown(binding.submit, 0, 10)

        }
    }

}