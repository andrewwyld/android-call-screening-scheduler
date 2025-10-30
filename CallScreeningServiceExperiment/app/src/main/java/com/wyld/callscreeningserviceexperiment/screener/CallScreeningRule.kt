package com.wyld.callscreeningserviceexperiment.screener

import android.net.Uri

interface CallScreeningRule {
    fun permitCallFrom(handle: Uri): Boolean
}