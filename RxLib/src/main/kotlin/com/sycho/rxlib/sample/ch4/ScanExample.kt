package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.07
 */
class ScanExample {
    /**
     * scan()함수는 실행할 때마다 입력값에 맞는 중간 결과 및 최종 결과를 발행한다.
     */
    fun marbleDiagram() {
        val balls = arrayOf("1", "3", "5")
        val source = balls.toObservable()
            .scan { ball1: String, ball2: String -> "$ball2($ball1)" }
        // reduce() 함수를 사용했다면 최종결과만 출력되지만 scan() 함수를 썼으므로 중간 결과도 함께 출력된다.
        // scan() 함수의 리턴 타입을 보면 Observable<T> 이다.
        // 값이 입력될 때마다 발행하기 때문에 발행이 안 될 수가 없기 때문이다.
        source.subscribe { Log.i(it) }
    }
}