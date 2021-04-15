package com.ilham.githubfavouriteuser.repository

import com.ilham.githubfavouriteuser.network.APIHelper

class MainRepository(private val apiHelper: APIHelper) {
    suspend fun getUsers(query: String) = apiHelper.getUser(query)
    suspend fun getUserDataList(query: String) = apiHelper.getUserDataList(query)
    suspend fun getFollowerList(query: String) = apiHelper.getFollower(query)
    suspend fun getFollowingList(query: String) = apiHelper.getFollowing(query)

}