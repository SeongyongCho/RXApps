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
 * @author 파인원_조성용선임
 * @since 2019.03.13
 */
class OpenWeatherMapV1 {
    companion object {
        const val URL = "https://api.openweathermap.org/data/2.5/weather?q=Seoul&APPID="

        @JvmStatic
        fun main(args: Array<String>) {
            OpenWeatherMapV1().run()
        }
    }

    fun run() {
        val source = Observable.just(URL + Utils.API_KEY)
            .map { OkHttpHelper.getWithLog(it) }
            .subscribeOn(Schedulers.io())

        // 어떻게 한 번만 호출하게 만들 수 있을까?
        val temperature = source.map(this::parseTemperature)
        val city = source.map(this::parseCityName)
        val country = source.map(this::parseCountry)
        Utils.exampleStart()

        Observable.concat(temperature, city, country)
            .observeOn(Schedulers.newThread())
            .subscribe { Log.it(it) }
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