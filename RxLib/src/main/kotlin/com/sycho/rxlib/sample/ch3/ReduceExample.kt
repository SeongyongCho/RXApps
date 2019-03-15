package com.sycho.rxlib.sample.ch3

import io.reactivex.Maybe
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.06
 */
class ReduceExample {
    /**
     * 발행한 데이터를 모두 사용하여 어떤 최종 결과 데이터를 합성하는 reduce() 함수를 사용해 본다.
     */
    fun marbleDiagram() {
        val balls = arrayOf("1", "3", "5")
        // reduce() 함수를 호출하여 인자로 넘긴 결과가 없는채로 완료 될 수 있어 Observable이 아닌 Maybe 이다.
        // reduce() 함수에 들어가는 인자는 BiFunction이다.
        // 이 예제에서는 String 두개를 받아서 합성하여 String을 리턴할 것이므로 타입은 BiFunction<String, String, String> 이다.
//        val mergeBalls = BiFunction { ball1: String, ball2: String -> "$ball2($ball1)" }
        val source: Maybe<String> = balls.toObservable().reduce { ball1: String, ball2: String -> "$ball2($ball1)" }
        source.subscribe { System.out.println("$it") }
    }
}