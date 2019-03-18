package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.12
 */
class ComputationSchedulerExample {
    /**
     * IO 작업이 없는 계산작업일 경우 Schedulers.computation()을 사용하도록 하자.
     * CPU 개수만큼 thread를 생성한다.
     */
    fun basic() {
        val orgs = arrayOf("1", "3", "5")
        val source = orgs.toObservable().zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), BiFunction<String, Long, String> { a, _ -> a })

        // 구독 #1
        source.map { item -> "<<$item>>" }
            .subscribeOn(Schedulers.computation())
            .subscribe { Log.i(it) }

        // 구독 #2
        source.map { item -> "##$item##" }
            .subscribeOn(Schedulers.computation())
            .subscribe { Log.i(it) }

        Utils.sleep(1000)
    }
}