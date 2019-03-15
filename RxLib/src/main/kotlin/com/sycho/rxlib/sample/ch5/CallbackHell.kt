package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import okhttp3.*
import java.io.IOException

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class CallbackHell {
    companion object {
        private const val FIRST_URL = "https://api.github.com/zen"
        private const val SECOND_URL = "${Utils.GITHUB_ROOT}/samples/callback_hell"
    }

    private val client = OkHttpClient()

    private val onSuccess = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            Log.i(response.body()?.string())
        }
    }

    /**
     * callback이 중첩되어 사용된다.
     */
    fun run() {
        val request = Request.Builder()
            .url(FIRST_URL)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(response.body()?.string())

                // 콜팩을 다시 추가
                val request2 = Request.Builder()
                    .url(SECOND_URL)
                    .build()
                client.newCall(request2).enqueue(onSuccess)
            }
        })
    }
}