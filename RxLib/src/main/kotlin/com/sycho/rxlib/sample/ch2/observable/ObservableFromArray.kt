package com.sycho.rxlib.sample.ch2.observable

import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.02.26
 */
class ObservableFromArray {
    fun intArray() {
        val arr: Array<Int> = arrayOf(100, 200, 300)
        // fromArray()함수는 배열 데이터를 발행하는데 사용한다.
        // 그런데 kotlin array를 Observable.fromArray()에 넣을 수 없다. 왜냐하면 타입이 다르기 때문에.
        // fromArray()에서 요구하는 타입은 배열 포인터인데 kotlin array는 클래스 객체라서 맞지 않다.
        // 물론 방법은 있다. rxkotlin에서 제공하는 toObservable()을 사용하면 Observable.fromArray로 변환되어 사용이 가능하다.
        // 아래 함수 정의부를 보면 전달된 Array<T> 클래스 객체를 포인터로 Observable.fromArray() 에게 전달하도록 하고 있다.
        // fun <T : Any> Array<T>.toObservable(): Observable<T> = Observable.fromArray(*this)
        val source2 = arr.toObservable()
        source2.subscribe { System.out.println(it) }
    }
}