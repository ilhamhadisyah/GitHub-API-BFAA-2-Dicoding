package com.ilham.githubmemberapp.favouriteUserDatabase.db

import android.net.Uri
import android.provider.BaseColumns
import java.net.URI

object DatabaseContract {
    const val AUTHORITY = "com.ilham.githubmemberapp"
    const val SCHEME = "content"

    class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "users"
            const val LOGIN = "login"
            const val AVATAR = "avatar"
            const val USERNAME = "username"

            val CONTENT_URI :  Uri= Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}