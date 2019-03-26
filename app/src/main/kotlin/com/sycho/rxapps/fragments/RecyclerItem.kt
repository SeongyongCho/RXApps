package com.sycho.rxapps.fragments

import android.graphics.drawable.Drawable

/**
 * kotlin의 data 클래스가 있으니 lombok 같은건 굳이 필요 없다
 *
 * @author Cho Seong-yong
 * @since 2019.03.26
 */
data class RecyclerItem(val image: Drawable,
                        val title: String)