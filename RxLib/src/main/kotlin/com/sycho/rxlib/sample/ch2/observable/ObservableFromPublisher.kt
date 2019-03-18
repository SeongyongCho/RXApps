package com.sycho.rxlib.sample.ch2.observable

import io.reactivex.Observable
import org.reactivestreams.Publisher

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.02.27
 */
class ObservableFromPublisher {
    fun basic() {
        val publisher = Publisher<String> {
            it.onNext("Hello Observable.fromPublisher()")
            it.onComplete()
        }

        val source = Observable.fromPublisher(publisher)
        source.subscribe { System.out.println("$it") }
    }
}