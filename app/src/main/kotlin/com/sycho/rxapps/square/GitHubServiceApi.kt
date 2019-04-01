package com.sycho.rxapps.square

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import java.util.concurrent.Future

/**
 * Retrofit의 어노테이션을 활용하여 메소드 선언
 *
 * @author Cho Seong-yong
 * @since 2019.03.27
 */
interface GitHubServiceApi {

    // 기본 URL의 뒤에 추가로 붙는 것들이다.
    // 인자로 받는 owner와 repo가 각각 {owner}와 {repo}로 들어간다.
    @GET("repos/{owner}/{repo}/contributors")
    fun getCallContributors(@Path("owner") owner: String, @Path("repo") repo: String): Call<List<Contributor>>

    @GET("repos/{owner}/{repo}/contributors")
    fun getObContributors(@Path("owner") owner: String, @Path("repo") repo: String): Observable<List<Contributor>>

    @Headers("Accept: application/vnd.github.v3.full+json")
    @GET("repos/{owner}/{repo}/contributors")
    fun getFutureContributors(@Path("owner") owner: String, @Path("repo") repo: String): Future<List<Contributor>>
}