package org.fullsack101.bandit.context

import assertk.assertThat
import assertk.assertions.isFailure
import assertk.assertions.messageContains
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
}
