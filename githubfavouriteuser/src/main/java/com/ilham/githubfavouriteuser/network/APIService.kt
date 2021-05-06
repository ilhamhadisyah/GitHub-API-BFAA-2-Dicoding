package com.ilham.githubfavouriteuser.network

import com.ilham.githubfavouriteuser.data.UserDetail
import com.ilham.githubfavouriteuser.data.UserFollowersItem
import com.ilham.githubfavouriteuser.data.UserFollowingItem
import retrofit2.http.GET
import retrofit2.http.Path


interface APIService {

    @GET("users/{username}")// user detail endpoint
    suspend fun getDetailUser(
        @Path("username") username: String
    ): UserDetail

    @GET("users/{username}/followers")//user follower endpoint
    suspend fun getFollowerUser(
        @Path("username") username: String
    ): List<UserFollowersItem>

    @GET("users/{username}/following")//following of user endpoint
    suspend fun getFollowingUser(
        @Path("username") username: String
    ): List<UserFollowingItem>
}