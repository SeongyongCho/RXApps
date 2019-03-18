package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.12
 */
class TrampolineSchedulerExample {
    /**
     * Trampoline 스케줄러는 새로운 thread를 생성하지 않고 현재 thread에서 무한의 queue를 생성한다.
     * queue에 작업을 넣은다음 하나씩 꺼내서 동작하기 때문에 구독 1이 먼저 그 다음 구독 2가 실행된다.
     */
    fun basic() {
        val orgs = arrayOf("1", "3", "5")
        val source = orgs.toObservable()

        // 구독 #1
        source.subscribeOn(Schedulers.trampoline())
            .map { data -> "<<$data>>" }
            .subscribe { Log.i(it) }

        // 구독 #2
        source.subscribeOn(Schedulers.trampoline())
            .map { data -> "##$data##" }
            .subscribe { Log.i(it) }

        Utils.sleep(500)
    }
}