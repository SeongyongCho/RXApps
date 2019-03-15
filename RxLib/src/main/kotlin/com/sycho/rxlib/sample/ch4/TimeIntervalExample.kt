package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Timed

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class TimeIntervalExample {
    /**
     * timeInterval()함수는 어떤값을 발해했을 때 이전 값을 발행한 이후 얼마나 시간이 흘렀는지를 알려준다.
     */
    fun marbleDiagram() {
        val data = arrayOf("1", "3", "7")
        Utils.exampleStart()
        val source: Observable<Timed<String>> = data.toObservable()
            .delay { item ->
                Utils.doSomething()
                Observable.just(item)
            }
            .timeInterval()
        source.subscribe { Log.it(it) }
        Utils.sleep(1000)
    }
}