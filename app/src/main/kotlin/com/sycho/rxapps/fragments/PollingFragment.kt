package com.sycho.rxapps.fragments

import android.os.Bundle
import com.sycho.rxapps.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_polling.*
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.26
 */
class PollingFragment : BaseFragment() {

    companion object {
        private const val INITIAL_DELAY: Long = 0L
        private const val PERIOD: Long = 3L
    }

    private var disposables: CompositeDisposable? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_polling
    }

    override fun getLogViewId(): Int? {
        return R.id.rv_polling_log
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        disposables = CompositeDisposable()

        btn_polling.setOnClickListener {
            startPollingV1()
        }

        btn_polling2.setOnClickListener {
            startPollingV2()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables?.clear()
    }

    /**
     * 일정시간 간격으로 작업하기위한 Timer 클래스를 interval() 함수로 대체해본다.
     */
    private fun startPollingV1() {
        // interval이 뭐였는가?
        // 주어진 시간 간격으로 0L 부터 Long 값을 발행하는 함수이다.
        // 그러면 이 Long 값을 flatMap() 함수를 사용하여 String으로 바꾸자
        val ob = Observable.interval(INITIAL_DELAY, PERIOD, TimeUnit.SECONDS)
            .flatMap { o ->
                Observable.just("polling #1 $o")
            }

        // 구독하는곳에서는 로그 리스트에 추가해주는걸로 끝이다.
        val disposable = ob.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::log)

        disposables?.add(disposable)
    }

    private fun startPollingV2() {
        // 다른 방법으로 구현한 예이다.
        // Observable 생성은 just로 한번만 하고
        // 이 Observable을 repeatWhen()으로 계속 발행하게 한다.
        // delay를 3초로 줬으므로 3초마다 Observable을 계속해서 발행하게 된다
        val ob2 = Observable.just("polling #2")
            .repeatWhen { o -> o.delay(PERIOD, TimeUnit.SECONDS) }

        val disposable = ob2.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::log)

        disposables?.add(disposable)
    }
}