package com.ilham.githubmemberapp.favouriteUserDatabase.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "users"
            const val LOGIN = "login"
            const val AVATAR = "avatar"
            const val USERNAME = "username"
        }
    }
}