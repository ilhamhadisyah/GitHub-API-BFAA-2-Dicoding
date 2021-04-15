package com.ilham.githubfavouriteuser.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SearchUser (
    @Expose
    @SerializedName("items")
    val itemUsers: ArrayList<SearchUserItem>
)