package com.ilham.githubmemberapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract
import com.ilham.githubmemberapp.favouriteUserDatabase.db.FavouriteUserHelper

class UserFavouriteProvider : ContentProvider() {

    companion object{
        private const val USER =1
        private const val USER_LOGIN = 2
        private lateinit var favouriteUserHelper: FavouriteUserHelper

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }
    init {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.UserColumns.TABLE_NAME, USER)
        sUriMatcher.addURI(DatabaseContract.AUTHORITY,"${DatabaseContract.UserColumns.TABLE_NAME}", USER_LOGIN)
    }
    override fun onCreate(): Boolean {
        favouriteUserHelper = FavouriteUserHelper.getInstance(context as Context)
        favouriteUserHelper.open()
        return true
    }


    override fun query(uri: Uri,strings: Array<String>?, s: String?, strings1:Array<String>?,s1:String?): Cursor? {
        return when(sUriMatcher.match(uri)){
            USER -> favouriteUserHelper.queryAll()
            //USER_LOGIN -> favouriteUserHelper.queryById(uri.lastPathSegment.toString())
            USER_LOGIN -> favouriteUserHelper.queryAll()
            else->null
        }
    }


    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added = favouriteUserHelper.insert(values)
        /*
        val added :Long= when(USER){
            sUriMatcher.match(uri) -> favouriteUserHelper.insert(values)
            else->0
        }

         */
        context?.contentResolver?.notifyChange(DatabaseContract.UserColumns.CONTENT_URI,null)
        return Uri.parse("${DatabaseContract.UserColumns.CONTENT_URI}/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted = favouriteUserHelper.deleteById(uri.lastPathSegment.toString())
        /*
        val deleted : Int = when(USER_LOGIN){
            sUriMatcher.match(uri) -> favouriteUserHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

         */
        context?.contentResolver?.notifyChange(DatabaseContract.UserColumns.CONTENT_URI,null)
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
        context?.contentResolver?.notifyChange(DatabaseContract.UserColumns.CONTENT_URI,null)
        return updated
    }
}