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
 * Main gateway contents fragment
 *
 * @author Cho Seong-yong
 * @since 2019.03.19
 */
class MainFragment : RowsSupportFragment() {

    private lateinit var mActivity: Activity

    private lateinit var mContentsRowAdapter: ArrayObjectAdapter

    private var mOnItemViewClickedListener: OnItemViewClickedListener? = null

    private var mOnItemViewSelectedListener: OnItemViewSelectedListener? = null

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
        onItemViewSelectedListener = OnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row ->
            mOnItemViewSelectedListener?.onItemSelected(itemViewHolder, item, rowViewHolder, row)
        }

        mContentsRowAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mContentsRowAdapter

        initMainContentsRow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun requestMainContents(jsonName: String) : Observable<ArrayList<MainContentData>> {
        return Observable.create { emitter ->
            val mainContentDataList = ArrayList<MainContentData>()
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
            json.let {
                val jsonObject = JSONObject(it)
                val jsonArray = jsonObject.getJSONArray("main_card_list")
                val gson = Gson()
                if (jsonArray != null) {

                    for (i in 0 until jsonArray.length()) {
                        val record = jsonArray.getJSONObject(i)
                        val mainContentData = gson.fromJson<MainContentData>(record.toString(), MainContentData::class.java)
                        mainContentDataList.add(mainContentData)
                    }
                    emitter.onNext(mainContentDataList)
                }
            }

            emitter.onComplete()
        }
    }

    private fun responseMainContents(mainContentDataList: ArrayList<MainContentData>) : Observable<ListRow> {
        return Observable.create { emitter ->
            val headerItem = HeaderItem("Activities")
            val mainContentsAdapter = ArrayObjectAdapter(MainContentCardPresenter())
            for (item in mainContentDataList) {
                mainContentsAdapter.add(item)
            }
            val listRow = ListRow(headerItem, mainContentsAdapter)
            emitter.onNext(listRow)
            emitter.onComplete()
        }
    }

    private fun initMainContentsRow() {
        requestMainContents("main_card_list.json")
            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                responseMainContents(it).observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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

    fun setOnItemViewSelectedListener(listener: OnItemViewSelectedListener) {
        mOnItemViewSelectedListener = listener
    }
}
