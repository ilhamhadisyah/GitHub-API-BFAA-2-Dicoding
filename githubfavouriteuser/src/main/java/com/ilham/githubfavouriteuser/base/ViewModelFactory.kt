package com.ilham.githubfavouriteuser.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilham.githubfavouriteuser.data.MainRepository
import com.ilham.githubfavouriteuser.network.APIHelper
import com.ilham.githubfavouriteuser.viewModel.MainViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: APIHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unkown Class")
    }

}