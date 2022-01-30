package org.fullsack101.bandit.model

import org.fullsack101.bandit.context.ContextAttributeId
import kotlin.collections.HashMap

class ContextAttributeData() {
    private val data: Map<ContextAttributeId, List<Float>> = HashMap()
}
