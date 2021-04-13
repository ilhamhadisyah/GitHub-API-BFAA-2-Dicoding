package com.ilham.githubmemberapp.favouriteUserDatabase.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.ilham.githubmemberapp.favouriteUserDatabase.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.ilham.githubmemberapp.favouriteUserDatabase.entity.FavouriteUser
import java.sql.SQLException

class FavouriteUserHelper(context: Context) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavouriteUserHelper? = null

        fun getInstance(context: Context): FavouriteUserHelper =
            INSTANCE ?: synchronized(this) {
            INSTANCE ?: FavouriteUserHelper(context)
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        database.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${DatabaseContract.UserColumns.LOGIN} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "${DatabaseContract.UserColumns.LOGIN} = ? ", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "${DatabaseContract.UserColumns.LOGIN} = '$id'", null)
    }

    fun getAllUser(): ArrayList<FavouriteUser> {
        val arrayList = ArrayList<FavouriteUser>()
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.UserColumns.Companion.LOGIN} DESC",
            null)
        cursor.moveToFirst()
        var favUser : FavouriteUser
        if (cursor.count>0){
            do {
                favUser = FavouriteUser()
                favUser.apply {
                    login = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.Companion.LOGIN))
                    avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(AVATAR))
                    userName = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME))
                }

                arrayList.add(favUser)
                cursor.moveToNext()
            }while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }
    fun insertUser(favouriteUser: FavouriteUser):Long{
        val args = ContentValues()
        args.apply {
            put(DatabaseContract.UserColumns.LOGIN,favouriteUser.login)
            put(AVATAR,favouriteUser.avatarUrl)
            put(USERNAME,favouriteUser.userName)
        }
        return database.insert(DATABASE_TABLE,null,args)
    }
    fun deleteUser(id: Int):Int{
        return database.delete(TABLE_NAME,"${DatabaseContract.UserColumns.LOGIN} ='$id'",null)
    }
}