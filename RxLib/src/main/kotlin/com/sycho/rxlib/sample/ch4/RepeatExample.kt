package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.OkHttpHelper
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.07
 */
class RepeatExample {
    /**
     * 단순 반복 실행하는 repeat() 함수이다.
     */
    fun marbleDiagram() {
        val balls = arrayOf("1", "3", "5")
        val source = balls.toObservable().repeat(3)
        source.doOnComplete { Log.d("onComplete") }
            .subscribe { Log.i(it) }
    }

    /**
     * timer()는 한번 발행하면 끝인데 왜 반복이 됐지? 그야 repeat() 함수를 넣었으니까
     * 그런데 thread가 별도로 생성되므로 로그 찍어보면 각각 다른 별도 thread에서 동작하게 된다.
     */
    fun heartbeatV1() {
        Utils.exampleStart()
        val serverUrl = "https://api.github.com/zen"

        // 2초 간격으로 서버에 ping 보내기
        Observable.timer(2, TimeUnit.SECONDS)
            .map { v -> serverUrl } // 발행되는게 0L 이므로 이거 그대로 쓰면 안 되니 serverUrl 전달하도록 한다
            .map { OkHttpHelper.get(it) }
            .repeat()
            .subscribe { res -> Log.it("Ping Result : $res") }
        Utils.sleep(10000)
    }

    /**
     * 반복 효과가 있는 interval() 함수를 사용하여 동일한 별도 thread에서 동작하도록 개선한다.
     */
    fun heartbeatV1_1() {
        Utils.exampleStart()
        val serverUrl = "https://api.github.com/zen"

        Observable.interval(0L, 2000, TimeUnit.MILLISECONDS)
            .map { serverUrl }
            .map { OkHttpHelper.get(it) }
            .take(3)
            .subscribe { res -> Log.it("Ping Result : $res") }

        Utils.sleep(10000)
    }
}