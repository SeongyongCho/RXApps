package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.11
 */
class MergeExample {
    /**
     * merge() 함수는 입력 Observable의 순서와 모든 Observable이 데이터를 발행하는지 등에 대하여 관여하지 않고
     * 어느 쪽이든 업스트림에 먼저 입력되는 데이터를 그대로 발행한다.
     */
    fun marbleDiagram() {
        val data1 = arrayOf("1", "3")
        val data2 = arrayOf("2", "4", "6")

        val source1 = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
            .map { it.toInt() }
            .map { idx -> data1[idx] }
            .take(data1.size.toLong())
        val source2 = Observable.interval(50L, TimeUnit.MILLISECONDS)
            .map { it.toInt() }
            .map { idx -> data2[idx] }
            .take(data2.size.toLong())

        val source = Observable.merge(source1, source2)

        source.subscribe { Log.i(it) }
        Utils.sleep(1000)
    }
}