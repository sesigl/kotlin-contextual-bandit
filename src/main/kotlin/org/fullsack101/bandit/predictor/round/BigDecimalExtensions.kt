package org.fullsack101.bandit.predictor.round

import java.math.BigDecimal

fun BigDecimal.rounding(info: RoundingInfo): BigDecimal {
    return setScale(info.scale(), info.mode())
}
