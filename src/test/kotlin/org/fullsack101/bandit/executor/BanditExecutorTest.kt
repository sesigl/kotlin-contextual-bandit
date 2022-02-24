package org.fullsack101.bandit.executor

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.fullsack101.bandit.context.Context
import org.fullsack101.bandit.event.RewardEvent
import org.fullsack101.bandit.explorationexploitation.ExplorationExploitationGreedySelector
import org.fullsack101.bandit.explorationexploitation.Percentage
import org.fullsack101.bandit.predictor.BanditPredictor
import org.fullsack101.bandit.predictor.ModelAction
import org.fullsack101.bandit.predictor.ModelReward
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.math.BigDecimal

private val TEST_REWARD = ModelReward(BigDecimal.ONE)
private val TEST_ACTION = ModelAction("TEST_ACTION")

class BanditExecutorTest {

    @Test
    @Disabled
    internal fun name() {
        val banditExecutor = BanditExecutor(
            banditPredictor = BanditPredictor(),
            explorationExploitationSelector = ExplorationExploitationGreedySelector(
                explorationPercentage = Percentage(30)
            )
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
