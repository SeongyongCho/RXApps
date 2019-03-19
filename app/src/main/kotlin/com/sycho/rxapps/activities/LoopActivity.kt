package com.sycho.rxapps.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sycho.rxapps.R
import com.sycho.rxapps.log.LogAdapter
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.activity_loop.*

/**
 * java와 rxJava 버전별로 제어 흐름 구현 비교.
 * 모든 버튼의 동작은 Iterable 객체에서 apple을 찾으면 그것을 출력하고 그렇지 않으면 Not found를 출력하도록 한다.
 *
 * @author Cho Seong-yong
 * @since 2019.03.18
 */
class LoopActivity : Activity() {

    companion object {
        private val TAG = LoopActivity::class.java.simpleName
    }

    private lateinit var mLogAdapter: LogAdapter
    private lateinit var mLogs: ArrayList<String>

    private val samples = arrayListOf("banana", "orange", "apple", "apple mango", "melon", "watermelon")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loop)

        setupLogger()
        setSampleTitle()
        btn_loop.setOnClickListener(mOnClickListener)
        btn_loop2.setOnClickListener(mOnClickListener)
        btn_loop3.setOnClickListener(mOnClickListener)
    }

    private fun setSampleTitle() {
        // kotlin에서는 res id 로 바로 접근 가능하니 butterknife 등은 굳이 필요 없음.
        tv_title.append(samples.toObservable().reduce { r, s -> (r + "\n") + s}.blockingGet())
    }

    private val mOnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.btn_loop -> {
                log(">>>>> get an apple :: java")
                // for 루프로 전체를 순회하여 apple로 시작하는거 찾아서 출력한다
                for (s in samples) {
                    if (s.contains("apple")) {
                        log(s)
                        break
                    }
                }
            }
            R.id.btn_loop2 -> {
                log(">>>>> get an apple :: rxJava 1.x")
                // 발행되는거에서 apple로 시작하는거 필터링한다
                rx.Observable.from(samples).filter { s -> s.contains("apple") }
                    .firstOrDefault("Not found")
                    .subscribe { log(it.toString()) }
            }
            R.id.btn_loop3 -> {
                log(">>>>> get an apple :: rxJava 2.x")
                // rxJava 1.x와 다른점은 타입을 식별할 수 있는 fromXXX를 사용한다는 것이다
                Observable.fromIterable(samples)
//                    .skipWhile { s -> !s.contains("apple") }
                    .filter { s -> s.contains("apple") }
                    .first("Not found").subscribe(Consumer { log(it) })
            }
        }
    }

    private fun log(log: String) {
        mLogs.add(log)
        mLogAdapter.notifyItemRangeChanged(0, mLogs.size)
    }

    private fun setupLogger() {
        mLogs = ArrayList()
        mLogAdapter = LogAdapter(mLogs)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rv_log.layoutManager = linearLayoutManager
        rv_log.adapter = mLogAdapter
    }
}
