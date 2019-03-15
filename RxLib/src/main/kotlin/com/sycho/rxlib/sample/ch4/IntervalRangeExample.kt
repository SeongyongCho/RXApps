package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.06
 */
class IntervalRangeExample {
    /**
     * interval()과 range()를 혼합해놓은 intervalRange()함수이다.
     * 일정한 시간 간격으로 시작 숫자(n)부터 m개 까지 생성하고 onComplete 이벤트를 발생시킨다.
     * 별도의 계산 스케줄러에서 동작하고 발행하는 데이터 타입은 Long이다.
     */
    fun printNumbers() {
        val source = Observable.intervalRange(1, 10, 0L, 100L, TimeUnit.MILLISECONDS)
        source.subscribe { Log.i(it) }
        Utils.sleep(1000)
    }

    /**
     * interval()함수로 intervalRange()함수와 동일하게 동작하도록 조합해보자
     * 동일한 동작을 하는데 어떤방법이 좋을까
     * 일반 텍스트 에디터에서 보면 인자의 의미를 모르니 가독성이 떨어질 것이니
     * 조합하는 것이 좀 더 직관적이긴 할 것이다.
     */
    fun makeWithInterval() {
        val source = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
            .map { it + 1 } // 0부터 발행되니 +1
            .take(5) // interval()은 계속 발행하므로 5개만 하도록 제한을 둔다
        source.subscribe { Log.i(it) }
        Utils.sleep(1000)
    }
}