package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.07
 */
class SwitchMapExample {

    /**
     * switchMap()함수는 인터리빙 상황에서 순서를 보장하기 위해 진행 중이던 작업을 바로 중단해버린다.
     * 또한 여러개의 값이 발행됐을 경우 마지막 데이터의 처리는 보장한다.
     */
    fun marbleDiagram() {
        Utils.exampleStart()

        val balls = arrayOf("1", "3", "5")
        val source = Observable.interval(100L, TimeUnit.MILLISECONDS)
            .map { it.toInt() }
            .map { balls[it] }
            .take(balls.size.toLong())
            .switchMap { ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                .map { "$ball◇" }
                .take(2) }
        source.subscribe { Log.it(it) }
        Utils.sleep(2000)
    }

    /**
     * 중간과정을 보기위해 doOnNext()함수를 사용해본다.
     * 발행되어 조합까지는 300ms가 소요되는데 발행 3회는 300ms가 소요된다.
     * 그래서 1을 조합하기 전에 마지막꺼 발행 됐으므로 조합이 취소되고
     * 5가 정상적으로 두번 출력되는 것이다.
     */
    fun usingDoOnNext() {
        Utils.exampleStart()

        val balls = arrayOf("1", "3", "5")
        val source = Observable.interval(100L, TimeUnit.MILLISECONDS)
            .map { it.toInt() }
            .map { balls[it] }
            .take(balls.size.toLong())
            .doOnNext { Log.dt(it) }
            .switchMap { ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                .map { "$ball◇" }
                .take(2) }
        source.subscribe { Log.it(it) }
        Utils.sleep(2000)
    }
}