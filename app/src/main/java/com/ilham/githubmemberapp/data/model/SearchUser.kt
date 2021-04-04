package com.ilham.githubmemberapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ilham.githubmemberapp.utils.Resources


data class SearchUser (
    @Expose
    @SerializedName("items")
    val itemUsers: ArrayList<SearchUserItem>
)