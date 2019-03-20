package com.sycho.rxapps.leanback

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.leanback.widget.BaseCardView
import com.sycho.rxapps.R

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.19
 */
class MainContentCardView : BaseCardView {
    constructor(context: Context)
            : this(context, null)

    constructor(context: Context, attrs: AttributeSet?)
            : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initCard(context)
    }

    // 위 생성자들을 java로 decompile한 결과이다.
    // Context에 null이 들어갈경우 바로 NullPointerException이다...
//    public MainContentCardView(@NotNull Context context) {
//        Intrinsics.checkParameterIsNotNull(context, "context");
//        this(context, (AttributeSet)null);
//    }
//
//    public MainContentCardView(@NotNull Context context, @Nullable AttributeSet attrs) {
//        Intrinsics.checkParameterIsNotNull(context, "context");
//        this(context, attrs, 0);
//    }
//
//    public MainContentCardView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        Intrinsics.checkParameterIsNotNull(context, "context");
//        super(context, attrs, defStyleAttr);
//        this.initCard(context);
//    }

    private lateinit var mCardTitleTextView: TextView

    private fun initCard(context: Context) {
        isFocusable = true
        isFocusableInTouchMode = true

        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.widget_content_card_view, this)

        mCardTitleTextView = findViewById(R.id.card_title)
    }

    fun setCardTitle(title: String) {
        // mCardTitleTextView는 생성자에서 초기화한다.
        // null이 될 수 없으니 불필요한 null 처리는 필요 없다.
        mCardTitleTextView.text = title
    }

    fun setCardTitleColor(colorCode: String) {
        mCardTitleTextView.setTextColor(Color.parseColor(colorCode))
    }

    fun setCardBackgroundColor(colorCode: String) {
        mCardTitleTextView.setBackgroundColor(Color.parseColor(colorCode))
    }
}