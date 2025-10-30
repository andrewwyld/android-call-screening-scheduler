package com.wyld.callscreeningserviceexperiment.screener

import android.content.UriMatcher
import android.net.Uri

class CallScreeningScheduledAllowRule(start: Long, end: Long, val matcher: UriMatcher): CallScreeningScheduledRule(start, end) {

    override fun permitCallFrom(handle: Uri): Boolean {
        return matcher.match(handle) != UriMatcher.NO_MATCH
    }

}