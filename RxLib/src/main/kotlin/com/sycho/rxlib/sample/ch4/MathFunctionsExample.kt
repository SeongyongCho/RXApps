package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import hu.akarnokd.rxjava2.math.MathFlowable
import io.reactivex.Flowable
import io.reactivex.rxkotlin.toFlowable
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class MathFunctionsExample {
    /**
     * 수학 함수들
     */
    fun marbleDiagram() {
        val data = arrayOf(1, 2, 3, 4)

        // 1. count
        // Observable에서 발행된 데이터의 개수
        val source = data.toObservable().count()
        source.subscribe { count -> Log.i("count is $count") }

        // 2. max() min()
        // Observable에서 발행된 데이터에서 최대값, 최소값
        data.toFlowable().to { MathFlowable.max(it) }.subscribe { max -> Log.i("max is $max") }
        data.toFlowable().to { MathFlowable.min(it) }.subscribe { min -> Log.i("min is $min") }

        // 3. sum() average()
        // Observable에서 발행된 데이터들의 합, 평균
        val flowable = data.toFlowable().to { MathFlowable.sumInt(it) }
        flowable.subscribe { sum -> Log.i("sum is $sum") }
        val flowable2 = data.toFlowable().to { MathFlowable.averageDouble(it) }
        flowable2.subscribe { avr -> Log.i("average is $avr") }
    }
}