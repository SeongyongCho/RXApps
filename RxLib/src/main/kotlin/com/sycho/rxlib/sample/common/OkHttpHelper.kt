package com.sycho.rxlib.sample.common

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.07
 */
class OkHttpHelper {
    companion object {
        val client = OkHttpClient()
        const val ERROR = "ERROR"

        @JvmStatic
        fun get(url: String) : String? {
            val request = Request.Builder().url(url).build()
            try {
                val res = client.newCall(request).execute()
                return res.body()?.string()
            } catch (e: IOException) {
                Log.e(e.message)
                throw e
            }
        }

        @JvmStatic
        fun getT(url: String) : String? {
            val request = Request.Builder().url(url).build()
            try {
                val res = client.newCall(request).execute()
                return res.body()?.string()
            } catch (e: IOException) {
                Log.et(e.message)
                throw e
            }
        }

        @JvmStatic
        fun getWithLog(url: String) : String? {
            Log.d("OkHttp call URL = $url")
            return get(url)
        }
    }
}