package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Shape
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.zipWith
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.11
 */
class CombineLatestExample {

    /**
     * combineLatest() 함수는 2개 이상의 Observable을 기반으로 Observable 각각의 값이 변경되었을 때 갱신해준다.
     * 마지막 인자 combiner가 각 Observable을 결합하여 어떤 결과를 만들어주는 역할을 한다.
     * 모든 Observable이 발행되어야 결과가 나오며 이후에는 어느쪽이든 갱신되면 반영되어 최신 결과가 나온다.
     */
    fun marbleDiagram() {
        val data1 = arrayOf("6", "7", "4", "2")
        val data2 = arrayOf("DIAMOND", "STAR", "PENTAGON")
        val source = Observable.combineLatest(
            data1.toObservable().zipWith( // 시간과 병합하여 100ms마다 발행
                Observable.interval(100L, TimeUnit.MILLISECONDS),
                BiFunction<String, Long, String> { shape, _ -> Shape.getColor(shape) }),  // Observable #1
            data2.toObservable().zipWith(
                Observable.interval(150L, 200L, TimeUnit.MILLISECONDS),
                BiFunction<String, Long, String> { shape, _ -> Shape.getSuffix(shape) }), // Observable #2
            BiFunction<String, String, String> { v1, v2 -> v1 + v2 })                           // combiner
        source.subscribe { Log.i(it) }
        Utils.sleep(1000)
    }
}