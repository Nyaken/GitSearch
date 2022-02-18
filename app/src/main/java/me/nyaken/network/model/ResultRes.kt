package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultRes(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Item>
): Parcelable