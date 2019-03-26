package com.sycho.rxapps.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sycho.rxapps.log.LogAdapter

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.25
 */
abstract class BaseFragment : Fragment() {

    protected var mActivity: Activity? = null
    protected var mLogView: RecyclerView? = null

    private var mLogAdapter: LogAdapter? = null
    private lateinit var mLogs: ArrayList<String>

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun getLogViewId(): Int?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        val logViewId = getLogViewId()
        logViewId?.let {
            mLogView = view.findViewById(logViewId)
            setupLogger()
        }
        return view
    }

    protected fun log(log: String?) {
        log?.let {
            mLogs.add(it)
            mLogAdapter?.notifyItemRangeChanged(0, mLogs.size)
        }
    }

    private fun setupLogger() {
        mLogs = ArrayList()
        mLogAdapter = LogAdapter(mLogs)
        val linearLayoutManager = LinearLayoutManager(mActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        mLogView?.layoutManager = linearLayoutManager
        mLogView?.adapter = mLogAdapter
    }
}