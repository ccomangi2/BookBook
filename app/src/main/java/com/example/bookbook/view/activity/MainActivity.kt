package com.example.bookbook.view.activity

import android.os.Bundle
import com.example.bookbook.R
import com.example.bookbook.base.view.BaseActivity
import com.example.bookbook.view.fragment.BookListFragment


class MainActivity : BaseActivity() {
    override val frameLayoutId = R.id.contentFrame

    override fun setContentView() {
        setContentView(R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(BookListFragment::class.java)
    }
}