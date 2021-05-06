package com.ilham.githubfavouriteuser

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
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FavouriteUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.title)
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(R.drawable.base_action_bar_background))
        }
        loadUserAsync()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return false
    }


    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.favProgress.visibility = View.VISIBLE
            val deferredUser = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            binding.favProgress.visibility = View.GONE
            val users = deferredUser.await()
            if (users.size > 0) {
                showItem(users)
            } else {
                showSnackbar("Tidak ada data")
            }
        }
    }

    private fun showItem(list: ArrayList<FavouriteUser>) {
        binding.rvFavourite.layoutManager = LinearLayoutManager(this)
        adapter = FavouriteUserAdapter(list)
        binding.rvFavourite.adapter = adapter
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.rvFavourite, message, Snackbar.LENGTH_SHORT).show()
    }
}