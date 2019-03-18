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
 * @author Cho Seong-yong
 * @since 2019.03.11
 */
class TakeUntilExample {
    /**
     * takeUntil()함수는 take()함수에 조건을 설정한다.
     * take()함수처럼 일정 개수만 값을 발행하되 완료 기준을 다른 Observable에서 값을 발행하는지로 판단한다.
     */
    fun marbleDiagram() {
        val data = arrayOf("1", "2", "3", "4", "5", "6")

        // 100ms마다 발행하는 Observable #1이 총 500ms까지 돌아가면 (4 까지 출력)
        // 500ms마다 발행하는 Observable #2의 발행으로 인해 종료된다.
        val source = data.toObservable()
            .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), BiFunction<String, Long, String> { v, _ -> v})
            .takeUntil(Observable.timer(500L, TimeUnit.MILLISECONDS))

        source.subscribe { Log.i(it) }
        Utils.sleep(1000)
    }
}