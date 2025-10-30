package com.wyld.callscreeningserviceexperiment

interface CallScreeningRule {
    fun permitCallFrom(): Boolean
}