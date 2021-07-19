package com.example.bookbook.view.fragment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.bookbook.R
import com.example.bookbook.base.view.util.BaseViewHolder
import com.example.bookbook.base.view.util.loadGlideImage
import com.example.bookbook.model.schema.book.BookItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.book_list_item.view.*

class BookListAdapter(val doOnClick: (item: BookItem, sharedView: View) -> Unit) :
    PagingDataAdapter<BookItem, BaseViewHolder<BookItem>>(DIFF_CALLBACK) {
    companion object {
        private val PAYLOAD_TITLE = Any()

        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<BookItem>() {
                override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
                    oldItem.isbn == newItem.isbn

                override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
                    oldItem == newItem

                override fun getChangePayload(oldItem: BookItem, newItem: BookItem): Any? {
                    return if (sameExceptTitle(oldItem, newItem)) PAYLOAD_TITLE
                    else null
                }
            }

        private fun sameExceptTitle(
            oldItem: BookItem,
            newItem: BookItem
        ): Boolean {
            return oldItem.copy(isbn = newItem.isbn) == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BookItem> {
        val inflater = LayoutInflater.from(parent.context.applicationContext)
        val view = inflater.inflate(R.layout.book_list_item, parent, false)
        return BookItemHolder(view, this)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BookItem>, position: Int) {
        getItem(position)?.let { item ->
            holder.bindItemHolder(holder, item, position)
        }
    }

    class BookItemHolder(
        override val containerView: View,
        private val adapter: BookListAdapter
    ) : BaseViewHolder<BookItem>(containerView), LayoutContainer {

        override fun bindItemHolder(
            holder: BaseViewHolder<BookItem>,
            item: BookItem,
            position: Int
        ) {
            containerView.apply {
                book_img.loadGlideImage(item.thumbnail, item.databaseId)

                book_img.transitionName = item.databaseId.toString() + item.isbn

                book_title.text = item.title

                if (item.authors.isEmpty()) {
                    book_writer.text = ""
                } else {
                    book_writer.text = item.authors.joinToString(",")
                }

                book_item.setOnClickListener {
                    adapter.doOnClick(item, book_img)
                }
            }
        }

        override fun onItemSelected() {
            containerView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            containerView.setBackgroundColor(0)
        }
    }
}