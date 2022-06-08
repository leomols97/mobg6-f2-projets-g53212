package com.technical.myapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.lemenestrel.R

class MainActivity : AppCompatActivity() {
//    private var CurrentProgress = 0
//    private var progressBar: ProgressBar? = null
//    private var StartProgress: Button? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.progress_bar)
//        progressBar = findViewById(R.id.progressBar)
//        StartProgress = findViewById(R.id.startProgess)
//        val countDownTimer: CountDownTimer = object : CountDownTimer(11 * 1000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                CurrentProgress = CurrentProgress + 10
//                progressBar.setProgress(CurrentProgress)
//                progressBar.setMax(100)
//            }
//
//            override fun onFinish() {}
//        }
//        StartProgress.setOnClickListener(View.OnClickListener { countDownTimer.start() })
//    }
}