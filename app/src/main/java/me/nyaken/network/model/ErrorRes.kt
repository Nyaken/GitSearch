package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorRes(
    val message: String,
    val documentation_url: String
): Parcelable