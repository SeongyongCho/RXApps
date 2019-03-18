package com.sycho.rxlib.sample.ch2

import io.reactivex.Observable

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.02.26
 */
class FirstExample {
    /**
     * Observable 선언 방식 중 가장 단순한 just.
     * 래퍼런스 타입은 물론 사용자 정의 클래스의 객체도 인자로 넣을 수 있다.
     * 인자는 최대 10개까지 넣을 수 있지만 타입은 모두 같아야 한다.
     * String 을 2가지 발행하고 이를 구독하여 출력한다.
     * 당연히 subscribe() 함수를 호출해야만 발행이 된다는 것 상식으로 받아들이도록 하자.
     */
    fun emit1_2() {
        Observable.just("Hello", "Rxjava 2!!")
            .subscribe(System.out::println)
    }

    /**
     * Int를 6가지 발행하고 이를 구독하여 출력한다.
     */
    fun emit2_1() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .subscribe(System.out::println)
    }
}