package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class DelayExample {
    /**
     * delay()함수는 전달받은 시간과 단위만큼 Observable의 데이터 발행을 지연시킨다.
     * computation thread에서 실행된다.
     */
    fun marbleDiagram() {
        Utils.exampleStart()
        val data = arrayOf("1", "7", "2", "3", "4")
        val source = data.toObservable().delay(100L, TimeUnit.MILLISECONDS)
        source.subscribe { Log.it(it) }
        Utils.sleep(1000)
    }
}