package com.wyld.callscreeningserviceexperiment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wyld.callscreeningserviceexperiment.databinding.ActivityCallScreeningSchedulerBinding

class CallScreeningSchedulerView : AppCompatActivity() {

    private lateinit var binding: ActivityCallScreeningSchedulerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCallScreeningSchedulerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}