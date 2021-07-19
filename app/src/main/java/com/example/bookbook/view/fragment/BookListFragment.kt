package com.example.bookbook.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Fade
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook.R
import com.example.bookbook.base.constant.AppConfig
import com.example.bookbook.base.view.BaseActivity.Companion.BACK_STACK_STATE_ADD
import com.example.bookbook.base.view.BaseFragment
import com.example.bookbook.base.view.util.DetailsTransition
import com.example.bookbook.base.view.util.autoCleared
import com.example.bookbook.model.schema.book.BookRequest
import com.example.bookbook.view.fragment.BookInfoFragment.Companion.KEY_BOOK
import com.example.bookbook.view.fragment.adapter.BookListAdapter
import com.example.bookbook.viewmodel.BookListViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.Behavior.DragCallback
import kotlinx.android.synthetic.main.fragment_book_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import javax.inject.Inject


class BookListFragment : BaseFragment() {
    private var acvView by autoCleared<View>()

    @Inject
    internal lateinit var bookListViewModel: BookListViewModel

    private var searchSchema = BookRequest()

    private var isInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        acvView = inflater.inflate(R.layout.fragment_book_list, container, false)
        return acvView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        recyclerView?.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!isInitialized) {
            isInitialized = true
            bookListViewModel.initialize()

            initAppBar()
            initAdapter()

        } else {
            adapter.notifyDataSetChanged()
        }
    }

    private val adapter = BookListAdapter { item, sharedView ->
        val bundle = Bundle()
        bundle.putParcelable(KEY_BOOK, item)

        val newFragment = addFragment(
            BookInfoFragment::class.java,
            BACK_STACK_STATE_ADD,
            bundle,
            sharedView
        )

        newFragment.sharedElementEnterTransition = DetailsTransition()
        newFragment.enterTransition = Fade()
        exitTransition = Fade()
        newFragment.sharedElementReturnTransition = DetailsTransition()
    }

    private fun initAppBar() {
        val layoutParams = appbar_search.layoutParams as CoordinatorLayout.LayoutParams
        val behavior =
            layoutParams.behavior as AppBarLayout.Behavior? ?: AppBarLayout.Behavior()
        behavior.setDragCallback(object : DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
        layoutParams.behavior = behavior
    }


    private fun initAdapter() {
        recyclerView.adapter = adapter

        recyclerView.setItemViewCacheSize(AppConfig.recyclerViewCacheSize)
        recyclerView.setHasFixedSize(false)
        recyclerView.isVerticalScrollBarEnabled = false

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        layoutManager.isItemPrefetchEnabled = true
        recyclerView.layoutManager = layoutManager

        lifecycleScope.launchWhenCreated {
            bookListViewModel.books
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }
}