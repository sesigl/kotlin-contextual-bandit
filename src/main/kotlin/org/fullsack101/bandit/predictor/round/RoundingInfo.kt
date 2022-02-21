package org.fullsack101.bandit.predictor.round

import java.math.RoundingMode

interface RoundingInfo {
    fun scale(): Int
    fun mode(): RoundingMode
}
