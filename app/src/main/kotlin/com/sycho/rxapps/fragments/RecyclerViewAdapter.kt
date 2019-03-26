package com.sycho.rxapps.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sycho.rxapps.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.widget_recycler_view_item.view.*

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.26
 */
class RecyclerViewAdapter(items: ArrayList<RecyclerItem>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var mItems: ArrayList<RecyclerItem> = items
    private var mPublishSubject: PublishSubject<RecyclerItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.widget_recycler_view_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mItems[position]
        holder.mImage.setImageDrawable(item.image)
        holder.mTitle.text = item.title
        // 생성된 클릭 이벤트 Observable을 구독한다
        // 전달할 객체가 PublishSubject인데 구독자가 없더라도
        // 실시간 처리 되어야 하는 클릭 이벤트에 특성에 맞게
        // Hot Observable을 사용해야 한다
        // Hot Observable은 생성과 동시에 이벤트를 발행한다.
        holder.getClickObserver(item).subscribe(mPublishSubject)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun getItemPublishSubject(): PublishSubject<RecyclerItem> {
        return mPublishSubject
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImage: ImageView = itemView.item_image
        val mTitle: TextView = itemView.item_title

        init {
            itemView.isFocusable = true
            itemView.isFocusableInTouchMode = true
        }

        fun getClickObserver(item: RecyclerItem): Observable<RecyclerItem> {
            // 클릭 이벤트를 분리된 Observable에 생성한다
            return Observable.create { e -> itemView.setOnClickListener { e.onNext(item) } }
        }
    }
}