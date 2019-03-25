package com.sycho.rxapps.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import com.sycho.rxapps.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.fragment_debounce_search.*
import java.util.concurrent.TimeUnit


/**
 * 추천 검색어 기능 구현하기
 *
 */
class DebounceSearchFragment : BaseFragment() {

    private var mDisposable: Disposable? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_debounce_search
    }

    override fun getLogViewId(): Int {
        return R.id.dsf_rv_log
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 지정된 시간내의 이벤트는 무시하고 이후 최종으로 한번만 이벤트를 발생시키는 debounce()함수를 사용한다.
        mDisposable = getObservable()
            .debounce(500, TimeUnit.MILLISECONDS)    // 500ms 내의 이벤트는 무시하도록 한다
            .filter { s -> !TextUtils.isEmpty(s) }           // 텍스트가 있는것만 필터링한다
            .observeOn(AndroidSchedulers.mainThread())       // 처리가 끝나고 리스트에 표시할 것이므로 UI thread 사용하도록 한다.
            .subscribeWith(getObserver())                    // 구독해제 할 수 있도록 subscribeWith() 함수를 사용한다.

        // 위와 기법은 동일하다. 단지 RxBinding을 사용했기 때문에 Observable 생성이 필요 없을 뿐이다.
        // RxBinding이 참 좋아보이기는 하는데 커스텀 레이아웃 및 View를 많이 쓰는 프로젝트에서도 과연 쓸만할지는 모르겠다.
        mDisposable = RxTextView.textChangeEvents(dsf_input_deb_search)
            .debounce(400, TimeUnit.MILLISECONDS)
            .filter { s -> !TextUtils.isEmpty(s.text()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getObserverLib())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDisposable?.dispose()
    }

    private fun getObservable(): Observable<CharSequence> {
        // EditText의 TextChangedListener에 TextWatcher를 전달하였다
        // TextWatcher의 onTextChanged()가 사용자 입력에 의한 텍스트 변화 이벤트이며
        // 이것이 호출되면 해당 텍스트를 발행하도록 하고 있다.
        return Observable.create { emitter -> dsf_input_deb_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // onNext()에는 null이 들어갈 수 없으니 null이 아닐 때만 보내도록 한다.
                s?.let { emitter.onNext(it) }
            }
        }) }
    }

    private fun getObserver(): DisposableObserver<CharSequence> {
        return object : DisposableObserver<CharSequence>() {
            override fun onComplete() {

            }

            override fun onNext(t: CharSequence) {
                log("Search $t")
            }

            override fun onError(e: Throwable) {

            }
        }
    }

    /**
     * RxBinding을 사용하여 View를 담고 있는 이벤트를 발행받도록 하고 있다.
     */
    private fun getObserverLib(): DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>() {
            override fun onComplete() {

            }

            override fun onNext(t: TextViewTextChangeEvent) {
                log("Search ${t.text()}")
            }

            override fun onError(e: Throwable) {

            }
        }
    }

}
