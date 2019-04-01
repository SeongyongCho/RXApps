package com.sycho.rxapps.square

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * kotlin에서 Singleton을 만들고 싶다면 object를 사용하도록 한다.
 * 이 클래스를 java로 디컴파일 해보면 static 으로 인스턴스 생성하고 있는 코드가 나올 것이다.
 *
 * @author Cho Seong-yong
 * @since 2019.03.27
 */
object RestfulAdapter {

    private const val BASE_URI = "https://api.github.com/"

    /**
     * REST API 스택의 디버깅이 가능하도록 하고
     * 이를 위해서 OkHttpClient를 따로 구성한다.
     * 이 메소드를 사용하면 네트워크 연동 과정을 로그로 볼 수 있다.
     */
    fun getServiceApi(): GitHubServiceApi {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URI)
            .build()

        return retrofit.create(GitHubServiceApi::class.java)
    }

    /**
     * Retrofit에 포함된 OkHttpClient를 사용한다.
     * 이 메소드를 사용하면 로그상으로는 아무것도 안 나올 것이다.
     */
    fun getSimpleApi(): GitHubServiceApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GitHubServiceApi::class.java)
    }
}