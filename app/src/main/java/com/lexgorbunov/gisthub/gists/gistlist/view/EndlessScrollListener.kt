package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

class EndlessScrollListener : RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 5
    // The current offset index of data you have loaded
    private var currentPage = 0
    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0
    // True if we are still waiting for the last set of data to load.
    private var loading = true
    private var retry = false
    // Sets the starting page index
    private var startingPageIndex = 0

    internal var mLayoutManager: RecyclerView.LayoutManager
    internal lateinit var onLoadMoreListener: OnGistLoadMore

    constructor(startingPageIndex: Int, layoutManager: LinearLayoutManager, onLoadMoreListener: OnGistLoadMore) {
        this.startingPageIndex = startingPageIndex
        this.currentPage = startingPageIndex
        this.mLayoutManager = layoutManager
        this.onLoadMoreListener = onLoadMoreListener
    }

    constructor(startingPageIndex: Int, layoutManager: GridLayoutManager, onLoadMoreListener: OnGistLoadMore) {
        this.startingPageIndex = startingPageIndex
        this.currentPage = startingPageIndex
        this.mLayoutManager = layoutManager
        this.onLoadMoreListener = onLoadMoreListener
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    constructor(startingPageIndex: Int, layoutManager: StaggeredGridLayoutManager, onLoadMoreListener: OnGistLoadMore) {
        this.startingPageIndex = startingPageIndex
        this.currentPage = startingPageIndex
        this.mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (!retry) {
            if (loading && totalItemCount > previousTotalItemCount) {
                loading = false
                previousTotalItemCount = totalItemCount
            }
        } else {
            loading = false
            retry = false
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMoreListener.onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    // Call this method whenever performing new searches
    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    fun allowTryRetry() {
        retry = true
    }
}

interface OnGistLoadMore {
    // Defines the process for actually loading more data based on page
    fun onLoadMore(page: Int, totalItemsCount: Int, recycler: RecyclerView)
}
