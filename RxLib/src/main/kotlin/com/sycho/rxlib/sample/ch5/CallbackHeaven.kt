package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.OkHttpHelper
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class CallbackHeaven {
    companion object {
        private const val FIRST_URL = "https://api.github.com/zen"
        private const val SECOND_URL = "${Utils.GITHUB_ROOT}samples/callback_hell"
    }

    /**
     * concatWith()로 합쳐서 첫번째 서버요청 후에 두번째 서버요청 수행되도록 했다.
     * 근데 굳이 첫번째 결과가 없어도 두번째 요청은 가능하므로 추가 개선이 가능하다.
     */
    fun usingConcat() {
        Utils.exampleStart()
        val source = Observable.just(FIRST_URL)
            .subscribeOn(Schedulers.io())
            .map { OkHttpHelper.get(it) }
            .concatWith(Observable.just(SECOND_URL).map { OkHttpHelper.get(it) })
        source.subscribe { Log.it(it.toString()) }
        Utils.sleep(5000)
    }

    /**
     * 서버요청 Observable을 완벽히 둘로 나눴고
     * 둘 다 바로 서버 요청하고 결과만 합해서 출력하도록 했다.
     * 이런식으로 서로 영향이 없는 서버요청은 zip()으로 묶어버릴 수 있다.
     */
    fun usingZip() {
        Utils.exampleStart()

        val first = Observable.just(FIRST_URL)
            .subscribeOn(Schedulers.io())
            .map { OkHttpHelper.get(it) }
        val second = Observable.just(SECOND_URL)
            .subscribeOn(Schedulers.io())
            .map { OkHttpHelper.get(it) }

        Observable.zip(first, second, BiFunction<String?, String?, String> { a, b ->
            when {
                a == null -> "First response is null"
                b == null -> "Second response is null"
                else -> "\n>>$a\n>>$b"
            }
        }).subscribe { Log.it(it) }

        Utils.sleep(5000)
    }
}