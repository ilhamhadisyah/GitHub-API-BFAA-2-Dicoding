package com.ilham.githubmemberapp.network

import com.ilham.githubmemberapp.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
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