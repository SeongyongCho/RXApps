package com.sycho.rxlib.sample.ch2.subject

import io.reactivex.subjects.ReplaySubject

/**
 * ReplaySubject는 구독자가 새로 생기면 항상 데이터의 처음부터 끝까지 발행한다.
 * 모든 데이터를 저장해두기 때문에 이 과정에서 메모리 누수 조심해야 한다.
 *
 * @author Cho Seong-yong
 * @since 2019.02.28
 */
class ReplaySubjectExample {
    fun marbleDiagramReplaySubject() {
        // capacity 지정해보았다. 당연히 지정 안할 수도 있다.
        val subject = ReplaySubject.create<String>(10)
        // 각 구독자는 시점에 차이가 있을 뿐 모든 데이터를 출력한다.
        subject.subscribe { data -> System.out.println("Subscriber #1 => $data") }
        subject.onNext("1")
        subject.onNext("3")
        // #1은 1, 3 출력한 다음
        subject.subscribe { data -> System.out.println("Subscriber #2 => $data") }
        // #2가 구독 시작 했으므로 1, 3 먼저 출력한다
        subject.onNext("5")
        // 먼저 구독한건 #1이므로 #1이 먼저 5을 출력하고 그 다음 #2가 5을 출력한다.
        subject.onComplete()
    }
}