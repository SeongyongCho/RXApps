package com.sycho.rxapps.leanback

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.leanback.widget.BaseCardView
import com.sycho.rxapps.R

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.20
 */
class SubContentCardView : BaseCardView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initCard(context)
    }

    private lateinit var mCardTitleTextView: TextView

    private fun initCard(context: Context) {
        isFocusable = true
        isFocusableInTouchMode = true

        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.widget_sub_card_view, this)

        mCardTitleTextView = findViewById(R.id.sub_card_title)
    }

    fun setCardTitle(title: String) {
        mCardTitleTextView.text = title
    }
}