package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.07
 */
class ConcatMapExample {
    /**
     * concatMap()은 flatMap()과 비슷한데
     * flatMap()이 먼저 들어온 데이터를 처리하는 도중에 새로운 데이터가 들어오면 이것이 먼저 출력 될 수 있는 인터리빙이 발생할 수 있는 반면,
     * concatMap()은 먼저 들어온 데이터 순서대로 처리해서 결과를 내보낼 수 있도록 보장해준다.
     */
    fun marbleDiagram() {
        Utils.exampleStart()
        val balls = arrayOf("1", "3", "5")
        val source = Observable.interval(100L, TimeUnit.MILLISECONDS) // 100ms 마다 발행
            .map { it.toInt() }
            .map { balls[it] }
            .take(balls.size.toLong())
            .concatMap { ball -> Observable.interval(200L, TimeUnit.MILLISECONDS) // 200ms 마다 발행
                .map { "$ball◇" }
                .take(2) }
        // 발행 시간으로 봤을 때 조합 전에 발행된 데이터가 먼저 조합 되겠지만 이를 concatMap()이 잡아주고 있다.
        // 인터리빙을 허용하지 않기 때문에 순서대로 처리하는 만큼 시간이 소요된다.
        source.subscribe { Log.it(it) }
        Utils.sleep(2000)
    }

    fun interleaving() {
        Utils.exampleStart()
        val balls = arrayOf("1", "3", "5")
        val source = Observable.interval(100L, TimeUnit.MILLISECONDS) // 100ms 마다 발행
            .map { it.toInt() }
            .map { balls[it] }
            .take(balls.size.toLong())
            .flatMap { ball -> Observable.interval(200L, TimeUnit.MILLISECONDS) // 200ms 마다 발행
                .map { "$ball◇" }
                .take(2) }
        // 인터리빙을 허용하므로 순서가 섞이고 시간도 적게 소요된다.
        source.subscribe { Log.it(it) }
        Utils.sleep(2000)
    }
}