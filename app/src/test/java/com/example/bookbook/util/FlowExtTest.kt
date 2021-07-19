package com.example.bookbook.base.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.bookbook.base.common.util.customDebounce
import com.example.bookbook.test_util_kit.CoroutineTestRule
import com.example.bookbook.test_util_kit.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher

@InternalCoroutinesApi
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class FlowExtTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: TestViewModel

    @Before
    fun setup() {
        runBlockingTest {
            viewModel = TestViewModel()
        }
    }

    @Test
    fun customDebounceTest() {
        runBlockingTest {
            viewModel.org.value = 1
            viewModel.org.value = 2
            viewModel.org.value = 3

            Assert.assertEquals(
                viewModel.org.getOrAwaitValue(),
                3
            )

            println("customDebounceTest() pass")
        }
    }

    @InternalCoroutinesApi
    class TestViewModel : ViewModel() {
        val org = MutableLiveData<Int>()

        init {
            viewModelScope.launch {
                org.asFlow()
                    .customDebounce(500L)
                    .distinctUntilChanged()
                    .collect {
                        pullTrigger(it)
                    }
            }
        }

        private fun pullTrigger(params: Int) {
            println("trigger pulled")
        }
    }
}