package com.github.mininalisticclient.presenter.search_result.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mininalisticclient.R
import com.github.mininalisticclient.domain.entities.User

class UserListAdapter: PagingDataAdapter<User, UserListAdapter.ViewHolder>(DataDiffer) {
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val imvAvatar: AppCompatImageView by lazy { view.findViewById(R.id.imvAvatar) }
        private val tvName: AppCompatTextView by lazy { view.findViewById(R.id.tvName) }

        fun bind(user: User?) {
            Glide.with(view.context).load(user?.avatarUrl).into(imvAvatar)
            tvName.text = user?.login
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item, parent, false)
        )
    }

    object DataDiffer : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}