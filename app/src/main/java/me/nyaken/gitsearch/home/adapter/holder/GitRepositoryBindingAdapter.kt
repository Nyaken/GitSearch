package me.nyaken.gitsearch.home.adapter.holder

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

private val dateTimeFormat =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA).apply {
        timeZone = TimeZone.getTimeZone("Asia/Seoul")
    }

private val convertTimeFormat =
    SimpleDateFormat("yy.MM.dd a hh:mm", Locale.KOREA)

private fun Int?.getCurrencyString() = String.format("%,d", this)

@BindingAdapter("item_format_resource", "item_convert_date_time")
fun TextView.setConvertDateTime(
    resource: String,
    date: String
) {
    val originTime = dateTimeFormat.parse(date)
    originTime?.let {
        val convertDate = convertTimeFormat.format(it)
        text = String.format(
            resource,
            convertDate
        )
    }
}

@BindingAdapter("item_avatar")
fun ImageView.setAvatar(
    url: String?
) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions.bitmapTransform(MultiTransformation(CircleCrop())))
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("item_format_resource", "item_count")
fun TextView.setStar(
    resource: String,
    count: Int
) {
    text =
        String.format(
            resource,
            count.getCurrencyString()
        )
}
