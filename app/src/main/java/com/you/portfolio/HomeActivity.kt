package com.you.portfolio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private val fragmentList =
        arrayListOf(HomeFragment(), IndexFragment(), NewsFragment(), SettingFragment())
    private val tabTextList = arrayListOf("자산", "지표", "뉴스", "설정")
    private val tabIconList = arrayListOf(
        R.drawable.ic_round_paid_24,
        R.drawable.ic_round_trending_up_24,
        R.drawable.ic_round_article_24,
        R.drawable.ic_round_account_circle_24
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
    }

    private fun initView() {
        viewPager = findViewById(R.id.home_pager)
        tabLayout = findViewById(R.id.home_tab)

        viewPager.adapter = ViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTextList[position]
            tab.setIcon(tabIconList[position])
        }.attach()
    }
}

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentList: ArrayList<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}