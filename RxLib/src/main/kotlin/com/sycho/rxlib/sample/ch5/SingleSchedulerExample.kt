package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class SingleSchedulerExample {
    /**
     * Schedulers.single()은 rxJava 내부에서 단일 thread를 별도로 생성하여 구독 작업을 처리하며
     * 이 때 생성된 thread는 여러번 구독 요청이 와도 공통으로 사용한다.
     * single thread라서 활용도는 낮지만 저사양 디바이스에서라면 고려해볼만 하다.
     */
    fun basic() {
        val numbers = Observable.range(100, 5)
        val chars = Observable.range(0, 5)
            .map { Utils.numberToAlphabet(it.toLong()) }

        numbers.subscribeOn(Schedulers.single()).subscribe { Log.i(it) }
        chars.subscribeOn(Schedulers.single()).subscribe { Log.i(it) }

        Utils.sleep(500)
    }
}