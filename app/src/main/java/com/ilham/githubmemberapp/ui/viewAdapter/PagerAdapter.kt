package com.ilham.githubmemberapp.ui.viewAdapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ilham.githubmemberapp.ui.view.detail.fragments.FollowerFragment
import com.ilham.githubmemberapp.ui.view.detail.fragments.FollowingFragment

class PagerAdapter(
    appCompatActivity: AppCompatActivity,
    private val userLogin: Bundle) :
    FragmentStateAdapter(appCompatActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }

        if (fragment != null) {
            fragment.arguments = userLogin
        }

        return fragment as Fragment
    }
}