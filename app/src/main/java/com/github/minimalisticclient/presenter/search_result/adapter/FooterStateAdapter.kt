package com.github.minimalisticclient.presenter.search_result.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.minimalisticclient.R

class FooterStateAdapter(
    private val retryCallback: () -> Unit
): LoadStateAdapter<FooterStateAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {
        private val pbLoading: ProgressBar by lazy { view.findViewById(R.id.pbLoading) }
        private val txtErrorMsg: AppCompatTextView by lazy { view.findViewById(R.id.txtErrorMsg) }
        private val btnRetry: AppCompatButton by lazy { view.findViewById(R.id.btnRetry) }

        init {
            btnRetry.setOnClickListener {
                retryCallback.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            pbLoading.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            txtErrorMsg.isVisible = loadState is LoadState.Error
            txtErrorMsg.text = (loadState as? LoadState.Error)?.error?.message
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item_loading, parent, false),
            retryCallback
        )
    }
}
