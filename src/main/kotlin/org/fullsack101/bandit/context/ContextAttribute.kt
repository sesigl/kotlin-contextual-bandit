package org.fullsack101.bandit.context

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Defines a unique context attribute for a context.
 */

data class ContextAttribute(val key: ContextAttributeKey, val value: ContextAttributeValue) {
    companion object {
        @JvmStatic
        @JsonCreator
        fun create(key: String, value: String) =
            ContextAttribute(ContextAttributeKey(key), ContextAttributeValue(value))
    }
}
