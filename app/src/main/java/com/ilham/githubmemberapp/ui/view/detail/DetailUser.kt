@file:Suppress("DEPRECATION")

package com.ilham.githubmemberapp.ui.view.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ilham.githubmemberapp.R
import com.ilham.githubmemberapp.data.model.UserDetail
import com.ilham.githubmemberapp.databinding.ActivityDetailUserBinding
import com.ilham.githubmemberapp.network.APIClient
import com.ilham.githubmemberapp.network.APIHelper
import com.ilham.githubmemberapp.ui.base.ViewModelFactory
import com.ilham.githubmemberapp.ui.viewAdapter.PagerAdapter
import com.ilham.githubmemberapp.ui.viewModel.MainViewModel
import com.ilham.githubmemberapp.utils.Status
import com.squareup.picasso.Picasso


class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        const val LOGIN_KEY = "login"
    }

    private var followers: Int? = 0
    private var following: Int? = 0

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loginKey = intent.getStringExtra(LOGIN_KEY) as String
        supportActionBar?.apply {
            setBackgroundDrawable(getDrawable(R.drawable.base_action_bar_background))
            title = loginKey
        }
        setUpViewModel()
        setUpObserver(loginKey)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(APIHelper(APIClient.apiService)))
            .get(MainViewModel::class.java)
    }

    private fun setUpObserver(query: String) {
        viewModel.getUserDetailData(query).observe(this, {
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        resources.data?.let { userItemList -> retrieve(userItemList) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }

        })
    }

    private fun retrieve(itemUsers: UserDetail) {
        followers = itemUsers.followers
        following = itemUsers.following
        binding.nameDetail.text = itemUsers.name
        Picasso.get().load(itemUsers.avatar_url)
            .into(binding.detailUserAvatar)
        bindData(itemUsers)
        createTab(itemUsers)
        openUriBrowser(itemUsers)
    }

    private fun openUriBrowser(itemUsers: UserDetail) {
        binding.apply {
            openUrl.setOnClickListener {
                val uri: Uri = Uri.parse(itemUsers.htmlUrl)
                val openUri = Intent(Intent.ACTION_VIEW, uri)
                startActivity(openUri)
            }
        }
    }

    private fun createTab(itemUsers: UserDetail) {
        val bundle = Bundle()
        bundle.putString(LOGIN_KEY, itemUsers.login)
        val pagerAdapter = PagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = pagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            run {
                when (position) {
                    0 -> tab.text = "${getString(R.string.followers)} ($followers)"
                    1 -> tab.text = "${getString(R.string.following)} ($following)"
                }
            }
        }.attach()
    }

    private fun bindData(itemUsers: UserDetail) {
        binding.apply {
            if (itemUsers.company == null || itemUsers.company == "null" || itemUsers.company == "")
                company.visibility = View.GONE
            else company.text = itemUsers.company

            if (itemUsers.email == null || itemUsers.email == "null" || itemUsers.email == "")
                emailDetail.visibility = View.GONE
            else emailDetail.text = itemUsers.email

            if (itemUsers.location == null || itemUsers.location == "null" || itemUsers.location == "")
                location.visibility = View.GONE
            else location.text = itemUsers.location

            if (itemUsers.twitter_username == null || itemUsers.twitter_username == "null" || itemUsers.twitter_username == "")
                twitter.visibility = View.GONE
            else twitter.text = itemUsers.twitter_username

            if (itemUsers.blog == null || itemUsers.blog == "null" || itemUsers.blog == "")
                website.visibility = View.GONE
            else website.text = itemUsers.blog
        }

    }
    private fun openUrl(query: String){
        val uri : Uri = Uri.parse(query)
        val openURL = Intent(Intent.ACTION_VIEW,uri)
        startActivity(openURL)
    }

}