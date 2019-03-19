package com.sycho.rxapps.activities

import android.os.Bundle
import com.sycho.rxapps.R
import com.trello.rxlifecycle3.components.RxActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_hello.*

/**
 * RxLifecycle 라이브러리는 Activity, Fragment 등 안드로이드 앱의 라이프 사이클을 rxJava에서 사용할 수 있게 해준다.
 * RxActivity를 살펴보면 기존 Activity를 상속하고 각 상태별로 라이프 사이클 관리 구현이 되어 있다.
 * 구독할 때 발생할 수 있는 메모리 누수를 방지하기 위해 사용하는 것이며
 * 여기서는 Activity가 종료되면 Observable은 자동으로 해제(dispose)된다.
 * Activity 종료 이외에 해제하고 싶다면 dispose() 함수를 사용하여 직접 관리하도록 하자.
 *
 * @author Cho Seong-yong
 * @since 2019.03.18
 */
class HelloActivityV3 : RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        Observable.just("Hello, rx world!")
            .compose(bindToLifecycle()) // 라이프사이클 관리하도록 추가
            .subscribe { text_view.text = it }
    }
}
