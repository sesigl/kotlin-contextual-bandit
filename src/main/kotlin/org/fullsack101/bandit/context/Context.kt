package org.fullsack101.bandit.context

/**
 * A context contains n [ContextAttribute] and must be immutable. For a single request to process
 * a event to update the model, or for a prediction a context needs to be provided.
 *
 */
data class Context(val contextAttributes: List<ContextAttribute>) {

    init {
        require(hasOnlyUniqueAttributes())
    }

    private fun hasOnlyUniqueAttributes() =
        contextAttributes.map(ContextAttribute::id).toSet().size == contextAttributes.map(ContextAttribute::id).toList().size

}
