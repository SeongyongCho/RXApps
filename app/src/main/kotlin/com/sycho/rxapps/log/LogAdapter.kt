package com.sycho.rxapps.log

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sycho.rxapps.R

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.18
 */
class LogAdapter : RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private var logList: ArrayList<String>

    class ViewHolder : RecyclerView.ViewHolder, View.OnFocusChangeListener, View.OnClickListener, View.OnKeyListener {

        var logTextView: TextView

        constructor(itemView: View) : super(itemView) {
            logTextView = itemView.findViewById(R.id.tv_log)
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

    constructor(logItemList: ArrayList<String>) {
        logList = logItemList
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
            val item = logList[position]
            holder.logTextView.text = item
        }
    }


}