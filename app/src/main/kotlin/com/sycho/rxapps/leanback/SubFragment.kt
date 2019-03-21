package com.sycho.rxapps.leanback

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.nio.charset.Charset

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.20
 */
class SubFragment : RowsSupportFragment() {

    private lateinit var mActivity: Activity

    private lateinit var mContentsRowAdapter: ArrayObjectAdapter

    private var mOnItemViewClickedListener: OnItemViewClickedListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onItemViewClickedListener = OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
            mOnItemViewClickedListener?.onItemClicked(itemViewHolder, item, rowViewHolder, row)
        }

        mContentsRowAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mContentsRowAdapter

        verticalGridView.setPadding(0, 0, 0, 0)
        initSubContentsRow()
    }

    private fun requestSubContents(jsonName: String) : Observable<ArrayList<SubContentData>> {
        return Observable.create { emitter ->
            val subContentDataList = ArrayList<SubContentData>()
            var json: String? = null
            try {
                val inputStream = mActivity.assets.open(jsonName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                val readSize = inputStream.read(buffer)
                inputStream.close()
                if (readSize > 0) {
                    json = String(buffer, Charset.forName("UTF-8"))
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }

            json?.let {
                val jsonObject = JSONObject(it)
                val jsonArray = jsonObject.getJSONArray("sub_card_list")
                val gson = Gson()
                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val record = jsonArray.getJSONObject(i)
                        val subContentData = gson.fromJson<SubContentData>(record.toString(), SubContentData::class.java)
                        subContentDataList.add(subContentData)
                    }
                    emitter.onNext(subContentDataList)
                }
            }

            emitter.onComplete()
        }
    }

    private fun responseSubContents(subContentDataList: ArrayList<SubContentData>) : Observable<ListRow> {
        return Observable.create {emitter ->
            val headerItem = HeaderItem("Fragments")
            val subContentsAdapter = ArrayObjectAdapter(SubContentCardPresenter())
            for (item in subContentDataList) {
                subContentsAdapter.add(item)
            }
            val listRow = ListRow(headerItem, subContentsAdapter)
            emitter.onNext(listRow)
            emitter.onComplete()
        }
    }

    private fun initSubContentsRow() {
        requestSubContents("sub_card_list.json")
            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .concatMap { subContentDataList ->
                responseSubContents(subContentDataList).observeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread())
            }
            .subscribe(object : Observer<ListRow> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ListRow) {
                    mContentsRowAdapter.add(t)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    fun setOnItemViewClickedListener(listener: OnItemViewClickedListener) {
        mOnItemViewClickedListener = listener
    }

}