package com.example.bookbook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bookbook.model.mock.FakeModel
import com.example.bookbook.model.mock.FakeRepository
import com.example.bookbook.model.mock.FakeSchema.mockBookSchema0
import com.example.bookbook.model.mock.FakeSchema.mockBookSchema1
import com.example.bookbook.model.mock.FakeSchema.mockBookSchema2
import com.example.bookbook.test_util_kit.CoroutineTestRule
import com.example.bookbook.test_util_kit.getOrAwaitValue
import com.meuus90.booksearcher.test_util_kit.flow.test
import io.mockk.MockKAnnotations
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class BookListViewModelTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: BookListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = BookListViewModel(FakeRepository())
        viewModel.initialize()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun postBookSchemaTest() {
        runBlockingTest {
            viewModel.postBookSchema(mockBookSchema0)

            val flow = viewModel.books
            flow.test(this) {
                assertValue(FakeModel.mockPagingData)
                println("postBookSchemaTest() pass")
            }
        }
    }

    @Test
    fun postBookSchemaWithDebounceTest() {
        runBlockingTest {
            viewModel.postBookSchemaWithDebounce(mockBookSchema0)
            viewModel.postBookSchemaWithDebounce(mockBookSchema1)
            viewModel.postBookSchemaWithDebounce(mockBookSchema2)

            Assert.assertEquals(
                viewModel.org.getOrAwaitValue(),
                mockBookSchema2
            )

            println("postBookSchemaWithDebounceTest() pass")
        }
    }
}