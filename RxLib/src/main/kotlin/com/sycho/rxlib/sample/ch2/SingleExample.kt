package com.sycho.rxlib.sample.ch2

import com.sycho.rxlib.sample.common.Order
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.02.27
 */
class SingleExample {
    fun just() {
        // Single은 오직 1개의 데이터만 발행할 수 있도록 제한된 특수 Observable이다.
        val source = Single.just("Hello Single")
        source.subscribe {
            success -> System.out.println("$success")
        }
    }

    /**
     * Observable 클래스에서 Single 클래스 활용
     */
    fun fromObservable() {
        // 기존 Observable에서 Single 객체로 변환
        val source = Observable.just("Hello Single")
        Single.fromObservable(source).subscribe { success -> System.out.println("$success") }

        // single() 함수를 호출해 Single 객체 생성
        Observable.just("Hello Single").single("default item").subscribe { success -> System.out.println("$success") }

        // first() 함수를 호출해 Single 객체 생성
        val colors = arrayOf("Red", "Blue", "Gold")
        Observable.fromArray("Red", "Blue", "Gold").first("default value").subscribe { success -> System.out.println("$success") }

        // empty Observable에서 Single 객체 생성
        Observable.empty<String>().single("default value").subscribe { success -> System.out.println("$success") }

        // take() 함수에서 Single 객체 생성
        Observable.just(Order("ORD-1", "", 0), Order("ORD-2", "", 0)).take(1).single(Order("default order", "", 0)).subscribe { success -> System.out.println("$success") }
    }
}