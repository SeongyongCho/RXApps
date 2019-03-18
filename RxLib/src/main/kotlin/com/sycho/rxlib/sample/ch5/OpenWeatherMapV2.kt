package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.OkHttpHelper
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.13
 */
class OpenWeatherMapV2 {
    companion object {
        const val URL = "https://api.openweathermap.org/data/2.5/weather?q=Seoul&APPID="

        @JvmStatic
        fun main(args: Array<String>) {
            OpenWeatherMapV2().run()
        }
    }

    fun run() {
        Utils.exampleStart()

        // ConnectableObservable을 사용하여 1개의 Observable을 여러 구독자가 공유하도록
        // 차가운 Observable을 뜨거운 Observable로 변환하였다.
        val source = Observable.just(URL + Utils.API_KEY)
            .map { OkHttpHelper.getWithLog(it) }
            .subscribeOn(Schedulers.io())
            .share() // ConnectableObserver 클래스의 publish()와 refCount()를 사용하는데 이 두개를 합친것이 바로 share() 함수이다.
            .observeOn(Schedulers.single())

        // 각각 출력하도록 했는데 별도 thread이므로 순서가 맞지않는 경우가 있다.
        // 그래서 위의 observeOn()에는 newThread가 아닌 single로 집어넣어봤다.
        // 뭐 어차피 이런식으로 각각 별도로 파싱하지는 않을 것이니 넘어가자.
        source.map(this::parseTemperature).subscribe { Log.it(it) }
        source.map(this::parseCityName).subscribe { Log.it(it) }
        source.map(this::parseCountry).subscribe { Log.it(it) }

        Utils.sleep(2000)
    }

    private fun parseTemperature(json: String) : String {
        return parse(json, "\"temp\":[0-9]*.[0-9]*")
    }

    private fun parseCityName(json: String) : String {
        return parse(json, "\"name\":\"[a-zA-Z]*\"")
    }

    private fun parseCountry(json: String) : String {
        return parse(json, "\"country\":\"[a-zA-Z]*\"")
    }

    private fun parse(json: String, regex: String) : String {
        val pattern = Pattern.compile(regex)
        val match = pattern.matcher(json)
        if (match.find()) {
            return match.group()
        }

        return "N/A"
    }
}