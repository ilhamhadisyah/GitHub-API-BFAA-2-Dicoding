@file:Suppress("DEPRECATION")

package com.ilham.githubmemberapp.ui.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.githubmemberapp.R
import com.ilham.githubmemberapp.ui.view.setting.Setting
import com.ilham.githubmemberapp.ui.viewAdapter.ItemAdapter
import com.ilham.githubmemberapp.databinding.ActivityMainBinding
import com.ilham.githubmemberapp.data.model.SearchUserItem
import com.ilham.githubmemberapp.network.APIClient
import com.ilham.githubmemberapp.network.APIHelper
import com.ilham.githubmemberapp.ui.base.ViewModelFactory
import com.ilham.githubmemberapp.ui.viewModel.MainViewModel
import com.ilham.githubmemberapp.utils.Status


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setting.setOnClickListener(clickListener)
        binding.search.queryHint = getString(R.string.hint)
        binding.search.onActionViewExpanded()
        showProgress(1)
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                setupViewModel()
                setUI()
                setUpObserver(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        binding.search.clearFocus()
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.setting -> {
                val goToSetting = Intent(this, Setting::class.java)
                startActivity(goToSetting)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(APIHelper(APIClient.apiService)))
            .get(MainViewModel::class.java)
    }

    private fun setUI() {
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemAdapter(arrayListOf())
        binding.homeRecyclerView.adapter = adapter
    }

    private fun setUpObserver(query: String) {
        viewModel.getItemList(query).observe(this, {
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        showProgress(2)
                        resources.data?.let { userItemList -> retrieve(userItemList.itemUsers) }
                    }
                    Status.ERROR -> {
                        showProgress(4)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        showProgress(3)

                    }
                }
            }

        })
    }

    private fun retrieve(users: List<SearchUserItem>) {
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }

    private fun showProgress(status: Int) {
        when (status) {
            1 -> { // On query not filled
                binding.homeRecyclerView.visibility = View.GONE
                binding.progressAnim.visibility = View.GONE
                binding.welcome.visibility = View.VISIBLE
                binding.networkError?.visibility = View.GONE
            }
            2 -> { //on success
                binding.homeRecyclerView.visibility = View.VISIBLE
                binding.progressAnim.visibility = View.GONE
                binding.welcome.visibility = View.GONE
                binding.networkError?.visibility = View.GONE
            }
            3 -> { // On loadding
                binding.homeRecyclerView.visibility = View.GONE
                binding.progressAnim.visibility = View.VISIBLE
                binding.welcome.visibility = View.GONE
                binding.networkError?.visibility = View.GONE
            }
            4 ->{ // Network Error
                binding.networkError?.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
                binding.progressAnim.visibility = View.GONE
                binding.welcome.visibility = View.GONE
            }
        }
    }
}