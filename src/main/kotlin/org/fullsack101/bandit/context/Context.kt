package org.fullsack101.bandit.context

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.fullsack101.bandit.predictor.ModelAction

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
                .joinToString(LIST_DELIMITER)
            "Only unique ContextAttribute keys are allowed, received: $missingAttributeKeys"
        }
    }

    companion object {

        private val jacksonObjectMapper = jacksonObjectMapper()
        private const val LIST_DELIMITER = ","
        const val JSON_ELEMENT_DELIMITER = "|||"

        fun from(contextIdentifier: ContextIdentifier): Context {
            val contextAttributes = contextIdentifier.id.split(JSON_ELEMENT_DELIMITER)
                .map {
                    val contextAttribute: ContextAttribute =
                        jacksonObjectMapper.readValue(it, ContextAttribute::class.java)
                    contextAttribute
                }
                .toList()

            return Context(contextAttributes)
        }
    }

    private fun hasOnlyUniqueAttributes() =
        contextAttributes.map(ContextAttribute::key).toSet().size == contextAttributes.map(
            ContextAttribute::key
        ).toList().size

    fun getUniqueIdentifier(): ContextIdentifier {
        val contextAsString = contextAttributes
            .map { jacksonObjectMapper.writeValueAsString(it) }
            .joinToString(JSON_ELEMENT_DELIMITER)

        return ContextIdentifier(contextAsString)
    }

    fun cloneAndAddAction(action: ModelAction): Context {
        return Context(
            listOf(
                *this.contextAttributes.toTypedArray(),
                ContextAttribute(
                    ContextAttributeKey("action"),
                    ContextAttributeValue(action.action)
                )
            )
        )
    }
}
