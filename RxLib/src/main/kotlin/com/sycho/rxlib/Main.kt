package com.sycho.rxlib

import com.sycho.rxlib.sample.ch2.*
import com.sycho.rxlib.sample.ch2.observable.*
import com.sycho.rxlib.sample.ch2.subject.AsyncSubjectExample
import com.sycho.rxlib.sample.ch2.subject.BehaviorSubjectExample
import com.sycho.rxlib.sample.ch2.subject.PublishSubjectExample
import com.sycho.rxlib.sample.ch2.subject.ReplaySubjectExample
import com.sycho.rxlib.sample.ch3.*
import com.sycho.rxlib.sample.ch4.*
import com.sycho.rxlib.sample.ch5.*
import com.sycho.rxlib.sample.common.Utils

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.02.26
 */
object Main {

    @JvmStatic
    fun main(args: Array<String>) {
//        launchCh2To1()
//        launchCh2To2()
//        launchCh2To5()
//        launchCh2To6()
//        launchCh3()
//        launchCh3To3()
//        launchCh3To4()
//        launchCh3To5()
//        launchCh4To1()
//        launchCh4To1Ex()
//        launchCh4To2()
//        launchCh4To3()
//        launchCh4To4()
//        launchCh4To5()
        launchCh5To1()
    }

    private fun launchCh2To1() {
        val firstExample = FirstExample()
        firstExample.emit1_2()
        firstExample.emit2_1()

        val observableNotifications = ObservableNotifications()
        observableNotifications.emit()

        val observableCreateExample = ObservableCreateExample()
        observableCreateExample.basic()

        val observableFromArray = ObservableFromArray()
        observableFromArray.intArray()

        val observableFromIterable = ObservableFromIterable()
        observableFromIterable.listExample()
        observableFromIterable.setExample()
        observableFromIterable.blockingQueueExample()

        val observableFromCallable = ObservableFromCallable()
        observableFromCallable.basic()

        val observableFromFuture = ObservableFromFuture()
        observableFromFuture.basic()

        val observableFromPublisher = ObservableFromPublisher()
        observableFromPublisher.basic()
    }

    private fun launchCh2To2() {
        val singleExample = SingleExample()
        singleExample.just()
        singleExample.fromObservable()
    }

    private fun launchCh2To5() {
        val asyncSubjectExample = AsyncSubjectExample()
        asyncSubjectExample.marbleDiagramAsyncSubject()
        asyncSubjectExample.asSubscriber()
        asyncSubjectExample.afterComplete()
        Utils.exampleComplete()
        val behaviorSubjectExample = BehaviorSubjectExample()
        behaviorSubjectExample.marbleDiagramBehaviorSubject()
        Utils.exampleComplete()
        val publishSubjectExample = PublishSubjectExample()
        publishSubjectExample.marbleDiagramPublishSubjectExample()
        Utils.exampleComplete()
        val replaySubjectExample = ReplaySubjectExample()
        replaySubjectExample.marbleDiagramReplaySubject()
    }

    private fun launchCh2To6() {
        val connectableObservableExample =ConnectableObservableExample()
        connectableObservableExample.marbleDiagram()
    }

    private fun launchCh3() {
        val mapExample = MapExample()
        mapExample.marbleDiagram()
        Utils.exampleComplete()
        mapExample.mappingType()
        Utils.exampleComplete()
        FlatMapExample().marbleDiagram()
        Utils.exampleComplete()
        val gugudan = Gugudan()
//        gugudan.plainKotlin()
//        Utils.exampleComplete()
//        gugudan.plainRx1()
//        Utils.exampleComplete()
//        gugudan.plainRx2()
//        Utils.exampleComplete()
//        gugudan.plainRx3()
//        Utils.exampleComplete()
        gugudan.usingResultSelector()
    }

    private fun launchCh3To3() {
        val filterExample = FilterExample()
        filterExample.marbleDiagram()
        Utils.exampleComplete()
        filterExample.evenNumbers()
        Utils.exampleComplete()
        filterExample.otherFilters()
    }

    private fun launchCh3To4() {
        val reduceExample = ReduceExample()
        reduceExample.marbleDiagram()
    }

    private fun launchCh3To5() {
        val queryTvSales = QueryTvSales()
        queryTvSales.run()
    }

    private fun launchCh4To1() {
        val intervalExample = IntervalExample()
        intervalExample.printNumbers()
        Utils.exampleComplete()
        intervalExample.noInitialDelay()
        Utils.exampleComplete()
        val timerExample = TimerExample()
        timerExample.showTime()
        Utils.exampleComplete()
        val rangeExample = RangeExample()
        rangeExample.forLoop()
        Utils.exampleComplete()
        val intervalRangeExample = IntervalRangeExample()
        intervalRangeExample.printNumbers()
        Utils.exampleComplete()
        intervalRangeExample.makeWithInterval()
        Utils.exampleComplete()
        val deferExample = DeferExample()
        deferExample.marbleDiagram()
        Utils.exampleComplete()
        deferExample.notDeferred()
        Utils.exampleComplete()
        val repeatExample = RepeatExample()
        repeatExample.marbleDiagram()
    }

    private fun launchCh4To1Ex() {
//        RepeatExample().heartbeatV1()
        RepeatExample().heartbeatV1_1()
    }

    private fun launchCh4To2() {
//        val concatMapExample = ConcatMapExample()
//        concatMapExample.marbleDiagram()
//        concatMapExample.interleaving()
//        val switchMapExample = SwitchMapExample()
//        switchMapExample.marbleDiagram()
//        switchMapExample.usingDoOnNext()
//        val groupByExample = GroupByExample()
//        groupByExample.marbleDiagram()
//        groupByExample.filterBallGroup()
        val scanExample = ScanExample()
        scanExample.marbleDiagram()
    }

    private fun launchCh4To3() {
//        val zipExample = ZipExample()
//        zipExample.marbleDiagram()
//        Utils.exampleComplete()
//        zipExample.zipNumbers()
//        zipExample.zipWithNumbers()
//        Utils.exampleComplete()
//        zipExample.zipInterval()
//        val electricBills = ElectricBills()
//        electricBills.electricBillV1()
//        Utils.exampleComplete()
//        electricBills.electricBillV2()
//        val combineLatestExample = CombineLatestExample()
//        combineLatestExample.marbleDiagram()
//        val reactiveSum = ReactiveSum()
//        reactiveSum.run()
//        val mergeExample = MergeExample()
//        mergeExample.marbleDiagram()
        val concatExample = ConcatExample()
        concatExample.marbleDiagram()
    }

    private fun launchCh4To4() {
//        AmbExample().marbleDiagram()
//        TakeUntilExample().marbleDiagram()
//        SkipUntilExample().marbleDiagram()
        AllExample().marbleDiagram()
    }

    private fun launchCh4To5() {
//        MathFunctionsExample().marbleDiagram()
//        DelayExample().marbleDiagram()
        TimeIntervalExample().marbleDiagram()
    }

    private fun launchCh5To1() {
//        HelloRxJava2V2().emit()
//        val flipExample = FlipExample()
//        flipExample.marbleDiagram()
//        flipExample.observedOnRemove()
//        NewThreadSchedulerExample().basic()
//        ComputationSchedulerExample().basic()
//        IoSchedulerExample().basic()
//        TrampolineSchedulerExample().basic()
//        SingleSchedulerExample().basic()
//        ExecutorSchedulerExample().basic()
//        HttpGetExample().run()
//        CallbackHell().run()
        val callbackHeaven = CallbackHeaven()
//        callbackHeaven.usingConcat()
        callbackHeaven.usingZip()
    }
}