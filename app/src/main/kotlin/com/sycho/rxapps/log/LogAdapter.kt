package com.sycho.rxapps.log

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sycho.rxapps.R
import kotlinx.android.synthetic.main.widget_log_card.view.*

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.18
 */
class LogAdapter(logItemList: ArrayList<String>) : RecyclerView.Adapter<LogAdapter.ViewHolder>() {

    // primary constructor로 넘어온 logItemList로 바로 대입한다.
    // 프로퍼티 대입만 하는 생성자의 경우 이걸로 끝이다.
    private var logList: ArrayList<String> = logItemList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnFocusChangeListener, View.OnClickListener, View.OnKeyListener {

        // 프로퍼티를 이곳에 추가하면 getter가 만들어진다.
        // 인스턴스 지정을 이곳에 하면 생성자에서 findViewById() 호출된다.
        // Activity나 Fragment처럼 HashMap _$_findViewCache 캐시가 없기 때문에
        // 이렇게 합성 프로퍼티 자체를 가져오도록 하면 findViewById() 호출은 생성자에서의 한 번으로 끝이다.
        val logTextView: TextView = itemView.tv_log

        // primary constructor
        // constructor(itemView: View) {}
        init {
            itemView.onFocusChangeListener = this
            itemView.setOnClickListener(this)
            itemView.setOnKeyListener(this)
        }

        override fun onFocusChange(v: View?, hasFocus: Boolean) {

        }

        override fun onClick(v: View?) {

        }

        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            return false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.widget_log_card, parent, false)
        val viewHolder = ViewHolder(v)

        viewHolder.itemView.isClickable = true
        viewHolder.itemView.isFocusable = true
        viewHolder.itemView.isFocusableInTouchMode = true

        return viewHolder
    }

    override fun getItemCount(): Int {
        return logList.size
    }

    override fun onBindViewHolder(holder: LogAdapter.ViewHolder, position: Int) {
        if (logList.size > position) {
            with(holder) {
                logTextView.text = logList[position]
            }
        }
    }


}