package com.sycho.rxlib.sample.ch2.observable

import com.sycho.rxlib.sample.common.Utils
import io.reactivex.observables.ConnectableObservable
import java.util.concurrent.TimeUnit

/**
 * Subject 클래스처럼 Cold Observable을 Hot Observable로 변환한다.
 * 원 데이터를 여러 구독자에게 동시에 전달할 때 사용한다.
 * 발행시점은 connect() 함수 호출이다. subscribe() 함수만 호출해서는 발행되지 않는다.
 *
 * @author 파인원_조성용선임
 * @since 2019.02.28
 */
class ConnectableObservableExample {
    fun marbleDiagram() {
        val dt = arrayOf("1", "3", "5")
        val balls = ConnectableObservable.interval(100L, TimeUnit.MILLISECONDS) // 100ms 간격으로 발행한다
            .map(Long::toInt)
            .map{dt[it]} // 배열에 인덱스 집어넣으면 되는거
            .take(dt.size.toLong())
        val source = balls.publish() // 여러 구독자에게 발행하기 위한 connect() 함수 호출 전까지 발행을 유예한다.
        // 구독자들 구독한다.
        source.subscribe { System.out.println("Subscriber #1 => $it") }
        source.subscribe { System.out.println("Subscriber #2 => $it") }
        // 발행 시작한다.
        source.connect()

        // #1 1
        // #2 1
        // #1 3
        // #2 3
        // 여기 까지 발행돼서 200ms 흘렀다.

        Utils.sleep(250)
        // 250ms 기다렸다가 #3이 구독 시작한다.
        source.subscribe { System.out.println("Subscriber #3 => $it") }
        Utils.sleep(100)
        // 구독한 순서대로 #1 #2 #3 순으로 5 출력한다.
    }
}