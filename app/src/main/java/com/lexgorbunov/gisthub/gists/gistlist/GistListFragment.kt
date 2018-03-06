package com.lexgorbunov.gisthub.gists.gistlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R
import dagger.android.support.DaggerFragment

class GistListFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gist_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        with((activity as AppCompatActivity)) {
            setTitle(R.string.title_gist_list)
            with(supportActionBar) {
                if (this != null) {
                    setDisplayHomeAsUpEnabled(true)
                    setDisplayShowHomeEnabled(true)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun getInstance(): GistListFragment = GistListFragment()

    }

}
