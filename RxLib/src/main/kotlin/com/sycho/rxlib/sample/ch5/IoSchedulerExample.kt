package com.sycho.rxlib.sample.ch5

import com.sycho.rxlib.sample.common.Log
import com.sycho.rxlib.sample.common.Utils
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.12
 */
class IoSchedulerExample {
    /**
     * IO Scheduler는 입출력 작업의 결과를 얻기까지 대기시간이 길기 때문에 필요할 때마다 thread를 계속 생성한다.
     * 네트워크 요청, 파일 IO, DB 쿼리 등에서 사용하도록 한다.
     */
    fun basic() {
        val root = "c:\\"
        val files = File(root).listFiles()
        val source = files.toObservable().filter { f -> !f.isDirectory }.map { f -> f.absolutePath }.subscribeOn(Schedulers.io())
        source.subscribe { Log.i(it) }
        Utils.sleep(500)
    }
}