package me.nyaken.gitsearch.home.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.nyaken.gitsearch.databinding.ItemSearchResultViewHolderBinding
import me.nyaken.gitsearch.home.MainViewModel
import me.nyaken.network.model.Item

class GitRepositoryViewHolder(
    private val binding: ItemSearchResultViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(
        viewModel: MainViewModel,
        item: Item?
    ) {
        binding.vm = viewModel
        binding.item = item
    }

    companion object {
        fun create(parent: ViewGroup): GitRepositoryViewHolder {
            val binding = ItemSearchResultViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return GitRepositoryViewHolder(binding)
        }
    }
}
