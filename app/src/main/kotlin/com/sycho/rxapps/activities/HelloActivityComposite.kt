package com.sycho.rxapps.activities

import android.app.Activity
import android.os.Bundle
import com.sycho.rxapps.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_hello.*

/**
 * CompositeDisposable 에 추가하여 한번에 해제하는 방식이다.
 *
 * @author Cho Seong-yong
 * @since 2019.04.02
 */
class HelloActivityComposite : Activity() {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        // subscribe() 함수를 사용할 때는 구독자가 아닌 소비자를 전달하도록 한다.
        // 어차피 현업에서는 onNext, onError, onComplete 를 처리해야될 경우가 많기 때문에
        // subscribeWith() 함수를 쓰도록 하자.
        val disposable = Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("Hello world!")
            emitter.onComplete()
        }).subscribeWith(object : DisposableObserver<String>() {
            override fun onComplete() {

            }

            override fun onNext(t: String) {
                text_view.text = t
            }

            override fun onError(e: Throwable) {

            }
        })
//            .subscribe(text_view::setText)
        mCompositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!mCompositeDisposable.isDisposed) {
            // dispose 이후에는 Disposable을 추가할 수 없다.
            mCompositeDisposable.dispose()
            // clear 이후 계속해서 Disposable을 추가할 수 있다.
//            mCompositeDisposable.clear()
        }
    }
}