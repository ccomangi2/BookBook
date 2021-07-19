package com.example.bookbook.base.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bookbook.di.Injectable

open class BaseFragment : Fragment(), Injectable {
    lateinit var baseActivity: BaseActivity
    private lateinit var context: Context

    override fun getContext() = context

    override fun onAttach(context: Context) {
        super.onAttach(context)

        baseActivity = (context as BaseActivity)
        this.context = context
    }

    internal fun addFragment(
        cls: Class<*>,
        backStackState: Int,
        bundle: Bundle? = null,
        sharedView: View? = null
    ): Fragment {
        return baseActivity.addFragment(cls, backStackState, bundle, sharedView)
    }

    internal fun hideKeyboard() {
        baseActivity.hideKeyboard()
    }
    
    internal fun showToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast?.show()
    }
}