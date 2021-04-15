package com.ilham.githubfavouriteuser.network

import com.ilham.githubfavouriteuser.model.SearchUser
import com.ilham.githubfavouriteuser.model.UserDetail
import com.ilham.githubfavouriteuser.model.UserFollowersItem
import com.ilham.githubfavouriteuser.model.UserFollowingItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("search/users?")//user search endpoint
    suspend fun getUserList(
        @Query("q") q: String
    ): SearchUser

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