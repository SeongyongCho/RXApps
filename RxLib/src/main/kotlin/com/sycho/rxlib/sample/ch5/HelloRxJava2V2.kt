package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import io.reactivex.Observable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class HelloRxJava2V2 {
    fun emit() {
        Observable.just("Hello", "RxJava 2!!").subscribe { Log.i(it) }
    }
}