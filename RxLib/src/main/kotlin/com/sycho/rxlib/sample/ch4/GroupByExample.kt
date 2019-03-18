package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Shape
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.07
 */
class GroupByExample {
    /**
     * groupBy()함수는 keySelector 인자 기준으로 단인 Observable을 여러 개로 이루어진 Observable그룹으로 만든다.
     */
    fun marbleDiagram() {
        val objs = arrayOf("6", "4", "2-T", "2", "6-T", "4-T")
        val source = objs.toObservable().groupBy { Shape.getShape(it) } // 발행할 것을 타입 확인하여 리턴
        // GroupedObservable로 전달되므로 subscribe()를 또 호출해야한다.
        source.subscribe { obj ->
            obj.subscribe { v -> System.out.println("GROUP:${obj.key}\t Value:$v") }
        }
    }

    fun filterBallGroup() {
        val objs = arrayOf("6", "4", "2-T", "2", "6-T", "4-T")
        val source = objs.toObservable().groupBy { Shape.getShape(it) } // 발행할 것을 타입 확인하여 리턴
        // GroupedObservable로 전달되므로 subscribe()를 또 호출해야한다.
        // 그룹이 BALL 인것만 뽑는다.
        source.subscribe { obj -> obj.filter { obj.key == Shape.BALL }
            .subscribe { v -> System.out.println("GROUP:${obj.key}\t Value:$v") }
        }
    }
}