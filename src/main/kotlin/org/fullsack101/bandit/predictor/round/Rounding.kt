package org.fullsack101.bandit.predictor.round

import java.math.RoundingMode

enum class Rounding(private val scale: Int, private val roundingMode: RoundingMode) : RoundingInfo {
    REWARD(2, RoundingMode.HALF_UP);

    override fun scale(): Int {
        return scale
    }

    override fun mode(): RoundingMode {
        return roundingMode
    }
}
