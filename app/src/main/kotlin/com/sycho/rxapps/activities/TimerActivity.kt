package com.sycho.rxapps.activities

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import com.sycho.rxapps.R
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.*

/**
 * Description
 *
 * @author Cho Seong-yong
 * @since 2019.03.26
 */
class TimerActivity : Activity(), View.OnClickListener {

    private val DELAY = 0L
    private val PERIOD = 1000L
    private var count: Int = 0

    private var mTimer: Timer? = null

    private fun timerStart() {
        count = 0
        mTimer = Timer()
        mTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread { text_view.text = count++.toString() }
            }
        }, DELAY, PERIOD)
    }

    private fun timerStop() {
        mTimer?.cancel()
    }

    companion object {
        private const val MILLISINFUTURE: Long = 11 * 1000
        private const val COUNT_DOWN_INTERVAL: Long = 1000
    }

    private var mCountDownTimer: CountDownTimer? = null

    private fun initCountDownTask() {
        mCountDownTimer = object : CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            override fun onFinish() {
                text_view.text = "Finish ."
            }

            override fun onTick(millisUntilFinished: Long) {
                text_view.text = count--.toString()
            }
        }
    }

    private fun countDownTimerStart() {
        count = 10
        mCountDownTimer?.start()
    }

    private fun countDownTimerStop() {
        mCountDownTimer?.cancel()
    }

    private var mHandler: Handler? = null
    private val timer = object : Runnable {
        override fun run() {
            text_view.text = count++.toString()
            // handler에 인자로 전달할 경우 람다식 불가함.
            mHandler?.postDelayed(this, 1000)
        }
    }

    private fun initHandler() {
        mHandler = Handler()
    }

    private fun handlerStart() {
        count = 0
        mHandler?.postDelayed(timer, 0)
    }

    private fun handlerStop() {
        mHandler?.removeCallbacksAndMessages(null)
    }

    private fun stop() {
        timerStop()
        countDownTimerStop()
        handlerStop()
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.button -> {
                    stop()
                    timerStart()
                }
                R.id.button2 -> {
                    stop()
                    countDownTimerStart()
                }
                R.id.button3 -> {
                    stop()
                    handlerStart()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        button.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)

        initCountDownTask()
        initHandler()
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }
}