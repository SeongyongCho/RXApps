package com.sycho.rxlib.sample.common

/**
 * Description
 *
 * @author 파인원_조성용선임
 * @since 2019.02.28
 */
class Log {
    companion object {
        fun d(tag: String, any: Any) {
            System.out.println(getThreadName() + " | " + tag + " | debug = " + any)
        }

        fun e(tag: String, any: Any) {
            System.out.println(getThreadName() + " | " + tag + " | error = " + any)
        }

        fun i(tag: String, any: Any) {
            System.out.println(getThreadName() + " | " + tag + " | value = " + any)
        }

        fun v(any: Any) {
            System.out.println(getThreadName() + " | = " + any)
        }

        fun d(any: Any?) {
            System.out.println(getThreadName() + " | debug = " + any)
        }

        fun e(any: Any?) {
            System.out.println(getThreadName() + " | error = " + any)
        }

        fun i(any: Any?) {
            System.out.println(getThreadName() + " | value = " + any)
        }

        fun it(any: Any) {
            val time = System.currentTimeMillis() - Utils.startTime
            System.out.println(getThreadName() + " | " + time + " | " + "value = " + any)
        }

        fun dt(any: Any) {
            val time = System.currentTimeMillis() - Utils.startTime
            System.out.println(getThreadName() + " | " + time + " | " + "debug = " + any)
        }

        fun et(any: Any?) {
            val time = System.currentTimeMillis() - Utils.startTime
            System.out.println(getThreadName() + " | " + time + " | " + "error >> " + any)
        }

        fun onNextT(any: Any) {
            val time = System.currentTimeMillis() - Utils.startTime
            System.out.println(getThreadName() + " | " + time + " | " + "onNext >> " + any)
        }

        fun onCompleteT() {
            val time = System.currentTimeMillis() - Utils.startTime
            System.out.println(getThreadName() + " | " + time + " | " + "onComplete")
        }

        fun getThreadName() : String {
            var threadName = Thread.currentThread().name
            if (threadName.length > 30) {
                threadName = threadName.substring(0, 30)
            }
            return threadName
        }

        fun getMethodName() : String {
            val ste = Thread.currentThread().stackTrace
            return ste[ste.size - 3].methodName
        }
    }
}