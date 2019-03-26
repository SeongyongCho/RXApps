package com.sycho.rxapps.fragments

import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sycho.rxapps.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recycler_view.*

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.26
 */
class RecyclerViewFragment : BaseFragment() {

    private lateinit var mItems: ArrayList<RecyclerItem>
    private lateinit var mRecyclerViewAdapter: RecyclerViewAdapter

    private lateinit var compositeDisposable: CompositeDisposable

    override fun getLayoutId(): Int {
        return R.layout.fragment_recycler_view
    }

    override fun getLogViewId(): Int {
        return R.id.rv_log
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(mActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.addItemDecoration(RecyclerViewDecoration(mActivity))
        mItems = ArrayList()
        mRecyclerViewAdapter = RecyclerViewAdapter(mItems)
        recycler_view.adapter = mRecyclerViewAdapter
        val disposable = mRecyclerViewAdapter.getItemPublishSubject().subscribe { toast(it.title) }

        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(disposable)
    }

    override fun onStart() {
        super.onStart()

        val disposable = getItemObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                mItems.add(item)
                mRecyclerViewAdapter.notifyDataSetChanged()
            }

        compositeDisposable.add(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun getItemObservable(): Observable<RecyclerItem> {
        val pm = mActivity?.packageManager
        val i = Intent(Intent.ACTION_MAIN, null)
//        i.addCategory(Intent.CATEGORY_LAUNCHER)
        i.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
        val activities = pm?.queryIntentActivities(i, 0)

        return Observable.fromIterable(activities)
            .sorted(ResolveInfo.DisplayNameComparator(pm))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { item ->
                val image: Drawable = item.activityInfo.loadIcon(pm)
                val title: String = item.activityInfo.loadLabel(pm).toString()
                RecyclerItem(image, title)
            }
    }

    private fun toast(title: String) {
        Toast.makeText(mActivity, title, Toast.LENGTH_SHORT).show()
    }
}