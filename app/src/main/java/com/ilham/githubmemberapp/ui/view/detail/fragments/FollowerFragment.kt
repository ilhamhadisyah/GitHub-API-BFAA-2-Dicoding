package com.ilham.githubmemberapp.ui.view.detail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.githubmemberapp.data.model.UserFollowersItem
import com.ilham.githubmemberapp.databinding.FragmentFollowerBinding
import com.ilham.githubmemberapp.network.APIClient
import com.ilham.githubmemberapp.network.APIHelper
import com.ilham.githubmemberapp.ui.base.ViewModelFactory
import com.ilham.githubmemberapp.ui.view.detail.DetailUser
import com.ilham.githubmemberapp.ui.viewAdapter.ItemFollowersAdapter
import com.ilham.githubmemberapp.ui.viewModel.MainViewModel
import com.ilham.githubmemberapp.utils.Status

@Suppress("DEPRECATION")
class FollowerFragment : Fragment() {

    private lateinit var itemAdapter : ItemFollowersAdapter
    private lateinit var viewModel: MainViewModel
    private var _binding : FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginKey = arguments?.getString(DetailUser.LOGIN_KEY).toString()
        setupViewModel()
        setUpObserver(loginKey)

    }
    private fun setUI() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(context)
        itemAdapter = ItemFollowersAdapter(arrayListOf())
        binding.rvFollowers.adapter = itemAdapter
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
                        resources.data?.let { userFollowers -> retrieve(userFollowers) }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {

                    }
                }
            }

        })
    }

    private fun retrieve(followersItem: List<UserFollowersItem>) {
        setUI()
        itemAdapter.apply {
            addUsers(followersItem)
            notifyDataSetChanged()

        }
    }


}