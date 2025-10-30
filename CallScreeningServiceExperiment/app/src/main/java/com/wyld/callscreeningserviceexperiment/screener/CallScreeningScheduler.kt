package com.wyld.callscreeningserviceexperiment.screener

import android.net.Uri
import java.util.LinkedList

class CallScreeningScheduler {

    val rootList: LinkedList<CallScreeningScheduledRule> = LinkedList<CallScreeningScheduledRule>()
    val defaultRule: CallScreeningRule = object: CallScreeningRule {
        override fun permitCallFrom(handle: Uri): Boolean {
            return true
        }
    }

    fun addRuleAt(newRule: CallScreeningScheduledRule) {
        // create a list iterator and an insertion index
        val iterator = rootList.iterator()
        var index = 0
        var previousRule: CallScreeningScheduledRule? = null

        while (iterator.hasNext()) {
            var rule = iterator.next();

            // if the new rule starts before the current one, this is the insertion point
            if (newRule.startsBefore(rule)) {

                // if the previous rule overlaps the new one, shorten it
                previousRule?.constrainEndToStartOf(newRule)

                // remove any rules that the new rule entirely engulfs
                while (rule.entirelyWithin(newRule)) {
                    iterator.remove();
                    rule = iterator.next()
                }

                // if the next rule overlaps the new one, shorten it
                rule.constrainStartToEndOf(newRule)

                break
            }
            // if we haven't found the insertion point, increment the index
            ++index
            previousRule = rule
        }

        // insert the new rule at the insertion index
        rootList.add(index, newRule)
    }

    fun getRuleAt(timeOfDay: Long): CallScreeningRule {
        val iterator = rootList.iterator();

        while (iterator.hasNext()) {
            val rule = iterator.next();

            if (rule.validAtTime(timeOfDay)) return rule

            if (rule.after(timeOfDay)) break
        }

        return defaultRule
    }

    class Builder() {
        private var callSchedule: CallScreeningScheduler = CallScreeningScheduler()

        fun addRuleAt(newRule: CallScreeningScheduledRule) = callSchedule.addRuleAt(newRule)

        fun build() = callSchedule;
    }
}