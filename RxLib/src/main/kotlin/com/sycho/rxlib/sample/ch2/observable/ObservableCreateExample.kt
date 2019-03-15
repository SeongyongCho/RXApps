package com.sycho.rxlib.sample.ch2.observable

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.Consumer

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.02.26
 */
class ObservableCreateExample {
    fun basic() {
        // create()함수는 just()함수와 다르게 onNext() onError() onComplete() 알림을 직접 호출해 주어야 한다.
        // create()를 사용하려면...
        // Observable이 dispose 되었을 때 등록된 콜백을 모두 해제하지 않으면 잠재적으로 memory leak이 발생한다.
        // 구독자가 구독하는 동안에만 onNext와 onComplete를 호출해야 한다.
        // 에러가 발생하면 onError 이벤트로만 에러를 전달해야 한다.
        // back pressure를 직접 처리해야 한다
        val source = Observable.create{ emitter: ObservableEmitter<Int> ->
            emitter.onNext(100)
            emitter.onNext(200)
            emitter.onNext(300)
            emitter.onComplete()
        }
        source.subscribe{t: Int? -> System.out.println("$t") }
    }

    fun subscribeAnonymously() {
        val source = Observable.create{ emitter: ObservableEmitter<Int> ->
            emitter.onNext(100)
            emitter.onNext(200)
            emitter.onNext(300)
            emitter.onComplete()
        }
        source.subscribe(object : Consumer<Int> {
            override fun accept(t: Int?) {
                System.out.println("Result : $t")
            }
        })
    }
}