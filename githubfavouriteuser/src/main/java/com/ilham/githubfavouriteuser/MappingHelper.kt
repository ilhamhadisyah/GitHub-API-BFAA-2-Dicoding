package com.ilham.githubfavouriteuser

import android.database.Cursor
import com.ilham.githubfavouriteuser.data.FavouriteUser
import com.ilham.githubfavouriteuser.db.DatabaseContract
import com.ilham.githubfavouriteuser.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.ilham.githubfavouriteuser.db.DatabaseContract.UserColumns.Companion.USERNAME


object MappingHelper {
    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<FavouriteUser> {
        val userList = ArrayList<FavouriteUser>()
        usersCursor?.apply {
            while (moveToNext()) {
                val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
                val avatarUrl = getString(getColumnIndexOrThrow(AVATAR))
                val userName = getString(getColumnIndexOrThrow(USERNAME))
                userList.add(FavouriteUser(login, avatarUrl, userName))
            }
        }
        return userList
    }
}