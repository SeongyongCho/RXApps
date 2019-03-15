package com.sycho.rxlib.sample.common

import java.io.IOException
import java.net.InetAddress
import kotlin.random.Random

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.02.28
 */
class Utils {
    companion object {
        const val GITHUB_ROOT = "https://raw.githubusercontent.com/yudong80/reactivejava/master/"
        const val API_KEY = "eb7347787a81cfd66b59e60a71a28e04"
        const val ERROR_CODE = "-500"
        var startTime: Long = 0

        fun exampleStart() {
            startTime = System.currentTimeMillis()
        }

        fun exampleStart(any: Any) {
            startTime = System.currentTimeMillis()
            Log.it(any)
        }

        fun exampleComplete() {
            System.out.println("---------------------------")
        }

        fun sleep(millis: Long) {
            try {
                Thread.sleep(millis)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        fun doSomething() {
            try {
                Thread.sleep(Random.nextInt(100).toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        private val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun numberToAlphabet(x: Long) : String {
            return ALPHABET[x.toInt() % ALPHABET.length].toString()
        }

        fun isNetworkAvailable() : Boolean {
            try {
                return InetAddress.getByName("www.google.com").isReachable(1000)
            } catch (e: IOException) {
                Log.v("Network is not available")
            }
            return false
        }

        fun toInt(v: String) : Int {
            return Integer.parseInt(v)
        }
    }
}