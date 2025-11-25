package com.wyld.callscreeningserviceexperiment

import android.telecom.Call
import android.telecom.CallScreeningService
import android.telecom.Connection.*
import com.wyld.callscreeningserviceexperiment.screener.CallScreeningRule
import com.wyld.callscreeningserviceexperiment.screener.CallScreeningScheduler

class CallScreeningServiceExtension: CallScreeningService() {

    val MILLISECONDS_PER_DAY = 86400000

    val scheduler: CallScreeningScheduler = CallScreeningScheduler()

    override fun onScreenCall(callDetails: Call.Details) {
        if (callDetails.callDirection == Call.Details.DIRECTION_INCOMING) {
            when (callDetails.callerNumberVerificationStatus) {
                VERIFICATION_STATUS_NOT_VERIFIED, VERIFICATION_STATUS_FAILED -> disallowSilently(callDetails)
                VERIFICATION_STATUS_PASSED -> allowConditionally(callDetails)
                else -> disallow(callDetails)
            }
        }
    }

    private fun allowConditionally(callDetails: Call.Details) {
        val rule: CallScreeningRule = scheduler.getRuleAt(callDetails.connectTimeMillis % MILLISECONDS_PER_DAY)
        respondToCall(
            callDetails,
            CallResponse.Builder()
                .setDisallowCall(!rule.permitCallFrom(callDetails.handle))
                .build()
        )
    }

    private fun disallow(callDetails: Call.Details) {
        respondToCall(
            callDetails,
            CallResponse.Builder()
                .setDisallowCall(true)
                .setSkipNotification(false)
                .build()
        )
    }

    private fun disallowSilently(callDetails: Call.Details) {
        respondToCall(
            callDetails,
            CallResponse.Builder()
                .setDisallowCall(true)
                .setSkipNotification(true)
                .build()
        )
    }

}