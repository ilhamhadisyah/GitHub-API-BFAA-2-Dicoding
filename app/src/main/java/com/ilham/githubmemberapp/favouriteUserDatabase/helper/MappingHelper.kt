package com.ilham.githubmemberapp.favouriteUserDatabase.helper

import android.database.Cursor
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.ilham.githubmemberapp.favouriteUserDatabase.entity.FavouriteUser

object MappingHelper {
    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<FavouriteUser>{
        val userList = ArrayList<FavouriteUser>()
        usersCursor?.apply {
            while (moveToNext()){
                val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
                val avatarUrl = getString(getColumnIndexOrThrow(AVATAR))
                val userName = getString(getColumnIndexOrThrow(USERNAME))
                userList.add(FavouriteUser(login,avatarUrl,userName))
            }
        }
        return userList
    }


}