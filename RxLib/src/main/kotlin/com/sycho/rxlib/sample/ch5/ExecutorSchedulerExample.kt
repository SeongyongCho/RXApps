package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.12
 */
class ExecutorSchedulerExample {
    /**
     * java의 Executor를 변환하여 Scheduler를 생성한다.
     * Executor와 Scheduler는 동작방식이 다르므로 꼭 기존의 Executor를 재사용할 때만 한정하여 사용하도록 한다.
     * 걍 필요 없는 걸로...
     */
    fun basic() {
        val THREAD_NUM = 10

        val data = arrayOf("1", "3", "5")
        val source = data.toObservable()
        val executor = Executors.newFixedThreadPool(THREAD_NUM)

        source.subscribeOn(Schedulers.from(executor)).subscribe { Log.i(it) }
        source.subscribeOn(Schedulers.from(executor)).subscribe { Log.i(it) }

        Utils.sleep(500)
    }
}