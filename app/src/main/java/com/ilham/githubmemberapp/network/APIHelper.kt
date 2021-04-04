package com.ilham.githubmemberapp.network

import retrofit2.http.Query

class APIHelper(private val apiService: APIService) {

    suspend fun getUser(query: String) = apiService.getUserList(query)
    suspend fun getUserDataList(query: String) = apiService.getDetailUser(query)
    suspend fun getFollower(query: String) = apiService.getFollowerUser(query)
    suspend fun getFollowing(query: String) = apiService.getFollowingUser(query)
}