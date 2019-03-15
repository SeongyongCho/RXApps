package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Shape
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.11
 */
class ZipExample {
    /**
     * zip()함수는 입력 Observable에서 데이터를 모두 새로 발행했을 때 그것을 결합해준다.
     * 여러 Observable에서 모두 데이터를 발행해야만 결합할 수 있고 그전까지는 기다린다.
     */
    fun marbleDiagram() {
        val shapes = arrayOf("BALL", "PENTAGON", "STAR")
        val coloredTriangles = arrayOf("2-T", "6-T", "4-T")
        val zipFun = BiFunction<String, String, String> { suffix, color -> color + suffix }

        val source = Observable.zip(
            shapes.toObservable().map { Shape.getSuffix(it) },
            coloredTriangles.toObservable().map { Shape.getColor(it) }, zipFun)
        source.subscribe { Log.i(it) }
    }

    /**
     * 3개 결합했고 이 때 함수는 Function3를 사용한다.
     */
    fun zipNumbers() {
        val source = Observable.zip(
            Observable.just(100, 200, 300),
            Observable.just(10, 20, 30),
            Observable.just(1, 2, 3),
            Function3<Int, Int, Int, Int> { t1, t2, t3 -> t1 + t2 + t3 })
        source.subscribe { Log.i(it) }
    }

    fun zipWithNumbers() {
        val source = Observable.zip(
            Observable.just(100, 200, 300),
            Observable.just(10, 20, 30),
            BiFunction<Int, Int, Int> { a, b -> a + b }) // zip으로 a + b 한 결과를 zipWith로 c를 결합한다.
            .zipWith(Observable.just(1, 2, 3), BiFunction<Int, Int, Int> { ab, c -> ab + c })
        source.subscribe { Log.i(it) }
    }

    /**
     * 데이터와 시간을 결합하는 zipInterval 기법이다.
     * 이렇게 결합함으로서 데이터 발행 시간을 조절할 수 있다.
     */
    fun zipInterval() {
        val source = Observable.zip(
            Observable.just("RED", "GREEN", "BLUE"),
            Observable.interval(200L, TimeUnit.MILLISECONDS),
            BiFunction<String, Long, String> { value, i -> value })
        Utils.exampleStart()
        source.subscribe { Log.it(it) }
        Utils.sleep(1000)
    }
}