package com.ilham.githubmemberapp.ui.viewAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.githubmemberapp.R
import com.ilham.githubmemberapp.databinding.UserItemListBinding
import com.ilham.githubmemberapp.data.model.SearchUserItem
import com.ilham.githubmemberapp.ui.view.detail.DetailUser
import com.squareup.picasso.Picasso

class ItemAdapter(private val list: ArrayList<SearchUserItem>) :
    RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = UserItemListBinding.bind(view)

        internal fun bind(searchUserItem: SearchUserItem?) {
            if (searchUserItem != null) {
                binding.username.text = searchUserItem.login
                Picasso.get().load(searchUserItem.avatarUrl)
                    .into(binding.avatar)
            }
        }

        init {
            view.setOnClickListener {
                val position: Int = adapterPosition
                val loginKey : String? =list[position].login
                val intent = Intent(itemView.context,DetailUser::class.java)
                intent.putExtra(DetailUser.LOGIN_KEY,loginKey)
                itemView.context.startActivity(intent)

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ItemHolder {
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

    fun addUsers(users: List<SearchUserItem>) {
        this.list.apply {
            clear()
            addAll(users)
        }

    }
}