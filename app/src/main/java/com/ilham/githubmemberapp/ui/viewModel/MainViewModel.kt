package com.ilham.githubmemberapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ilham.githubmemberapp.data.repos.MainRepository
import com.ilham.githubmemberapp.utils.Resources
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getItemList(query: String) = liveData(Dispatchers.IO) {
        emit(Resources.loading(data = null))
        try {
            emit(Resources.success(data = mainRepository.getUsers(query)))
        } catch (e: Exception) {
            emit(Resources.error(data = null, message = e.message ?: "Error ocurred"))
        }
    }

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