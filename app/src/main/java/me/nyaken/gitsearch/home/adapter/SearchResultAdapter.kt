package me.nyaken.gitsearch.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.nyaken.gitsearch.home.MainViewModel
import me.nyaken.gitsearch.home.adapter.holder.GitRepositoryViewHolder
import me.nyaken.network.model.Item

class SearchResultAdapter(
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<GitRepositoryViewHolder>() {

    private val items: ArrayList<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GitRepositoryViewHolder.create(parent)

    override fun onBindViewHolder(holder: GitRepositoryViewHolder, position: Int) {
        holder.bindView(viewModel, items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clearItems() {
        notifyItemRangeRemoved(0, itemCount)
        items.clear()
    }

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun getAllItems() = items
}