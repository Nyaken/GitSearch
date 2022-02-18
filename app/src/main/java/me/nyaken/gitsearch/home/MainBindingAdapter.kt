package me.nyaken.gitsearch.home

import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import me.nyaken.common.SearchSort
import me.nyaken.gitsearch.R

@BindingAdapter("sort_adapter")
fun AppCompatSpinner.sortAdapter(items: List<SearchSort>) {
    val dataAdapter: ArrayAdapter<SearchSort> =
        ArrayAdapter<SearchSort>(context, R.layout.item_search_sort, items)
    dataAdapter.setDropDownViewResource(R.layout.item_search_sort_dropdown)
    adapter = dataAdapter
}