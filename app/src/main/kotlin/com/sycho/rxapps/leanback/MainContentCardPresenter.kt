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
        // Presenter를 생성하는데 parent view가 null일 경우가 있는가?
        // 이런식으로 null이 올 수 없는 경우 강제로 non-null로 처리하도록 !!를 붙인다.
        val cardView = MainContentCardView(parent!!.context)
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true

        return Presenter.ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        if (item is MainContentData) {
            // viewHolder가 null이 아니어야 하고 ViewHolder.view는 MainContentCardView로 casting 되어야 한다.
            val cardView = viewHolder?.view as? MainContentCardView
            // 이렇게해서 획득한 MainContentCardView를 null 체크하고
            // method 호출이 편하게 apply로 감싸둔다.
            // apply 블록에서는 this가 해당 인스턴스이므로 바로 method 호출이 가능하다.
            cardView?.apply {
                setCardTitle(item.title)
                setCardTitleColor(item.title_color)
                setCardBackgroundColor(item.card_color)
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}