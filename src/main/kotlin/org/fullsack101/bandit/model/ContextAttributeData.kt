package org.fullsack101.bandit.model

import org.fullsack101.bandit.context.ContextAttributeKey
import java.util.concurrent.ConcurrentHashMap

class ContextAttributeData() {
    private val data: Map<ContextAttributeKey, ModelReward> = ConcurrentHashMap()
}
