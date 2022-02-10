package org.fullsack101.bandit.context

/**
 * A context contains n [ContextAttribute] and must be immutable. For a single request to process
 * a event to update the model, or for a prediction a context needs to be provided.
 *
 */
data class Context(val contextAttributes: List<ContextAttribute>) {

    init {
        require(hasOnlyUniqueAttributes()) {
            val missingAttributeKeys = contextAttributes
                .map(ContextAttribute::key)
                .map(ContextAttributeKey::key)
                .joinToString(",")
            "Only unique ContextAttribute keys are allowed, received: $missingAttributeKeys"
        }
    }

    private fun hasOnlyUniqueAttributes() =
        contextAttributes.map(ContextAttribute::key).toSet().size == contextAttributes.map(ContextAttribute::key).toList().size
}
