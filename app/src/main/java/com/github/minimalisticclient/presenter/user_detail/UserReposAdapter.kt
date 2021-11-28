package com.github.minimalisticclient.presenter.user_detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.github.minimalisticclient.R
import com.github.minimalisticclient.domain.entities.Repo

class UserReposAdapter: RecyclerView.Adapter<UserReposAdapter.ViewHolder>() {

    private val listRepos = ArrayList<Repo>()

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        private val context by lazy { v.context }
        private val txtRepoName: AppCompatTextView by lazy { v.findViewById(R.id.txtRepoName) }
        private val txtLanguage: AppCompatTextView by lazy { v.findViewById(R.id.txtLanguage) }
        private val txtDescription: AppCompatTextView by lazy { v.findViewById(R.id.txtDescription) }

        fun bind(repo: Repo) {
            txtRepoName.text = context.getString(R.string.repo_name, repo.name)
            txtLanguage.text = context.getString(R.string.repo_language, repo.language ?: "")
            txtDescription.text = context.getString(R.string.repo_description, repo.description ?: "")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_repo_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listRepos[position])
    }

    override fun getItemCount(): Int {
        return listRepos.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(repos: List<Repo>) {
        listRepos.clear()
        listRepos.addAll(repos)
        notifyDataSetChanged()
    }
}
