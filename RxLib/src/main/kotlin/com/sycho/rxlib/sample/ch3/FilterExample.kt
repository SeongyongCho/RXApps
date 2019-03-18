package com.sycho.rxlib.sample.ch3

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.06
 */
class FilterExample {
    /**
     * 여러 데이터들이 발행되는데 딱 원하는 데이터만 받도록 filter() 함수를 사용해보자.
     */
    fun marbleDiagram() {
        val objs = arrayOf("1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON")
        // Observable 만들었고 여기서 끝에가 CIRCLE인 것만 뽑는다.
        // filter() 함수의 요구사항은 boolean을 리턴하는 함수형 인터페이스인 Predicate이다.
        // 람다 표현식으로 문장을 집어 넣었는데 Function인지 Predicate인지 구분은 컴파일러에서 알아서 해준다.
//        val filterCircle = Predicate<String> { obj -> obj.endsWith("CIRCLE") }
        val source = objs.toObservable().filter { obj -> obj.endsWith("CIRCLE") }
        source.subscribe { System.out.println("$it") }
    }

    /**
     * filter() 함수에 간단한 수식을 적용한 예이다.
     * 주어진 숫자들 중에서 짝수만 필터링 하고 있다.
     */
    fun evenNumbers() {
        val data = arrayOf(100, 34, 27, 99, 50)
        val source = data.toObservable().filter { number -> number % 2 == 0 }
        source.subscribe { System.out.println("$it") }
    }

    /**
     * filter()와 비슷한 함수들이 어떤게 있고 어떻게 동작하는지 알아본다.
     *
     * first(default) : Observable의 첫 번째 값을 필터함. 만약 값 없이 완료되면 대신 기본값을 리턴
     * last(default) : Observable의 마지막 값을 필터함. 만약 값 없이 완료되면 대신 기본값을 리턴
     * take(N) : 최초 N개 값만 가져온다.
     * takeLast(N) : 마지막 N개 값만 가져온다.
     * skip(N) : 최초 N개 값을 건너뛴다.
     * skipLast(N) : 마지막 N개 값을 건너뛴다.
     */
    fun otherFilters() {
        val numbers = arrayOf(100, 200, 300, 400, 500)
        var single: Single<Int>
        var source: Observable<Int>

        // first(default)
        single = numbers.toObservable().first(-1)
        single.subscribe { data -> System.out.println("first(-1) value = $data") }

        // last(default)
        single = numbers.toObservable().last(999)
        single.subscribe { data -> System.out.println("last(999) value = $data") }

        // take(N)
        source = numbers.toObservable().take(3)
        source.subscribe { data -> System.out.println("take(3) value = $data") }

        // takeLast(N)
        source = numbers.toObservable().takeLast(3)
        source.subscribe { data -> System.out.println("takeLast(3) value = $data") }

        // skip(N)
        source = numbers.toObservable().skip(2)
        source.subscribe { data -> System.out.println("skip(2) value = $data") }

        // skipLast(N)
        source = numbers.toObservable().skipLast(2)
        source.subscribe { data -> System.out.println("skipLast(2) value = $data") }
    }
}