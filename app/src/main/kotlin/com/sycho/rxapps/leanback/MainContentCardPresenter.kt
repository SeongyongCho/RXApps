package com.sycho.rxapps.leanback

import android.view.ViewGroup
import androidx.leanback.widget.Presenter

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.19
 */
class MainContentCardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val cardView = MainContentCardView(parent!!.context)
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true

        return Presenter.ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        if (item is MainContentData) {
            val cardView = viewHolder?.view as MainContentCardView
            cardView.setCardTitle(item.title)
            cardView.setCardTitleColor(item.title_color)
            cardView.setCardBackgroundColor(item.card_color)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}