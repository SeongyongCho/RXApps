package com.sycho.rxlib.sample.ch3

import com.sycho.rxlib.sample.common.Log
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.02.28
 */
class MapExample {

    /**
     * map()은 어떤 값을 입력받아서 반환하는 함수의 포인터가 들어간다.
     * 이 예제를 보면 람다식으로 들어가있는데
     * String을 입력 받아서 끝에 마름모를 추가하여 반환하는 함수이다.
     */
    fun marbleDiagram() {
        val balls = arrayOf("1", "2", "3", "5")
//        val dBall = Function<String, String> {
//            ball -> "$ball◇"
//        }
//        val source = balls.toObservable().map(dBall)
        // dBall 함수는 한 줄짜리이므로 간단하게 몸체만 집어넣으면 된다.
        // 함수 포인터가 들어갈 것이면 map(), 함수 몸체가 들어갈 것이면 map {} 이다. 차이점 명심해라.
        val source = balls.toObservable().map { ball -> "$ball◇" }
        // 발행순서대로 각각 끝에 마름모 추가되어 출력된다.
        source.subscribe { Log.i(it) }
    }

    /**
     * 여러문장으로 구성된 함수 포인터를 map()에 전달해보는 예제이다.
     */
    fun mappingType() {
        // rxJava 제네릭 함수형 인터페이스
        // Predicate<T>::boolean test(T t) - t값을 받아서 참이나 거짓을 반환한다.
        // Consumer<T>::void accept(T t) - t값을 받아서 처리한다.
        // Function<T, R>::R apply(T t) - t값을 받아서 결과를 반환한다.
        val ballToIndex = Function<String, Int> { ball ->
            when (ball) {
                "RED" -> 1
                "YELLOW" -> 2
                "GREEN" -> 3
                "BLUE" -> 5
                else -> -1
            }
        }

        val balls = arrayOf("RED", "YELLOW", "GREEN", "BLUE")
        val source = balls.toObservable().map(ballToIndex)
        source.subscribe { System.out.println("$it") }
    }
}