package com.example.bookbook.base.view.util

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(val view: View) : RecyclerView.ViewHolder(view),
    ItemHolderBinder<T> {
    val context: Context = view.context
}