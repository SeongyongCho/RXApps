package com.sycho.rxlib.sample.ch3

import com.sycho.rxlib.sample.common.Log
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.05
 */
class FlatMapExample {
    /**
     * flatMap() 함수는 결과가 Observable로 나온다.
     * 그러므로 한개는 물론 여러개의 데이터를 발행할 수 있다.
     */
    fun marbleDiagram() {
        val balls = arrayOf("1", "3", "5")
        // 문자열 하나는 입력 받으면 마름모 추가해서 두번 발행한다.
//        val getDoubleDiamonds = Function<String, Observable<String>> {
//            ball -> Observable.just("$ball◇", "$ball◇")
//        }
//        val source = balls.toObservable().flatMap(getDoubleDiamonds)
        val source = balls.toObservable().flatMap { ball -> Observable.just("$ball◇", "$ball◇") }
        source.subscribe { Log.i("$it") }
    }
}