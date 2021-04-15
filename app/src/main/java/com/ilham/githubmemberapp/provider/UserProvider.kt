package com.ilham.githubmemberapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract.AUTHORITY
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.ilham.githubmemberapp.favouriteUserDatabase.db.FavouriteUserHelper

class UserProvider:ContentProvider() {
    companion object{
        private const val USER =1
        private const val USER_LOGIN = 2
        private lateinit var favouriteUserHelper: FavouriteUserHelper

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }
    init {
        sUriMatcher.addURI(AUTHORITY,TABLE_NAME, USER)
        sUriMatcher.addURI(AUTHORITY,"$TABLE_NAME/#", USER_LOGIN)
    }
    override fun onCreate(): Boolean {
        favouriteUserHelper = FavouriteUserHelper.getInstance(context as Context)
        favouriteUserHelper.open()
        return true
    }

    override fun query(uri: Uri,strings: Array<String>?, s: String?, strings1:Array<String>?,s1:String?): Cursor? {
        return when(sUriMatcher.match(uri)){
            USER -> favouriteUserHelper.queryAll()
            USER_LOGIN -> favouriteUserHelper.queryById(uri.lastPathSegment.toString())
            else->null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added :Long= when(USER){
            sUriMatcher.match(uri) -> favouriteUserHelper.insert(values)
            else->0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted : Int = when(USER_LOGIN){
            sUriMatcher.match(uri) -> favouriteUserHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return deleted
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated : Int = when(USER_LOGIN){
            sUriMatcher.match(uri) -> favouriteUserHelper.update(uri.lastPathSegment.toString(),values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return updated
    }
}