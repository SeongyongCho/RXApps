package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * interval() 함수는 일정 시간 간격으로 데이터 흐름을 생성한다.
 * 계산 동작은 별도 thread에서 동작한다.
 *
 * @author 파인원_조성용선임
 * @since 2019.03.06
 */
class IntervalExample {
    /**
     * period 인자에 넣은 값 만큼 초기 실행에 딜레이 들어간다.
     */
    fun printNumbers() {
        Utils.exampleStart()
        val source = Observable.interval(100L, TimeUnit.MILLISECONDS)
            .map { data -> (data + 1) * 100 }
            .take(5)
        source.subscribe { Log.it(it) }
        Utils.sleep(1000)
    }

    /**
     * 초기 실행 딜레이를 직접 지정한다. 보통 딜레이를 없애기 위해 0L 넣는다.
     */
    fun noInitialDelay() {
        Utils.exampleStart()
        val source = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
            .map { data -> (data + 1) * 100 }
            .take(5)
        source.subscribe { Log.it(it) }
        Utils.sleep(1000)
    }
}