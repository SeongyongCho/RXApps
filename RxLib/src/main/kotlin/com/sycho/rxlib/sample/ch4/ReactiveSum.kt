package com.sycho.rxlib.sample.ch4

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.observables.ConnectableObservable
import java.util.*

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.11
 */
class ReactiveSum {
    fun run() {
        val source = userInput() // 사용자가 입력한거에서
        val a = source.filter { str -> str.startsWith("a:") } // a: 로 시작되는거만 발행하도록 하고
            .map { str -> str.replace("a:", "") } // a: 지워서 숫자만 남도록 한다
            .map { it.toInt() } // 숫자만 남겨진것을 Int로 변환한다.
        val b = source.filter { str -> str.startsWith("b:") }
            .map { str -> str.replace("b:", "") }
            .map { it.toInt() }
        Observable.combineLatest(
            a.startWith(0), // 첫번째로 0을 발행한다.
            b.startWith(0), // 두개 Observable 모두 발행됐다
            BiFunction<Int, Int, Int> { x, y -> x + y }) // 발행된 두 값의 합을 제공한다.
            .subscribe { res -> System.out.println("Result: $res") }
        source.connect()
    }

    /**
     * 사용자로부터 값을 입력 받는 무한루프
     */
    private fun userInput() : ConnectableObservable<String> {
        return Observable.create<String> {
            val input = Scanner(System.`in`)
            while (true) {
                System.out.println("Input: ")
                val line = input.nextLine()
                it.onNext(line)

                if (line.indexOf("exit") >= 0) {
                    input.close()
                    break
                }
            }
        }.publish()
    }
}