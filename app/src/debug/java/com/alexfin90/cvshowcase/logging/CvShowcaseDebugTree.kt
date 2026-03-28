package com.alexfin90.cvshowcase.logging

import timber.log.Timber

object CvShowcaseDebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return "${super.createStackElementTag(element)}:${element.lineNumber}"
    }
}
