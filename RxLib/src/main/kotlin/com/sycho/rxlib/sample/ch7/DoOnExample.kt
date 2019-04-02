package com.sycho.rxlib.sample.ch7

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

/**
 * rxJava를 도입했을 때 로그를 넣어봤자 보이지 않는다.
 * Observable로 시작하는 upstream과 처리하는 downstream이 한 문장으로 이루어져있기 때문이며
 * 이를 위해서 doOnNext() doOnError() doOnComplete() 함수가 준비되어 있다.
 *
 * @author Cho Seong-yong
 * @since 2019.04.02
 */
class DoOnExample {

    /**
     * onNext()는 doOnNext() 등으로 각 이벤트에 맞는 디버깅용 함수가 제공된다.
     * doOnNext()의 경우에는 발행된 값을 함께 찍어볼 수도 있다.
     */
    fun basic() {
        val orgs = arrayOf("1", "3", "5")
        val source = orgs.toObservable()

        source.doOnNext { data -> Log.d("onNext()", data) }
            .doOnComplete { Log.d("onComplete()") }
            .doOnError { e -> Log.e("onError()", if (e != null) e.message!! else "NoErrorMessage") }
            .subscribe { Log.i(it) }
    }

    /**
     * 인위적으로 예외를 발생시켜서 doOnError() 에 어떻게 찍히는지 보자.
     */
    fun withError() {
        // 0으로 나누도록 하여 exception 발생 시킨다
        val divider = arrayOf(10, 5, 0)

        divider.toObservable()
            .map { div -> 1000 / div }
            .doOnNext { data -> Log.d("onNext()", data) }
            .doOnComplete { Log.d("onComplete()") }
            .doOnError { e -> Log.e("onError()", e?.message ?: "NoErrorMessage") }
            .subscribe { Log.i(it) }
    }

    /**
     * doOnEach() 함수를 사용하여 Notification<T> 객체를 전달받아서 이벤트별로 처리한다.
     */
    fun doOnEach() {
        val data = arrayOf("ONE", "TWO", "THREE")
        val source = data.toObservable()

        source.doOnEach { noti ->
            if (noti.isOnNext) Log.d("onNext()", noti?.value ?: "NoValue")
            if (noti.isOnComplete) Log.d("onComplete()")
            if (noti.isOnError) Log.e("onError()", noti?.error?.message ?: "NoErrorMessage")
        }
            .subscribe(System.out::println)
    }

    /**
     * doOnEach() 함수에 Observer를 전달할 수도 있다.
     */
    fun doOnEachObserver() {
        val orgs = arrayOf("1", "3", "5")
        val source = orgs.toObservable()

        source.doOnEach(object : Observer<String> {
            override fun onComplete() {
                Log.d("onComplete()")
            }

            override fun onSubscribe(d: Disposable) {
                // doOnEach() 에서는 onSubscribe() 함수는 호출되지 않는다.
            }

            override fun onNext(t: String) {
                Log.d("onNext()", t)
            }

            override fun onError(e: Throwable) {
                // rxJava를 사용하다보면 onError() 이벤트에 null이 들어오는 경우가 있다.
                Log.e("onError()", e.message ?: "NoErrorMessage")
            }
        })
            .subscribe { Log.i(it) }
    }

    /**
     * doOnSubscribe()와 doOnDispose() 함수를 알아본다.
     * doOnSubscribe() 함수는 Observable을 구독했을 때 작업을 할 수 있다.
     * doOnDispose() 함수는 Observable 구독을 해지했을 때 호출된다.
     */
    fun doOnSubscribeAndDispose() {
        val orgs = arrayOf("1", "3", "5", "2", "6")
        val source = orgs.toObservable()
            .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), BiFunction<String, Long, String> { a, _ -> a })
            .doOnSubscribe { d -> Log.d("onSubscribe()") }
            .doOnDispose { Log.d("onDispose()") }
        val d = source.subscribe { Log.i(it) }

        Utils.sleep(200)
        d.dispose()
        Utils.sleep(300)
    }

    /**
     * doOnSubscribe()와 doOnDispose() 함수를 한번에 처리할 수 있는 doOnLifeCycle() 함수도 있다.
     */
    fun doOnLifeCycle() {
        val orgs = arrayOf("1", "3", "5", "2", "6")
        val source = orgs.toObservable()
            .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), BiFunction<String, Long, String> { a, _ -> a })
//            .doOnLifecycle(Consumer<Disposable> { Log.d("onSubscribe()") }, Action { Log.d("onDispose()") })
            .doOnLifecycle({ Log.d("onSubscribe()") }, { Log.d("onDispose()") }) // 위 라인에서 어떤 인터페이스가 들어가는지 참고
        val d = source.subscribe { Log.i(it) }

        Utils.sleep(300)
        d.dispose()
        Utils.sleep(300)
    }

    /**
     * doOnTerminate() 함수는 onComplete() 또는 onError() 이벤트의 발생 직전에 실행된다.
     */
    fun doOnTerminate() {
        val orgs = arrayOf("1", "3", "5")
        val source = orgs.toObservable()

        source.doOnTerminate { Log.d("onTerminate()") }
            .doOnComplete { Log.d("onComplete()") }
            .doOnError { e -> Log.e("onError()", e?.message ?: "NoErrorMessage") }
            .subscribe { Log.i(it) }
    }
}