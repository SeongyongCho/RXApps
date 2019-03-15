package com.sycho.rxlib.sample.ch2.subject

import io.reactivex.subjects.PublishSubject

/**
 * PublishSubject 클래스는 구독자가 subscribe() 함수를 호출하면 값을 발행하기 시작한다.
 *
 * @author 파인원_조성용선임
 * @since 2019.02.28
 */
class PublishSubjectExample {
    fun marbleDiagramPublishSubjectExample() {
        val publishSubject = PublishSubject.create<String>()
        // 구독 시작한 다음인 1, 3, 5를 출력한다.
        publishSubject.subscribe { data -> System.out.println("Subscriber #1 => $data") }
        publishSubject.onNext("1")
        publishSubject.onNext("3")
        // 구독 시작한 다음인 5를 출력한다.
        publishSubject.subscribe { data -> System.out.println("Subscriber #2 => $data")}
        publishSubject.onNext("5")
    }
}