package com.ilham.githubmemberapp.ui.viewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.githubmemberapp.R
import com.ilham.githubmemberapp.databinding.UserItemFavouriteBinding
import com.ilham.githubmemberapp.favouriteUserDatabase.entity.FavouriteUser
import com.squareup.picasso.Picasso

class FavouriteUserAdapter(val users: ArrayList<FavouriteUser>) :
    RecyclerView.Adapter<FavouriteUserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemFavouriteBinding.bind(itemView)

        fun bind(favouriteUser: FavouriteUser) {
            binding.apply {
                username.text = favouriteUser.userName
                Picasso.get().load(favouriteUser.avatarUrl).into(avatar)
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