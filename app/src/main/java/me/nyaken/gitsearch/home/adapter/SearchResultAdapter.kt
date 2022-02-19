package me.nyaken.gitsearch.home.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import me.nyaken.gitsearch.home.MainViewModel
import me.nyaken.gitsearch.home.adapter.holder.GitRepositoryViewHolder
import me.nyaken.network.model.Item

class SearchResultAdapter(
    private val viewModel: MainViewModel
) : PagingDataAdapter<Item, GitRepositoryViewHolder>(DataDifferentiator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GitRepositoryViewHolder.create(parent)

    override fun onBindViewHolder(holder: GitRepositoryViewHolder, position: Int) {
        holder.bindView(viewModel, getItem(position))
    }

    object DataDifferentiator : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}