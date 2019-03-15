package com.sycho.rxlib.sample.ch4

import com.sycho.rxlib.sample.common.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.toObservable
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.math.min

/**
 * 전기요금 계산 예제
 *
 * @author 파인원_조성용선임
 * @since 2019.03.11
 */
class ElectricBills {

    private var index = 0

    fun electricBillV1() {
        // 사용량
        val data = arrayOf(
            "100",  // 910 + 93.3 * 100 = 10,240원
            "300"   // 1600 + 93.3 * 200 + 187.9 * 100 = 39,050원
        )

        // 기본요금
        val basePrice = data.toObservable()
            .map { it.toInt() }
            .map { v ->
                when {
                    v <= 200 -> 910
                    v <= 400 -> 1600
                    else -> 7300
                }
            }

        // 전력량
        val usagePrice: Observable<Int> = data.toObservable()
            .map { it.toInt() }
            .map { v ->
                val series1 = min(200, v) * 93.3
                val series2 = min(200, max(v-200, 0)) * 187.9
                val series3 = min(0, max(v-400, 0)) * 280.65
                (series1 + series2 + series3).toInt()
            }
        // 사용량에 대항 기본요금 발행되고 전력량 발행되면 이거 두개 더해서 요금 발행한다.
        val source = Observable.zip(basePrice, usagePrice, BiFunction<Int, Int, Int> { v1, v2 -> v1 + v2 })
        source.map { v -> DecimalFormat("#,###").format(v) }    // 발행된 요금을 포맷 적용한다
            .subscribe { v ->
                val sb = StringBuilder()
                sb.append("Usage: " + data[index] + " kWh => ")  // 사용량 출력한다.
                sb.append("Price: ${v}원")                       // 포맷 적용된 요금 출력한다
                Log.i(sb.toString())
                index++ // 맴버변수는 side effect 발생할 수 있으므로 함수형 프로그래밍에서 사용하지 않아야 한다
            }
    }

    fun electricBillV2() {
        // 사용량
        val data = arrayOf(
            "100",  // 910 + 93.3 * 100 = 10,240원
            "300"   // 1600 + 93.3 * 200 + 187.9 * 100 = 39,050원
        )

        // 기본요금
        val basePrice = data.toObservable()
            .map { it.toInt() }
            .map { v ->
                when {
                    v <= 200 -> 910
                    v <= 400 -> 1600
                    else -> 7300
                }
            }

        // 전력량
        val usagePrice: Observable<Int> = data.toObservable()
            .map { it.toInt() }
            .map { v ->
                val series1 = min(200, v) * 93.3
                val series2 = min(200, max(v-200, 0)) * 187.9
                val series3 = min(0, max(v-400, 0)) * 280.65
                (series1 + series2 + series3).toInt()
            }

        // 맴버변수 참조 없앤답시고 Pair 객체를 두개나 생성했다.
        // 다른 방법은 없나?
        val source = Observable.zip(basePrice, usagePrice, data.toObservable(),
            Function3<Int, Int, String, Pair<String, Int>> { v1, v2, i -> Pair(i, v1+v2) })     // Pair 생성
        source.map { fee -> Pair(fee.first, DecimalFormat("#,###").format(fee.second)) }        // Pair 생성
            .subscribe { v ->
                val sb = StringBuilder()
                sb.append("Usage: ${v.first} kWh => ")  // 사용량 출력한다.
                sb.append("Price: ${v.second}원")       // 포맷 적용된 요금 출력한다
                Log.i(sb.toString())
            }
    }
}