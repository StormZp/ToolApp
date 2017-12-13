package com.storm.toolapp.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.storm.toolapp.R
import com.storm.toolapp.base.BaseActivity
import com.storm.toolapp.databinding.ActivityMainBinding
import com.storm.toolapp.ui.fragment.MainFragment


class MainActivity : BaseActivity<ActivityMainBinding>() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(R.layout.activity_main)

    }

    override fun initData() {
        binding.bottom.addItem(BottomNavigationItem(R.mipmap.backtop, "首页"))
                .addItem(BottomNavigationItem(R.mipmap.backtop, "内容"))
                .addItem(BottomNavigationItem(R.mipmap.backtop, "个人中心")).initialise()

        binding.frame.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                when (position) {
                    1 -> return MainFragment.newInstance("展示", "展示")
                    2 -> return MainFragment.newInstance("中心", "中心")
                }
                return MainFragment.newInstance("首页", "首页")
            }

            override fun getCount(): Int {
                return 3
            }
        }
        binding.frame.setNoScroll(true)
    }

    override fun initTitle() {

    }

    override fun initListener() {
        binding.bottom.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {
                binding.frame.setCurrentItem(position, false)
            }

            override fun onTabReselected(position: Int) {

            }
        })
    }
}
