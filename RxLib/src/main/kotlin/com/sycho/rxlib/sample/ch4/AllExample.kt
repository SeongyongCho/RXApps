package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Shape
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.11
 */
class AllExample {
    /**
     * all()함수는 조건에 맞을 때만 true를 발행하고 조건에 맞지 않는 데이터가 발행되면 바로 false를 발행한다.
     * 루프 돌가다 특정조건일 때 빠져나오고 싶을 때 사용하면 좋을 것 같다.
     */
    fun marbleDiagram() {
        val data = arrayOf("1", "2", "3", "4")

        val source: Single<Boolean> = data.toObservable()
            .map { Shape.getShape(it) }
            .all { Shape.BALL == it }
        source.subscribe { res -> Log.i(res) }
    }
}