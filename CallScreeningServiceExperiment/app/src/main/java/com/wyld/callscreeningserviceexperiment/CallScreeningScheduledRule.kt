package com.wyld.callscreeningserviceexperiment

abstract class CallScreeningScheduledRule(
    var start: Long,
    var end: Long
): CallScreeningRule {
    fun startsBefore(otherRule: CallScreeningScheduledRule): Boolean {
        return this.start < otherRule.start
    }

    fun endsBeforeStartOf(otherRule: CallScreeningScheduledRule): Boolean {
        return this.end < otherRule.start
    }

    fun entirelyWithin(otherRule: CallScreeningScheduledRule): Boolean {
        return this.end < otherRule.end && otherRule.startsBefore(this)
    }

    fun constrainEndToStartOf(otherRule: CallScreeningScheduledRule) {
        this.end.coerceAtMost(otherRule.start)
    }

    fun constrainStartToEndOf(otherRule: CallScreeningScheduledRule) {
        this.start.coerceAtLeast(otherRule.end)
    }

    fun validAtTime(time: Long): Boolean {
        return this.start < time && time < this.end
    }

    fun after(time: Long): Boolean {
        return this.end < time
    }

}