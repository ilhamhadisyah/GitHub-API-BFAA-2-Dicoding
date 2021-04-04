package com.ilham.githubmemberapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchUserItem(

    @Expose
    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @Expose
    @SerializedName("events_url")
    val eventsUrl: String?,

    @Expose
    @SerializedName("followers_url")
    val followersUrl: String?,

    @Expose
    @SerializedName("following_url")
    val followingUrl: String?,

    @Expose
    @SerializedName("gists_url")
    val gistsUrl: String?,

    @Expose
    @SerializedName("gravatar_id")
    val gravatarId: String?,

    @Expose
    @SerializedName("html_url")
    val htmlUrl: String?,

    @Expose
    @SerializedName("id")
    val id: Int?,

    @Expose
    @SerializedName("login")
    val login: String?,

    @Expose
    @SerializedName("node_id")
    val nodeId: String?,

    @Expose
    @SerializedName("organizations_url")
    val organizationsUrl: String?,

    @Expose
    @SerializedName("received_events_url")
    val receivedEventsUrl: String?,

    @Expose
    @SerializedName("repos_url")
    val reposUrl: String?,

    @Expose
    @SerializedName("site_admin")
    val siteAdmin: Boolean,

    @Expose
    @SerializedName("starred_url")
    val starredUrl: String?,

    @Expose
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String?,

    @Expose
    @SerializedName("type")
    val type: String?,

    @Expose
    @SerializedName("url")
    val url: String?
)
