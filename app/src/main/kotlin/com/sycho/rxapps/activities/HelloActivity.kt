package com.sycho.rxapps.activities

import android.app.Activity
import android.os.Bundle
import com.sycho.rxapps.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_hello.*

/**
 * 구독한것을 Disposable 인터페이스로 받아서 직접 해제한다.
 *
 * @author Cho Seong-yong
 * @since 2019.04.01
 */
class HelloActivity : Activity() {

    private var mDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val observer = object : DisposableObserver<String>() {
            override fun onComplete() {

            }

            override fun onNext(t: String) {
                text_view.text = t
            }

            override fun onError(e: Throwable) {

            }
        }

        // Observable에서 onComplete(), onError() 함수가 호출되면 내부에서 자동으로 unsubscribe() 함수를 호출한다.
        // Observable은 Context를 복사하여 유지하고 있는데
        // View를 참조하고 있는 구독자가 구독중에 Activity가 비정상 종료되어 Activity가 종료돼도
        // GC가 되지 않아 memory leak이 발생할 수 있다.
//        Observable.create(ObservableOnSubscribe<String> { emitter ->
//            emitter.onNext("Hello world!")
//            emitter.onComplete()
//        }).subscribe(observer)

        mDisposable = Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("Hello world!")
            emitter.onComplete()
        }).subscribeWith(observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        // onCreate()에서 구독했으니 Lifecycle에 맞게 onDestroy()에서 해제하였다.
        // 좀 더 간단하게 쓸 수 있는 방법은?
        mDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }
}