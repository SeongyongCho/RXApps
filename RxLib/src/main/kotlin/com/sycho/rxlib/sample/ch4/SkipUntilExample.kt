package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.11
 */
class SkipUntilExample {
    /**
     * skipUntil()함수는 다른 Observable이 발행하기 전까지 값을 건너뛴다.
     */
    fun marbleDiagram() {
        val data = arrayOf("1", "2", "3", "4", "5", "6")

        // 100ms마다 발행하는 Observable #1이 총 500ms까지 돌아가면 (4 까지 출력)
        // 500ms마다 발행하는 Observable #2의 발행으로 인해 종료된다.
        val source = data.toObservable()
            .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), BiFunction<String, Long, String> { v, _ -> v})
            .skipUntil(Observable.timer(500L, TimeUnit.MILLISECONDS))

        source.subscribe { Log.i(it) }
        Utils.sleep(1000)
    }
}