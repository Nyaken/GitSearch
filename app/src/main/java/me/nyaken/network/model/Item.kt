package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: Int,
    val node_id: String,
    val name: String,
    val full_name: String,
    val owner: Owner,
    val html_url: String,
    val description: String,
    val language: String,
    val created_at: String,
    val updated_at: String,
    val pushed_at: String,
    val stargazers_count: Int,
    val watchers_count: Int,
    val forks_count: Int,
    val license: License,
    val topics: List<String>,
    val score: Float
): Parcelable {

    @Parcelize
    data class Owner(
        val avatar_url: String,
        val html_url: String,
    ): Parcelable

    @Parcelize
    data class License(
        val name: String,
        val url: String
    ): Parcelable

}