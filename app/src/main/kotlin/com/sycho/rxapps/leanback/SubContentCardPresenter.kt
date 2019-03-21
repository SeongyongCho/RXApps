package com.sycho.rxapps.leanback

import android.view.ViewGroup
import androidx.leanback.widget.Presenter

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.21
 */
class SubContentCardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val cardView = SubContentCardView(parent!!.context)
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true

        return Presenter.ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        if (item is SubContentData) {
            val cardView = viewHolder?.view as? SubContentCardView
            cardView?.apply {
                setCardTitle(item.title)
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}