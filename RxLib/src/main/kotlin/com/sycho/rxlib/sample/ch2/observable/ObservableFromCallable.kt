package com.sycho.rxlib.sample.ch2.observable

import io.reactivex.Observable
import java.util.concurrent.Callable

/**
 * Description
 *
 * @author Cho Seong-yong
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