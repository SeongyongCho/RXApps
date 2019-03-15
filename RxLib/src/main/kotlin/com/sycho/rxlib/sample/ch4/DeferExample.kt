package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Shape
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import java.util.concurrent.Callable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.06
 */
class DeferExample {

    private val colors = arrayListOf("1", "3", "5", "6").iterator()

    /**
     * defer()함수는 timer()함수와 비슷한데 데이터 흐름 생성을 구독자가 subscribe() 함수를 호출할 때까지 미룰 수 있다.
     * 현재 thread에서 실행되며 인자로 Callable을 받으므로 구독자가 subscribe()를 호출할 때까지 call() 메소드 호출을 미룰 수 있다.
     */
    fun marbleDiagram() {
        val supplier = Callable<Observable<String>> { getObservable() }
        val source = Observable.defer(supplier)

        source.subscribe { Log.i("Subscriber #1:$it") }
        source.subscribe { Log.i("Subscriber #2:$it") }
    }

    fun notDeferred() {
        val source = getObservable()
        source.subscribe { Log.i("Subscriber #1:$it") }
        source.subscribe { Log.i("Subscriber #2:$it") }
    }

    /**
     * 번호가 적힌 도형을 발행하는 Observable을 생성
     */
    private fun getObservable() : Observable<String> {
        if (colors.hasNext()) {
            val color = colors.next()
            return Observable.just(
                Shape.getString(color, Shape.BALL),
                Shape.getString(color, Shape.RECTANGLE),
                Shape.getString(color, Shape.PENTAGON))
        }

        return Observable.empty()
    }
}