package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import io.reactivex.Observable

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.06
 */
class RangeExample {
    /**
     * range() 함수는 주어진 값(n)부터 m개의 Integer 객체를 발행한다.
     * 스케줄러를 지원하지 않으므로 현재 thread에서 동작한다.
     * 반복문을 대체하기 위해 많이 사용된다.
     */
    fun forLoop() {
        // 1부터 시작해서 10번 발행하는데 여기서 짝수인 것만 필터링 한다.
        val source = Observable.range(1, 10).filter { number -> number % 2 == 0 }
        source.subscribe { Log.i(it) }
    }
}