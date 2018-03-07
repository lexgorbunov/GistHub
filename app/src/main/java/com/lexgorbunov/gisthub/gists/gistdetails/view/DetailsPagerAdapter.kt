package com.lexgorbunov.gisthub.gists.gistdetails.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R

class DetailsPagerAdapter(private val context: Context,
                          private val onPageInitiated: OnPageInitiated) : PagerAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)

    override fun getCount(): Int = Pages.values().size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val customPagerEnum = Pages.values()[position]
        val layout: View = inflater.inflate(customPagerEnum.layout, container, false)
        container.addView(layout)
        onPageInitiated.onPageInit(layout, position)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(Pages.values()[position].stringRes)
    }

    companion object {
        enum class Pages(@LayoutRes val layout: Int, @StringRes val stringRes: Int) {
            FILES(R.layout.page_files, R.string.label_files),
            HISTORY(R.layout.page_commits, R.string.label_commits);
        }
    }
}

interface OnPageInitiated {
    fun onPageInit(v: View, position: Int)
}
