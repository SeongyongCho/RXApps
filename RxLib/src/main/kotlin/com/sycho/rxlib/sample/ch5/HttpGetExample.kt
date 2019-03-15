package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import okhttp3.*
import java.io.IOException

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.03.12
 */
class HttpGetExample {
    companion object {
        const val URL_README = "https://raw.githubusercontent.com/yudong80/reactivejava/master/README.md"
    }

    private val client: OkHttpClient = OkHttpClient()

    fun run() {
        val request = Request.Builder()
            .url(URL_README)
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(response.body()?.string())
            }
        })
    }
}