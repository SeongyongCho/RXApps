package com.sycho.rxapps.fragments

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.sycho.rxapps.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.fragment_on_click.*


class OnClickFragment : BaseFragment() {

    companion object {
        private const val SEVEN = 7
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_on_click
    }

    override fun getLogViewId(): Int {
        return R.id.rv_log
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getClickEventObservable()
            .map { "clicked" } // 발행된 View를 그냥 String으로 변경하였다
            .subscribe(getObserver())

        getClickEventObservableWithLambda()
            .map { "clicked lambda" }
            .subscribe(this::log)

        getClickEventObservableWithRxBinding()
            .subscribe(this::log)

        getClickEventObservableExtra()
            .map { SEVEN }
            .flatMap(this::compareNumbers)
            .subscribe(this::log)
    }

    /**
     * onClick() 메소드에 Observable을 활용한다.
     * create()함수에 들어가는 인자가 어떻게 되어 있는지 잘 살펴본다.
     */
    private fun getClickEventObservable(): Observable<View> {
        return Observable.create(object : ObservableOnSubscribe<View> {
            override fun subscribe(emitter: ObservableEmitter<View>) {
                // onClick() 이벤트에 View가 전달되는데 이 v를 Observable<View>로 전달한다.
//                btn_click_observer.setOnClickListener(object : View.OnClickListener {
//                    override fun onClick(v: View?) {
//                        v?.let {
//                            emitter.onNext(it)
//                        }
//                    }
//                })

                // 위 코드를 람다식으로 쓰면 아래와 같다.
                // 여러 라인에 걸쳐 작업이 필요하면 아래와 같고
                btn_click_observer.setOnClickListener { view ->
                    emitter.onNext(view)
                }

                // 단지 onNext()로 끝이면 아래와 같다.
//                btn_click_observer_lambda.setOnClickListener(emitter::onNext)
            }
        })
    }

    /**
     * [getClickEventObservable]를 람다식으로 표현했다. 기능은 완전히 동일하다.
     */
    private fun getClickEventObservableWithLambda(): Observable<View> {
        return Observable.create { emitter -> btn_click_observer_lambda.setOnClickListener(emitter::onNext) }
    }

    /**
     * RxBinding을 사용하여 더 간단하게 하였다
     * RxView에 clicks() 메소드에 View를 전달하여 내부에서 자동으로 클릭 리스너가 처리되고
     * Observable도 명시적으로 생성할 필요가 없다.
     * 코드가 간결해지는건 좋은데 숙달되지 않았다면 오히려 뭔 코드인지 알 수 없으므로
     * 혼자하는 프로젝트에서만 사용하는게 좋을 것 같다.
     */
    private fun getClickEventObservableWithRxBinding(): Observable<String> {
        return RxView.clicks(btn_click_observer_binding).map { "Clicked RxBinding" }
    }

    private fun getClickEventObservableExtra(): Observable<View> {
        return Observable.create { emitter -> btn_click_observer_extra.setOnClickListener(emitter::onNext) }
    }

    /**
     * Observable 조합 예제이다.
     * Observable을 생성해서 데이터를 처리한 결과를 다른 Observable과 조합하여 최종 결과를 낸다.
     * 이러한 기법이 바로 리액티브 프로그래밍이다.
     */
    private fun compareNumbers(input: Int): Observable<String> {
        return Observable.just(input)
            .flatMap {
                val random = java.util.Random()
                val data = random.nextInt(10)
                Observable.just("local : $it", "remote : $data", "result = ${it == data}")
            }
    }

    // private DisposableObserver<? super String> getObserver()
    private fun getObserver(): DisposableObserver<in String> {
        return object : DisposableObserver<String>() {
            override fun onComplete() {
                log("complete")
            }

            override fun onNext(t: String) {
                log(t)
            }

            override fun onError(e: Throwable) {
                log(e.message)
            }
        }
    }
}
