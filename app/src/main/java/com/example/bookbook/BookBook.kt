package com.example.bookbook

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class BookBook : Application(), LifecycleObserver, HasAndroidInjector {
    override fun androidInjector(): AndroidInjector<Any> {
        TODO("Not yet implemented")
    }
}