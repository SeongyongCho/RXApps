package com.sycho.rxlib.sample.ch2.observable

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.02.26
 */
class ObservableNotifications {
    fun emit() {
        val source: Observable<String> = Observable.just("RED", "GREEN", "BLUE")
        // subscribe()
        // 인자가 하나도 없는 것은 onNext() onComplete() 이벤트가 무시되고 onError() 이벤트가 발생했을 때만 OnErrorNotImplementedException이 throw된다. 굳이 쓴다면 디버깅 용도가 될 것이다.
        // subscribe(Consumer<? super T> onNext)
        // onNext() 이벤트를 처리한다. 이 역시 onError() 이벤트가 발생하면 OnErrorNotImplementedException이 throw 된다.
        // subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError)
        // onNext() onError() 이벤트를 처리한다.
        // subscribe(consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete)
        // onNext() onError() onComplete() 이벤트롤 모두 처리한다.
        // 여기서 subscribe() 함수의 인자는 순서대로 onNext() onError() onComplete() 이벤트를 처리하도록 하였다.
        // onNext()에서는 발행된 값을 출력, onError()에서는 에러 메시지 출력, onComplete()에서는 텍스트 출력 하였다.
        val d: Disposable = source.subscribe(
            {t -> System.out.println("onNext() : value : $t")},
            {err -> System.err.println("onError() : err : " + err.message)},
            {System.out.println("onComplete()")})

        // subscribe() 리턴으로 Disposable을 받았다.
        // Disposable은 rxJava1의 Subscription(구독) 객체이다.
        // dispose() isDisposed() 두가지 메소드가 있는데
        // dispose()는 Observable에게 더 이상 데이터를 발행하지 않도록 구독을 끊는 메소드이다.
        // 그런데 Observable에서 onComplete 했다면 자동으로 구독을 끊기 때문에 굳이 호출하지는 않는다.
        // 그래서 아래 문장은 true가 찍힐 것이다.
        System.out.println("isDisposed() : " + d.isDisposed)
    }
}