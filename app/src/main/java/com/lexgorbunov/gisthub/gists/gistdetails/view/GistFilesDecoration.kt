package com.lexgorbunov.gisthub.gists.gistdetails.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.lexgorbunov.gisthub.R

class GistFilesDecoration(context: Context,
                          private val getGistFileTitleHandler: GetGistFileTitleByPosHandler) : RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null
    private val bounds = Rect()
    private val titlePaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var headerTextSize: Int = 0
    private var headerHeight: Int = 0
    private var headerPaddingLeft: Int = 0

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        if (divider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this " + "DividerItemDecoration. Please set that attribute all call setDrawable()")
        }
        a.recycle()
        titlePaint.color = ContextCompat.getColor(context, R.color.colorWarmGreyTwo)
        headerTextSize = context.resources.getDimensionPixelSize(R.dimen.gist_file_header_text_size)
        headerHeight = context.resources.getDimensionPixelSize(R.dimen.gist_file_header_height)
        headerPaddingLeft = context.resources.getDimensionPixelSize(R.dimen.gist_file_header_padding_left)
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titlePaint.textSize = headerTextSize.toFloat()
    }

    fun setDrawable(drawable: Drawable) {
        divider = drawable
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (parent.layoutManager == null || divider == null) {
            return
        }
        drawVertical(c, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val bottom = bounds.bottom + Math.round(child.translationY)
            val top = bottom - divider!!.intrinsicHeight
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(canvas)

            val x = (bounds.left + headerPaddingLeft).toFloat()
            val y = bounds.top.toFloat() + (headerHeight + headerTextSize) / 2
            getGistFileTitleHandler.getTitleByPos(parent.getChildAdapterPosition(child))?.let {
                canvas.drawText(it, x, y, titlePaint)
            }
        }
        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        if (divider == null) {
            outRect.set(0, headerHeight, 0, 0)
            return
        }
        outRect.set(0, headerHeight, 0, divider!!.intrinsicHeight)
    }

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL

        private const val TAG = "DividerItem"
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}

interface GetGistFileTitleByPosHandler {
    fun getTitleByPos(pos: Int): String?
}
