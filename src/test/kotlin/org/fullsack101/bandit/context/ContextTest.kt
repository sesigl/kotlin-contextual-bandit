package org.fullsack101.bandit.context

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.messageContains
import org.fullsack101.bandit.context.Context.Companion.JSON_ELEMENT_DELIMITER
import org.junit.jupiter.api.Test

class ContextTest {

    @Test
    fun `constructor - initialized with empty context`() {
        Context(contextAttributes = emptyList())
    }

    @Test
    fun `constructor - initialized single context`() {
        val contextAttributes = listOf(
            ContextAttribute(
                ContextAttributeKey("user_type"),
                ContextAttributeValue("premium")
            )
        )
        Context(contextAttributes)
    }

    @Test
    fun `constructor - duplicated context key - throws`() {
        val contextAttributes = listOf(
            ContextAttribute(
                ContextAttributeKey("user_type"), ContextAttributeValue("premium")
            ),
            ContextAttribute(
                ContextAttributeKey("user_type"), ContextAttributeValue("premium")
            ),
        )

        assertThat { Context(contextAttributes) }.isFailure().messageContains("user_type,user_type")
    }

    @Test
    fun `getUniqueIdentifier - returns comma separated json encoded list as string`() {
        val contextAttributes = listOf(
            ContextAttribute(
                ContextAttributeKey("user_type"),
                ContextAttributeValue("premium")
            ),

            ContextAttribute(
                ContextAttributeKey("time_of_day"),
                ContextAttributeValue("morning")
            )
        )
        val context = Context(contextAttributes)

        assertThat(context.getUniqueIdentifier().id).isEqualTo(
            """{"key":"user_type","value":"premium"}$JSON_ELEMENT_DELIMITER{"key":"time_of_day","value":"morning"}"""
        )
    }

    @Test
    fun `getUniqueIdentifier - returns an identifier that if used creates the same context`() {
        val contextAttributes = listOf(
            ContextAttribute(
                ContextAttributeKey("user_type"),
                ContextAttributeValue("premium")
            ),

            ContextAttribute(
                ContextAttributeKey("time_of_day"),
                ContextAttributeValue("morning")
            )
        )
        val context = Context(contextAttributes)

        val recreatedContext = Context.from(context.getUniqueIdentifier())

        assertThat(recreatedContext).isEqualTo(context)
    }
}
