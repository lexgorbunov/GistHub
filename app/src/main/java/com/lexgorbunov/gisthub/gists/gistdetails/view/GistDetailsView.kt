package com.lexgorbunov.gisthub.gists.gistdetails.view

import android.view.View
import com.lexgorbunov.gisthub.app.utils.toast
import com.lexgorbunov.gisthub.app.utils.tryParseError
import com.lexgorbunov.gisthub.gists.entity.Gist
import javax.inject.Inject

interface GistDetailsView {

    fun init(view: View)

    //fun showToast(text: String)
    fun showError(throwable: Throwable)

    fun displayDetails(gist: Gist)
}

class GistDetailsViewImpl @Inject constructor(): GistDetailsView {

    private lateinit var view: View

    override fun init(view: View) {
        this.view = view
        val context = view.context


    }

    //override fun showToast(text: String) {
    //    view.context.toast(text)
    //}

    override fun displayDetails(gist: Gist) {

    }

    override fun showError(throwable: Throwable) {
        tryParseError(view.context, throwable)?.let {
            view.context.toast(it.localizedMessage)
        }
    }

}
