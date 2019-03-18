package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.06
 */
class TimerExample {
    /**
     * timer()함수는 일정 시간 후에 한 개의 데이터만 발행하고 onComplete() 이벤트를 발생시키는 함수이다.
     * 역시 별도 계산 thread에서 동작한다.
     *
     * 이 예제는 500ms 후에 현재 시간을 한 번 발행하고 있다.
     */
    fun showTime() {
        Utils.exampleStart()
        val source = Observable.timer(500L, TimeUnit.MILLISECONDS)
            .map { SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date()) }
        source.subscribe { Log.it(it) }
        Utils.sleep(1000)
    }
}