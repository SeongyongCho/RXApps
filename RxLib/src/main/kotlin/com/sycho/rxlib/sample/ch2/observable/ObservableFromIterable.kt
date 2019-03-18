package com.sycho.rxlib.sample.ch2.observable

import com.sycho.rxlib.sample.common.Order
import io.reactivex.Observable
import java.util.concurrent.ArrayBlockingQueue

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.02.27
 */
class ObservableFromIterable {
    fun listExample() {
        val names = ArrayList<String>()
        names.add("Jerry")
        names.add("William")
        names.add("Bob")

        val source = Observable.fromIterable(names)
        source.subscribe{System.out.println("$it")}
    }

    fun setExample() {
        val cities = HashSet<String>()
        cities.add("Seoul")
        cities.add("London")
        cities.add("Paris")

        val source = Observable.fromIterable(cities)
        source.subscribe{System.out.println("$it")}
    }

    fun blockingQueueExample() {
        // BlockingQueue가 뭐임?
        // 멀티스레드 상의 생산자 소비자 패턴 구현 방식 중 하나이다
        // 생산자 소비자 패턴은?
        // 생산자가 추가해 놓으면 소비자는 이를 추출한다 이 때 소비자는 생산자가 입력할 때까지 대기할 수도 있다
        // ArrayBlockingQueue는?
        // capacity를 지정해둬야 하고 꽉 차면 추가를 block, 비었을 때는 추출을 block한다.
        // 그래서 이게 왜 필요함?
        // 생산과 소비가 따로 있는 것이 곳 생산자 소비자 패턴인데
        // 이것이 싱글스레드에서 이루어질 경우 실시간 반응은 하지만 생산이 늦어지면 그만큼 소비도 오래 기다려야 한다.
        // 멀티스레드에서 이루어 진다면 생산 스레드 따로 소비 스레드 따로에 전달 매개체가 생기므로
        // synchronized wait 등으로 직접 제어하지 않아도 되므로 thread safe 하다
        val orderQueue = ArrayBlockingQueue<Order>(100)
        orderQueue.add(Order("ORD-1", "고객1", 30))
        orderQueue.add(Order("ORD-2", "고객2", 17))
        orderQueue.add(Order("ORD-3", "고객3", 48))

        val source = Observable.fromIterable(orderQueue)
        source.subscribe{order -> System.out.println("주문번호 : " + order.id + " 성함 : " + order.name + " 연령 : " + order.age)}
    }
}