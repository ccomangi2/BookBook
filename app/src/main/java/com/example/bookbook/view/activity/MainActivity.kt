package com.example.bookbook.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookbook.R
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector


class MainActivity : AppCompatActivity(), HasAndroidInjector {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun androidInjector(): AndroidInjector<Any> {
        TODO("Not yet implemented")
    }
}