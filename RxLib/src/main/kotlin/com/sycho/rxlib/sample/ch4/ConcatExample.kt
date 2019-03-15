package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.11
 */
class ConcatExample {
    /**
     * concat() 함수는 2개 이상의 Observable을 이어 붙여준다.
     * 첫번째 Observable에서 onComplete 이벤트가 발생해야 다음 Observable을 구독한다.
     * 그런데 onComplete 이벤트가 발생하지 않으면 다음 Observable을 계속해서 대기하므로
     * memory leak이 발생할 수 있으니 반드시 onComplete 되도록 해야 한다.
     */
    fun marbleDiagram() {
        val onCompleteAction = Action { Log.d("onComplete()") }

        val data1 = arrayOf("1", "3", "5")
        val data2 = arrayOf("2", "4", "6")
        val source1 = data1.toObservable().doOnComplete(onCompleteAction)
        val source2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
            .map { it.toInt() }
            .map { idx -> data2[idx] }
            .take(data2.size.toLong())
            .doOnComplete(onCompleteAction)

        val source = Observable.concat(source1, source2).doOnComplete(onCompleteAction)
        source.subscribe { Log.i(it) }
        Utils.sleep(1000)
    }
}