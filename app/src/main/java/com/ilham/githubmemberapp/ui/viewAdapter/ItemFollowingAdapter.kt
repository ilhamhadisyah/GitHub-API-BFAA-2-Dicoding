package com.ilham.githubmemberapp.ui.viewAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.githubmemberapp.R
import com.ilham.githubmemberapp.databinding.UserItemListBinding
import com.ilham.githubmemberapp.data.model.UserFollowingItem
import com.ilham.githubmemberapp.ui.view.detail.DetailUserActivity
import com.squareup.picasso.Picasso

class ItemFollowingAdapter(private val list: ArrayList<UserFollowingItem>) :
    RecyclerView.Adapter<ItemFollowingAdapter.ItemHolder>() {

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = UserItemListBinding.bind(view)

        internal fun bind(followers: UserFollowingItem?) {
            if (followers != null) {
                binding.apply {
                    username.text = followers.login
                    Picasso.get().load(followers.avatarUrl)
                        .into(avatar)
                    url.text = followers.htmlUrl
                }
            }
        }

        init {
            view.setOnClickListener {
                val position: Int = adapterPosition
                val loginKey: String? = list[position].login
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.LOGIN_KEY, loginKey)
                itemView.context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemFollowingAdapter.ItemHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item_list, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addUsers(users: List<UserFollowingItem>) {
        this.list.apply {
            clear()
            addAll(users)
        }
    }
}