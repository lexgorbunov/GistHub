package com.lexgorbunov.gisthub.gists.gistlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.gistlist.presenter.GistListPresenter
import com.lexgorbunov.gisthub.gists.gistlist.view.GistListView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_gist_list.*
import javax.inject.Inject

class GistListFragment : DaggerFragment() {

    @Inject
    lateinit var presenter: GistListPresenter
    @Inject
    lateinit var listView: GistListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gist_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.init(gist_list_parent, presenter)
        presenter.init(listView, fragmentManager!!)
    }

    override fun onDestroyView() {
        presenter.destroy()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        with((activity as AppCompatActivity)) {
            setTitle(R.string.title_gist_list)
            with(supportActionBar) {
                if (this != null) {
                    setDisplayHomeAsUpEnabled(false)
                    setDisplayShowHomeEnabled(false)
                }
            }
        }
    }

    companion object {

        fun getInstance(): GistListFragment = GistListFragment()

    }

}
