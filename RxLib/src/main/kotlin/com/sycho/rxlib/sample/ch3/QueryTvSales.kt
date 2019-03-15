package com.sycho.rxlib.sample.ch3

import io.reactivex.Maybe
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.rxkotlin.toObservable

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.06
 */
class QueryTvSales {
    /**
     * 데이터 쿼리하는 예제를 만들어 보자.
     *
     * TV: $2,500
     * Camera: $300
     * TV: $1,600
     * Phone: $800
     *
     * 1. 전체 매출 데이터를 입력함.         -> 데이터 구조는?
     * 2. 매출 데이터 중 TV 매출을 필터링함. -> filter()로 TV인거 필터링 하면 된다.
     * 3. TV 매출의 합을 구함.               -> reduce() 타입은 BiFunction<Int, Int, Int>
     */
    fun run() {
        // 데이터 입력. 데이터 종류는 2가지이고 타입이 다르므로 이를 완벽히 수용할 수 있는 Pair를 사용한다.
        // Pair 클래스를 까보면 결국 데이터 클래스 인것을 알 수 있다.
        // 물론 데이터 종류가 많으면 데이터 클래스 직접 만들어서 쓰면 된다.
        // 자바에서는 Pair는 Apache commons Lang3 라이브러리꺼 사용하지만 kotlin stdlib 에서 기본으로 제공한다.
        // 또한 serializable 하므로 활용성도 높다.
        // 데이터가 3개 필요하면 Triple을 사용하면 된다.
        val sales = arrayListOf<Pair<String, Int>>()
        sales.add(Pair("TV", 2500))
        sales.add(Pair("Camera", 300))
        sales.add(Pair("TV", 1600))
        sales.add(Pair("Phone", 800))

        val filterOp = Predicate<Pair<String, Int>> { data -> data.first == "TV" }
        // reduce()는 병합이라서 인자가 모두 같은 타입이어야 한다.
        // 아래 처럼 하는것도 되긴 하지만 새로 인스턴스를 만드니 클래스를 직접 사용하는건 피해야 겠다.
//        val dataOp = BiFunction<Pair<String, Int>, Pair<String, Int>, Pair<String, Int>> { a, b -> Pair("TV", a.second + b.second) }
//        val source = sales.toObservable().filter(filterOp).reduce(dataOp)
//        source.subscribe { System.out.println("TV Sales: ${it.second}") }

        // Pair<String, Int> 에서 Int 값만 가져오도록 가공한다.
        val getSecond = Function<Pair<String, Int>, Int> { it.second }
        // 가져온 Int 값들을 합한다.
        val dataOp = BiFunction<Int, Int, Int> { a, b -> a + b }
        val source = sales.toObservable().filter(filterOp).map(getSecond).reduce(dataOp)
        source.subscribe { System.out.println("TV Sales: $it") }
    }
}