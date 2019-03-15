package com.sycho.rxlib.sample.ch3

import com.sycho.rxlib.sample.common.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.util.*

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.05
 */
class Gugudan {

    private fun mainOutput(name: String) : Int {
        val scanner = Scanner(System.`in`)
        System.out.println("$name 몇 단? : ")
        return scanner.nextLine().toInt()
    }

    fun plainKotlin() {
        val dan = mainOutput(Log.getMethodName())
        for (row in 1..9) {
            System.out.println("$dan * $row = " + dan * row)
        }
    }

    fun plainRx1() {
        val dan = mainOutput(Log.getMethodName())
        val source = Observable.range(1, 9) // for문을 range()로 대체했다.
        source.subscribe { row -> System.out.println("$dan * $row = " + dan * row) }
    }

    fun plainRx2() {
        val num = mainOutput(Log.getMethodName())
        // 입력받은 단수로 구구단 해서 9번 발행한다.
        val gugudan = Function<Int, Observable<String>> {
            dan -> Observable.range(1, 9).map { row -> "$dan * $row = " + dan * row }
        }
        // 입력받은 단수 발행해서 gugudan 함수로 던진다
        val source = Observable.just(num).flatMap(gugudan)
        source.subscribe { System.out.println("$it") }
    }

    /**
     * plainRx2() 에서 만든 함수를 인라인으로 flatMap() 함수에 넣어보자
     */
    fun plainRx3() {
        val num = mainOutput(Log.getMethodName())
        val source = Observable.just(num).flatMap { dan ->
            Observable.range(1, 9).map { row -> "$dan * $row = " + dan * row }
        }
        source.subscribe { System.out.println("$it") }
    }

    /**
     * Function으로 Int를 9개 전달하고
     * 입력받은 인자와 Function의 리턴값으로 BiFunction에서 구구단 결과를 전달한다.
     * 입력과 출력을 분리하여 출력을 달리 하고 싶을 때 사용하면 될 것 같다.
     */
    fun usingResultSelector() {
        val num = mainOutput(Log.getMethodName())
        val gugudan = Function<Int, Observable<Int>> { Observable.range(1, 9) }
        val biGugudan = BiFunction<Int, Int, String> {
            dan, row -> "$dan * $row = " + dan * row
        }
        val source = Observable.just(num).flatMap(gugudan, biGugudan)
        source.subscribe { System.out.println("$it") }
    }
}