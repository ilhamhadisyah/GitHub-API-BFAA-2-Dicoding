package com.ilham.githubfavouriteuser

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ilham.githubfavouriteuser.data.FavouriteUser
import com.ilham.githubfavouriteuser.databinding.UserItemFavouriteBinding
import com.ilham.githubfavouriteuser.db.DatabaseContract
import com.ilham.githubfavouriteuser.detail.DetailUserActivity

class FavouriteUserAdapter(val users: ArrayList<FavouriteUser>) :
    RecyclerView.Adapter<FavouriteUserAdapter.UserViewHolder>() {
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemFavouriteBinding.bind(itemView)
        private lateinit var uriWithId: Uri
        fun bind(favouriteUser: FavouriteUser) {
            binding.apply {
                username.text = favouriteUser.userName

                binding.deleteBtn.setOnClickListener {
                    val position = adapterPosition
                    val loginKey = favouriteUser.login
                    uriWithId =
                        Uri.parse(DatabaseContract.UserColumns.CONTENT_URI.toString() + "/" + loginKey)
                    itemView.context.contentResolver.delete(uriWithId, null, null)
                    users.removeAt(position)
                    notifyItemRemoved(position - 1)
                    Toast.makeText(itemView.context, "Deleted", Toast.LENGTH_SHORT).show()
                }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.LOGIN_KEY, favouriteUser.login)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item_favourite, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = this.users.size

}