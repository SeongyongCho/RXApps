package com.sycho.rxlib.sample.ch2.subject

import io.reactivex.subjects.BehaviorSubject

/**
 * BehaviorSubject 클래스는 구독자가 구독하면 가장 최근 값 혹은 기본값을 넘겨주는 클래스이다.
 *
 * @author Cho Seong-yong
 * @since 2019.02.28
 */
class BehaviorSubjectExample {
    fun marbleDiagramBehaviorSubject() {
        val subject = BehaviorSubject.createDefault("6")
        // 기본값인 6을 출력한 다음 1, 3, 5까지 출력된다.
        subject.subscribe { data -> System.out.println("Subscriber #1 => $data") }
        subject.onNext("1")
        subject.onNext("3")
        // 최근값인 3을 출력하고 5를 출력한다.
        subject.subscribe { data -> System.out.println("Subscriber #2 => $data") }
        subject.onNext("5")
        subject.onComplete()
    }
}