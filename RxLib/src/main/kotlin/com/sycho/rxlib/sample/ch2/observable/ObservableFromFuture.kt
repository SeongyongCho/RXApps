package com.sycho.rxlib.sample.ch2.observable

import io.reactivex.Observable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.02.27
 */
class ObservableFromFuture {
    fun basic() {
        val executor = Executors.newSingleThreadExecutor()
        val future = executor.submit<String> {
            System.out.println("Start future...")
            Thread.sleep(1000)
            "Hello Future"
        }

        val source = Observable.fromFuture(future)
        source.subscribe { System.out.println("$it") }
        // Executor는 절대 종료되지 않으므로 반드시 명시적으로 종료시켜줘야 된다.
        // 그렇지 않으면 이 예제 프로그램은 종료되지 않는다!
        // 종료방법은
        // shutdown() : 현재 진행중인 작업이 남아 있다면 모두 끝날때까지 기다렸다가 종료한다
        // shutdownNoe() : 진행중인 작업이 남아 있든 없든 종료한다
        try {
            System.out.println("Attempt to shutdown executor")
            executor.shutdown()
            executor.awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            System.err.println("Tasks interrupted")
        } finally {
            if (!executor.isTerminated) {
                System.err.println("Cancel non-finished tasks")
            }
            executor.shutdownNow()
            System.out.println("Shutdown finished")
        }
        // 하지만 rxJava 에서는 rxJava에서 제공하는 스케줄러를 사용하도록 권장한다.
    }
}