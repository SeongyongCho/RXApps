package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Shape
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class FlipExample {
    /**
     * subscribeOn()은 구독자가 Observable에 subscribe()함수를 호출하여 구독할 때 실행되는 thread를 지정한다.
     * observeOn()은 Observable에서 생성한 데이터 흐름이 처리될 때의 thread를 지정한다.
     * 여기서는 둘 다 newThread()로 했으므로 각각 새로운 thread에서 동작한다.
     */
    fun marbleDiagram() {
        val objs = arrayOf("1-S", "2-T", "3-P")
        val source = objs.toObservable()
            .doOnNext { data -> Log.v("Original data = $data") }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .map { Shape.flip(it) }
        source.subscribe { Log.i(it) }
        Utils.sleep(500)
    }

    /**
     * marbleDiagram() 예제에서 observeOn()을 제거하면,
     * 앞에 subscribeOn()에서 지정한 thread에서 전부 실행된다.
     */
    fun observedOnRemove() {
        val objs = arrayOf("1-S", "2-T", "3-P")
        val source = objs.toObservable()
            .doOnNext { data -> Log.v("Original data = $data") }
            .subscribeOn(Schedulers.newThread())
//            .observeOn(Schedulers.newThread())
            .map { Shape.flip(it) }
        source.subscribe { Log.i(it) }
        Utils.sleep(500)
    }
}