package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class NewThreadSchedulerExample {
    fun basic() {
        val orgs = arrayOf("1", "3", "5")

        orgs.toObservable()
            .doOnNext { data -> Log.v("Original data : $data") }
            .map { data -> "<<$data>>" }
            .subscribeOn(Schedulers.newThread())
            .subscribe { Log.i(it) }
        Utils.sleep(500) // sleep()을 제거하면 main thread가 바로 끝나므로 아무것도 출력되지 않는다.

        orgs.toObservable()
            .doOnNext { data -> Log.v("Original data : $data") }
            .map { data -> "##$data##" }
            .subscribeOn(Schedulers.newThread())
            .subscribe { Log.i(it) }
        Utils.sleep(500)
    }
}