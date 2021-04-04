package com.ilham.githubmemberapp.network

import com.ilham.githubmemberapp.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("search/users?")//user search endpoint
    //@Headers("Authorization: token c603e2a6b21af3e2123a2a6d702c0b3b6f35b564")
    suspend fun getUserList(
        @Query("q") q: String
    ): SearchUser


    @GET("users/{username}")// user detail endpoint
    //@Headers("Authorization: token c603e2a6b21af3e2123a2a6d702c0b3b6f35b564")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): UserDetail

    @GET("users/{username}/followers")//user follower endpoint
    //@Headers("Authorization: token c603e2a6b21af3e2123a2a6d702c0b3b6f35b564")
    suspend fun getFollowerUser(
        @Path("username") username: String
    ): List<UserFollowersItem>

    @GET("users/{username}/following")//following of user endpoint
    //@Headers("Authorization: token c603e2a6b21af3e2123a2a6d702c0b3b6f35b564")
    suspend fun getFollowingUser(
        @Path("username") username: String
    ): List<UserFollowingItem>
}