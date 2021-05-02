package com.kido1611.dicoding.moviecatalogue.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kido1611.dicoding.moviecatalogue.R

fun ImageView.loadImageFromTMDB(path: String?) {
    if (path.isNullOrBlank()) {
        return
    }

    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 6f
    circularProgressDrawable.centerRadius = 48f
    circularProgressDrawable.setColorSchemeColors(
        ContextCompat.getColor(
            context,
            R.color.teal_700
        )
    )
    circularProgressDrawable.start()

    Glide.with(this.context)
        .load("https://image.tmdb.org/t/p/w500$path")
        .apply(
            RequestOptions.placeholderOf(circularProgressDrawable)
                .error(R.drawable.ic_error)
        )
        .into(this)
}