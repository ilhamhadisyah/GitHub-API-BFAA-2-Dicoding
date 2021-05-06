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
import com.ilham.githubfavouriteuser.data.UserFollowersItem
import com.ilham.githubfavouriteuser.databinding.FragmentFollowerBinding
import com.ilham.githubfavouriteuser.detail.DetailUserActivity
import com.ilham.githubfavouriteuser.network.APIClient
import com.ilham.githubfavouriteuser.network.APIHelper
import com.ilham.githubfavouriteuser.utils.Status
import com.ilham.githubfavouriteuser.viewAdapter.ItemFollowersAdapter
import com.ilham.githubfavouriteuser.viewModel.MainViewModel


class FollowerFragment : Fragment() {

    private lateinit var itemAdapter: ItemFollowersAdapter
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
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
            rvFollowers.layoutManager = LinearLayoutManager(context)
            itemAdapter = ItemFollowersAdapter(arrayListOf())
            rvFollowers.adapter = itemAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(APIHelper(APIClient.apiService)))
            .get(MainViewModel::class.java)
    }

    private fun setUpObserver(query: String) {
        viewModel.getFollowerList(query).observe(viewLifecycleOwner, {
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
                    progressFragmentFollower.visibility = View.VISIBLE
                    rvFollowers.visibility = View.GONE
                }
            }
            2 -> { //on success
                binding.apply {
                    progressFragmentFollower.visibility = View.GONE
                    rvFollowers.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun retrieve(followersItem: List<UserFollowersItem>) {
        setUI()
        itemAdapter.apply {
            addUsers(followersItem)
            notifyDataSetChanged()

        }
    }


}