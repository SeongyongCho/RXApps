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

    // 리스너를 등록하지 않았을 경우 null 체크 가능하도록 null 허용한다.
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
                // null을 허용할경우 null 체크 되도록 ? 붙여야 한다.
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

    /**
     * assets에서 json 파일 읽어서 생성한 ArrayList<MainContentData>를 발행하는 Observable
     */
    private fun requestMainContents(jsonName: String): Observable<ArrayList<MainContentData>> {
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
            // json이 null이 아닐경우만 실행해야 하므로 let 사용함.
            json?.let {
                val jsonObject = JSONObject(it)
                val jsonArray = jsonObject.getJSONArray("main_card_list")
                val gson = Gson()
                if (jsonArray != null) {
                    // for 루프는 0부터 jsonArray의 카운트만큼 돌린다
                    for (i in 0 until jsonArray.length()) {
                        val record = jsonArray.getJSONObject(i)
                        // Gson.romJson()에서 필요한것은 Java class 이므로
                        // kotlin class인 MainContentData를 Java class로 매핑해준다.
                        val mainContentData = gson.fromJson<MainContentData>(record.toString(), MainContentData::class.java
                        )
                        mainContentDataList.add(mainContentData)
                    }
                    emitter.onNext(mainContentDataList)
                }
            }

            emitter.onComplete()
        }
    }

    /**
     * 전달된 데이터 리스트를 adapter에 집어넣어 [ListRow]를 생성하여 발행한다.
     */
    private fun responseMainContents(mainContentDataList: ArrayList<MainContentData>): Observable<ListRow> {
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

    /**
     * [requestMainContents] Observable과 [responseMainContents] Observable을 결합할것이다.
     * 이 때, 순차적으로 처리되도록 [Observable.concatMap]을 사용하였다.
     */
    private fun initMainContentsRow() {
        requestMainContents("main_card_list.json")
            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .concatMap { mainContentDataList ->
                // requestMainContents()에서 발행된 데이터 리스트를 인자로 전달한다.
                responseMainContents(mainContentDataList).observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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
