package com.sycho.rxlib.sample.ch2.observable

import io.reactivex.Observable
import java.util.concurrent.Callable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.02.27
 */
class ObservableFromCallable {
    fun basic() {
        val callable = Callable<String> {
            System.out.println("Start callable...")
            Thread.sleep(1000)
            "Hello Callable"
        }
        val source = Observable.fromCallable(callable)
        source.subscribe { System.out.println("$it") }
    }
}