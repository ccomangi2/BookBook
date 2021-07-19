package com.example.bookbook.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.doOnPreDraw
import com.example.bookbook.R
import com.example.bookbook.base.common.util.NumberTools
import com.example.bookbook.base.common.util.TimeTools
import com.example.bookbook.view.activity.MainActivity
import com.example.bookbook.base.view.BaseFragment
import com.example.bookbook.base.view.util.autoCleared
import com.example.bookbook.base.view.util.loadGlideImage
import com.example.bookbook.model.schema.book.BookItem
import kotlinx.android.synthetic.main.fragment_bookinfo.*
import javax.inject.Inject


class BookInfoFragment : BaseFragment() {
    companion object {
        const val KEY_BOOK = "KEY_BOOK"
    }

    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    private lateinit var bookItem: BookItem
    private var acvView by autoCleared<View>()

    @Inject
    internal lateinit var bookinfoViewModel: BookInfoFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        acvView = inflater.inflate(R.layout.fragment_bookinfo, container, false)

        return acvView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        book_img?.doOnPreDraw { startPostponedEnterTransition() }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = arguments
        bookItem = bundle?.getParcelable(KEY_BOOK)!!

        //도서 표지 썸네일
        book_img.transitionName = bookItem.databaseId.toString() + bookItem.isbn
        book_img.loadGlideImage(bookItem.thumbnail, bookItem.databaseId, true)

        //도서 제목
        book_title.text = bookItem.title

        //도서 출판 날짜
        book_date.text =
            TimeTools.convertDateFormat(bookItem.datetime, TimeTools.ISO8601, TimeTools.YMD)

        //도서 정가
        book_price.text = context.getString(
            R.string.currency_korean,
            NumberTools.convertToString(bookItem.price)
        )

        //도서 출판사
        book_house.text = bookItem.publisher

        //도서 저자
        if (bookItem.authors.isEmpty()) {
            book_writer.text = ""
        } else {
            book_writer.text = bookItem.authors.joinToString(",")
        }

        //도서 소개
        book_about.text = getString(R.string.contents_format, bookItem.contents)

        //아이템 번호
        item_num_tv.text

        //뒤로가기 버튼을 눌렀을 경우
        back_btn.setOnClickListener {
            mainActivity.onBackPressed()
        }
    }
}