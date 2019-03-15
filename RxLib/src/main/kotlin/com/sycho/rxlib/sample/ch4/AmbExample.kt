package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.11
 */
class AmbExample {
    /**
     * amb()함수는 여러개의 Observable 중에서 가장먼저 데이터를 발행한 것 이외에는 모두 무시된다.
     */
    fun marbleDiagram() {
        val data1 = arrayOf("1", "3", "5")
        val data2 = arrayOf("2-R", "4-R")

        val sources = arrayListOf(
            data1.toObservable().doOnComplete { Log.d("Observable #1 : onComplete()") },
            data2.toObservable().delay(100L, TimeUnit.MILLISECONDS)
                .doOnComplete { Log.d("Observable #2 : onComplete()") }
        )

        Observable.amb(sources)
            .doOnComplete { Log.d("Result : onComplete()") }
            .subscribe { Log.i(it) }
        Utils.sleep(1000)
    }
}