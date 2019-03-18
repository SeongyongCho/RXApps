package com.sycho.rxlib.sample.ch2.subject

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject

/**
 * AsyncSubject 클래스는 Observable에서 발행한 마지막 데이터를 얻어 올 수 있다.
 * 완료되기 전 마지막 데이터만 생각하면 되고 그 이전 데이터는 무시된다.
 *
 * @author Cho Seong-yong
 * @since 2019.02.28
 */
class AsyncSubjectExample {
    fun marbleDiagramAsyncSubject() {
        val subject = AsyncSubject.create<String>()
        subject.subscribe {data -> System.out.println("Subscriber #1 => $data")}
        subject.onNext("1")
        subject.onNext("3")
        subject.subscribe {data -> System.out.println("Subscriber #2 => $data")}
        subject.onNext("5")
        subject.onComplete()
    }

    fun asSubscriber() {
        val arr = arrayOf(10.1f, 13.4f, 12.5f)
        val source = arr.toObservable()
        val subject = AsyncSubject.create<Float>()
        subject.subscribe {data -> System.out.println("Subscriber #1 => $data")}
        // Cold Observable을 Hot Observable로 바꾸는건데... 오히려 복잡해져서 거의 안 쓸거 같음
        // 일단 이게 가능한 이유는 Subject 클래스가 Observable을 상속하고 Observer 인터페이스를 구현하기 때문이다
        // io.reactivex.subjects.Subject
        // public abstract class Subject<T> extends Observable<T> implements Observer<T>
        source.subscribe(subject)
    }

    fun afterComplete() {
        val subject = AsyncSubject.create<Int>()
        subject.onNext(10)
        subject.onNext(11)
        // 마찬가지로 마지막에 발행된 12만 출력된다
        subject.subscribe { data -> System.out.println("Subscriber #1 => $data") }
        subject.onNext(12)
        subject.onComplete()
        subject.onNext(13)
        // onComplete() 이후로 발행된건 무시된다. 그러므로 #2, #3 모두 12만 출력한다.
        subject.subscribe { data -> System.out.println("Subscriber #2 => $data") }
        subject.subscribe { data -> System.out.println("Subscriber #3 => $data") }
    }
}