package com.sycho.rxapps.fragments

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sycho.rxapps.R

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.26
 */
class RecyclerViewDecoration(context: Context?) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable? = context?.resources?.getDrawable(R.drawable.divider_recycler_item, null)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        mDivider?.apply {
            outRect.bottom = intrinsicHeight
        }
    }
}