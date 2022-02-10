package org.fullsack101.bandit.executor

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.fullsack101.bandit.context.Context
import org.fullsack101.bandit.event.RewardEvent
import org.fullsack101.bandit.model.BanditModel
import org.fullsack101.bandit.model.ContextAttributeData
import org.fullsack101.bandit.model.ModelAction
import org.fullsack101.bandit.model.ModelReward
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

private val TEST_REWARD = ModelReward(2f)
private val TEST_ACTION = ModelAction("TEST_ACTION")

class BanditExecutorTest {

    @Test
    @Disabled
    internal fun name() {
        val banditExecutor = BanditExecutor(
            banditModel = BanditModel(contextAttributeData = ContextAttributeData()),
            knownActions = emptySet()
        )

        val context = Context(contextAttributes = emptyList())
        val rewardEvent = RewardEvent(
            reward = TEST_REWARD,
            context = context,
            action = TEST_ACTION
        )

        banditExecutor.process(rewardEvent)

        val predictedAction = banditExecutor.calculateTargetToServeFor(context)

        assertThat(predictedAction).isEqualTo(TEST_ACTION)
    }
}
