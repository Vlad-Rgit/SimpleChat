package com.simplechat.android.presentation.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PaddingDecorator(
    val mLeft: Int,
    val mTop: Int,
    val mRight: Int,
    val mBottom: Int,
): RecyclerView.ItemDecoration() {

    constructor(all: Int) : this(
        all,
        all,
        all,
        all
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.run {
            left = mLeft
            top = mTop
            right = mRight
            bottom = mBottom
        }
    }
}