package com.ilham.githubfavouriteuser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavouriteUser(
    var login: String? = null,
    var avatarUrl: String? = null,
    var userName: String? = null
) : Parcelable