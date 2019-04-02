package com.sycho.rxapps.activities

import android.os.Bundle
import com.sycho.rxapps.R
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_hello.*

/**
 * RxLifecycle 라이브러리를 사용하는 방법이다.
 * 다만 AppCompat theme를 요구하고 있는데 leanback과 충돌이 있으므로 이 프로젝트에서는 사용하지 않는다.
 *
 * @author Cho Seong-yong
 * @since 2019.04.02
 */
class HelloRxAppActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        // compose() 함수로 RxLifecycle 설정한다.
        // bindToLifecycle() 함수를 전달하면 lifecycle에 맞게 쌍으로 동작한다.
        // onCreate()에서 구독했으면 onDestroy()에서 해제되는 식이다.
        // bindUntilEvent() 함수를 전달해서 해제 시점을 직접 지정할 수도 있다.
        Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("Hello world!")
            emitter.onComplete()
        })
//            .compose(bindToLifecycle())
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe(text_view::setText)
    }
}