package com.ilham.githubfavouriteuser.network


class APIHelper(private val apiService: APIService) {

    suspend fun getUserDataList(query: String) = apiService.getDetailUser(query)
    suspend fun getFollower(query: String) = apiService.getFollowerUser(query)
    suspend fun getFollowing(query: String) = apiService.getFollowingUser(query)
}