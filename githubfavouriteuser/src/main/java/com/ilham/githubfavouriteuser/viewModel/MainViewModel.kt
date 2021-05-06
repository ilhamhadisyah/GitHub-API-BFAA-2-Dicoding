package com.ilham.githubfavouriteuser.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ilham.githubfavouriteuser.data.MainRepository
import com.ilham.githubfavouriteuser.utils.Resources
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getUserDetailData(query: String) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = mainRepository.getUserDataList(query)))
        } catch (e: Exception) {
            emit(Resources.error(data = null, message = e.message ?: "Error ocurred"))
        }
    }

    fun getFollowerList(query: String) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = mainRepository.getFollowerList(query)))
        } catch (e: Exception) {
            emit(Resources.error(data = null, message = e.message ?: "Error ocurred"))
        }
    }

    fun getFollowingList(query: String) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = mainRepository.getFollowingList(query)))
        } catch (e: Exception) {
            emit(Resources.error(data = null, message = e.message ?: "Error ocurred"))
        }
    }
}