package com.example.bookbook.base.view.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.bookbook.R


fun ImageView.loadGlideImage(
    url: String,
    placeholderId: Int,
    loadOnlyFromCache: Boolean = false,
    onLoadingFinished: () -> Unit = {}
) {
    val placeholderResList = listOf(
        R.drawable.placeholder_red,
        R.drawable.placeholder_orange,
        R.drawable.placeholder_yellow,
        R.drawable.placeholder_green,
        R.drawable.placeholder_blue,
        R.drawable.placeholder_indigo,
        R.drawable.placeholder_purple
    )

    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }

    val placeholderRes = placeholderResList[placeholderId % placeholderResList.size]
    setBackgroundResource(placeholderRes)

    val requestOptions =
        RequestOptions.placeholderOf(placeholderRes)
            .priority(Priority.IMMEDIATE)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .override(Target.SIZE_ORIGINAL)
            .onlyRetrieveFromCache(loadOnlyFromCache)

    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .listener(listener)
        .error(R.drawable.shape_no_image)
        .into(this)

}