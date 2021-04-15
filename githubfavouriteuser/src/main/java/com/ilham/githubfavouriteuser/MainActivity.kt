package com.ilham.githubfavouriteuser

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ilham.githubfavouriteuser.data.FavouriteUser
import com.ilham.githubfavouriteuser.databinding.ActivityMainBinding
import com.ilham.githubfavouriteuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: FavouriteUserAdapter
    private lateinit var uriWithId: Uri
    private var favUser : FavouriteUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.favourite)
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(R.drawable.base_action_bar_background))
        }

        loadUserAsync()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                super.onBackPressed()
                return true
            }
        }
        return false
    }
    private fun loadUserAsync(){
        GlobalScope.launch(Dispatchers.Main){
            binding.favProgress.visibility = View.VISIBLE

            favUser = FavouriteUser()
            uriWithId = Uri.parse(CONTENT_URI.toString()+"/"+favUser?.login)

            val favouriteUserHelper =FavouriteUserHelper.getInstance(applicationContext)
            favouriteUserHelper.open()
            val deferredUser = async(Dispatchers.IO){
                val cursor = favouriteUserHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.favProgress.visibility = View.GONE
            val users = deferredUser.await()
            favouriteUserHelper.close()
            if (users.size>0){
                showItem(users)
            }else{
                showSnackbar("Tidak ada data")
            }
        }
    }
    private fun showItem(list : ArrayList<FavouriteUser>){
        binding.rvFavourite.layoutManager = LinearLayoutManager(this)
        adapter = FavouriteUserAdapter(list)
        binding.rvFavourite.adapter = adapter
    }
    private fun showSnackbar(message: String){
        Snackbar.make(binding.rvFavourite,message, Snackbar.LENGTH_SHORT).show()
    }
}