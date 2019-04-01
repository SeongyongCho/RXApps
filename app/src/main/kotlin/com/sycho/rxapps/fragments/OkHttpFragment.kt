package com.sycho.rxapps.fragments

import android.os.Bundle
import android.view.View
import com.sycho.rxapps.R
import com.sycho.rxapps.square.Contributor
import com.sycho.rxapps.square.RestfulAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_okhttp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Retrofit과 OkHttp를 활용하여 서버 연동한다.
 *
 *
 * @author Cho Seong-yong
 * @since 2019.03.27
 */
class OkHttpFragment : BaseFragment(), View.OnClickListener {

    companion object {
        // https://github.com/SeongyongCho/RXApps
        /**
         * github 사용자 이름
         */
        private const val sName = "SeongyongCho"
        /**
         * github 사용자에게 있는 repository
         */
        private const val sRepo = "RXApps"
    }

    private var mCompositeDisposable: CompositeDisposable? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_okhttp
    }

    override fun getLogViewId(): Int? {
        return R.id.ohf_rv_log
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ohf_btn_retrofit.setOnClickListener(this)
        ohf_btn_get_retrofit_okhttp.setOnClickListener(this)
        ohf_btn_get_retrofit_okhttp_rx.setOnClickListener(this)

        mCompositeDisposable = CompositeDisposable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable?.clear()
    }

    override fun onClick(v: View?) {
        v?.let { view ->
            when (view.id) {
                R.id.ohf_btn_retrofit -> {
                    startRetrofit()
                }
                R.id.ohf_btn_get_retrofit_okhttp -> {
                    startOkHttp()
                }
                R.id.ohf_btn_get_retrofit_okhttp_rx -> {
                    startRx()
                }
            }
        }
    }

    /**
     * retrofit + okHttp (Call의 내부)
     *
     */
    private fun startRetrofit() {
        val service = RestfulAdapter.getSimpleApi()
        val call = service.getCallContributors(sName, sRepo)
        call.enqueue(object : Callback<List<Contributor>> {
            override fun onFailure(call: Call<List<Contributor>>, t: Throwable) {
                log(t.message)
            }

            override fun onResponse(
                call: Call<List<Contributor>>,
                response: Response<List<Contributor>>
            ) {
                if (response.isSuccessful) {
                    val contributors = response.body()
                    // body 값이 null을 허용하므로 null 체크 해야 함
                    contributors?.let {
                        for (c: Contributor in it) {
                            log(c.toString())
                        }
                    }
                } else {
                    log("no successful")
                }
            }
        })
    }

    /**
     * retrofit + okHttp
     */
    private fun startOkHttp() {
        val service = RestfulAdapter.getServiceApi()
        val call = service.getCallContributors(sName, sRepo)
        call.enqueue(object : Callback<List<Contributor>> {
            override fun onFailure(call: Call<List<Contributor>>, t: Throwable) {
                log("not successful")
            }

            override fun onResponse(
                call: Call<List<Contributor>>,
                response: Response<List<Contributor>>
            ) {
                if (response.isSuccessful) {
                    val contributors = response.body()
                    contributors?.let {
                        for (c: Contributor in it) {
                            log(c.toString())
                        }
                    }
                } else {

                }
            }
        })
    }

    /**
     * retrofit + okHttp + rxJava
     */
    private fun startRx() {
        val service = RestfulAdapter.getServiceApi()
        val observable = service.getObContributors(sName, sRepo)
        // 생성된 Observable을 구독한다.
        // Retrofit은 RxJava를 지원하기 때문에 Observable로 생성이 가능한 것이고
        // 이에 따라 callback으로 받는게 아니라 구독을 해야한다.
        mCompositeDisposable?.add(observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Contributor>>() {
                override fun onComplete() {
                    log("complete")
                }

                override fun onNext(t: List<Contributor>) {
                    for (c: Contributor in t) {
                        log(c.toString())
                    }
                }

                override fun onError(e: Throwable) {
                    log(e.message)
                }
            })
        )
    }
}