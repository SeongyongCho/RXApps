package com.sycho.rxapps.activities

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.sycho.rxapps.R
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_hello.*

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.26
 */
class AsyncTaskActivity : Activity() {

    companion object {
        private val TAG = AsyncTaskActivity::class.java.simpleName
    }

    private lateinit var mAndroidTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        mAndroidTextView = text_view

        initAndroidAsync()
        initRxAsync()
    }

    private fun initAndroidAsync() {
        MyAsyncTask().execute("Hello", "async", "world")
    }

    /**
     * 흔히 사용하던 AsyncTask
     * 오직 한 번만 실행되어 재사용이 불가하다.
     * 액티비티 종료를 명시해야만 종료되므로 메모리 누수가 발생한다.
     * 항상 UI 스레드 위에서 불러와야 한다.
     */
    inner class MyAsyncTask : AsyncTask<String, Unit, String>() {
        override fun doInBackground(vararg params: String?): String {
            val word = StringBuilder()
            for (s in params) {
                word.append(s).append(" ")
            }
            return word.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            mAndroidTextView.text = result
        }
    }

    private fun initRxAsync() {
        Observable.just("Hello", "rx", "world")
            .reduce { x, y -> "$x $y" } // StringBuilder로 공백 추가하던것
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    /**
     * null이 올 수 있는 reduce()함수로 합성 했으니 이에 맞게 Observable이 아닌 Maybe다
     */
    private fun getObserver(): MaybeObserver<String> {
        return object : MaybeObserver<String> {
            override fun onSuccess(t: String) {
                text_view_2.text = t
            }

            override fun onComplete() {
                Log.i(TAG, "done")
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, e.message)
            }
        }
    }
}