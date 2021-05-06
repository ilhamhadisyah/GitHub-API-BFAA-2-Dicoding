@file:Suppress("DEPRECATION")

package com.ilham.githubfavouriteuser.detail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.githubfavouriteuser.base.ViewModelFactory
import com.ilham.githubfavouriteuser.data.UserFollowingItem
import com.ilham.githubfavouriteuser.databinding.FragmentFollowingBinding
import com.ilham.githubfavouriteuser.detail.DetailUserActivity
import com.ilham.githubfavouriteuser.network.APIClient
import com.ilham.githubfavouriteuser.network.APIHelper
import com.ilham.githubfavouriteuser.utils.Status
import com.ilham.githubfavouriteuser.viewAdapter.ItemFollowingAdapter
import com.ilham.githubfavouriteuser.viewModel.MainViewModel

class FollowingFragment : Fragment() {
    private lateinit var itemAdapter: ItemFollowingAdapter
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginKey = arguments?.getString(DetailUserActivity.LOGIN_KEY).toString()
        setupViewModel()
        setUpObserver(loginKey)
    }

    private fun setUI() {
        binding.apply {
            rvFollowing.layoutManager = LinearLayoutManager(context)
            itemAdapter = ItemFollowingAdapter(arrayListOf())
            rvFollowing.adapter = itemAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(APIHelper(APIClient.apiService)))
            .get(MainViewModel::class.java)
    }

    private fun setUpObserver(query: String) {
        viewModel.getFollowingList(query).observe(viewLifecycleOwner, {
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        showProgress(2)
                        resources.data?.let { userFollowers -> retrieve(userFollowers) }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                        showProgress(1)
                    }
                }
            }

        })
    }

    private fun showProgress(status: Int) {
        when (status) {
            1 -> { // On progress
                binding.apply {
                    progressFragmentFollowing.visibility = View.VISIBLE
                    rvFollowing.visibility = View.GONE
                }
            }
            2 -> { //on success
                binding.apply {
                    progressFragmentFollowing.visibility = View.GONE
                    rvFollowing.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun retrieve(followersItem: List<UserFollowingItem>) {
        setUI()
        itemAdapter.apply {
            addUsers(followersItem)
            notifyDataSetChanged()

        }
    }


}