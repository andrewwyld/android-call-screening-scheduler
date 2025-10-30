package com.wyld.callscreeningserviceexperiment.screener

import android.net.Uri

class CallScreeningScheduledAllowRule(start: Long, end: Long): CallScreeningScheduledRule(start, end) {
    override fun permitCallFrom(handle: Uri): Boolean {
        TODO("Not yet implemented")
    }

}