@file:Suppress("DEPRECATION")

package com.ilham.githubfavouriteuser.detail

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ilham.githubfavouriteuser.R
import com.ilham.githubfavouriteuser.base.ViewModelFactory
import com.ilham.githubfavouriteuser.data.UserDetail
import com.ilham.githubfavouriteuser.databinding.ActivityDetailUserBinding
import com.ilham.githubfavouriteuser.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.ilham.githubfavouriteuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.ilham.githubfavouriteuser.db.DatabaseContract.UserColumns.Companion.LOGIN
import com.ilham.githubfavouriteuser.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.ilham.githubfavouriteuser.network.APIClient
import com.ilham.githubfavouriteuser.network.APIHelper
import com.ilham.githubfavouriteuser.utils.Status
import com.ilham.githubfavouriteuser.viewAdapter.PagerAdapter
import com.ilham.githubfavouriteuser.viewModel.MainViewModel
import com.squareup.picasso.Picasso


class DetailUserActivity : AppCompatActivity() {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return false
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
                    0 -> tab.text = "Followers ($followers)"
                    1 -> tab.text = "Following ($following)"
                }
            }
        }.attach()
    }

    private fun bindData(itemUsers: UserDetail) {
        binding.apply {
            if (itemUsers.company == null || itemUsers.company.isNotEmpty())
                company.visibility = View.GONE
            else company.text = itemUsers.company

            if (itemUsers.email == null || itemUsers.email.isNullOrEmpty())
                emailDetail.visibility = View.GONE
            else emailDetail.text = itemUsers.email

            if (itemUsers.location == null || itemUsers.location.isNullOrEmpty())
                location.visibility = View.GONE
            else location.text = itemUsers.location

            if (itemUsers.twitter_username == null || itemUsers.twitter_username.isNullOrEmpty())
                twitter.visibility = View.GONE
            else twitter.text = itemUsers.twitter_username

            if (itemUsers.blog == null || itemUsers.blog.isNullOrEmpty())
                website.visibility = View.GONE
            else website.text = itemUsers.blog
        }

    }


}