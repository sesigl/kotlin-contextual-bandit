package org.fullsack101.bandit.predictor

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.fullsack101.bandit.context.Context
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import kotlin.random.Random

class BanditPredictorTest {

    companion object {
        private val ACTION_A = ModelAction("A")
        private val ACTION_B = ModelAction("B")
        private val TEST_CONTEXT = Context(contextAttributes = emptyList())
    }

    @Test
    fun `calculateRecommendedActionFor - returns exception if no actions are know`() {
        val banditPredictor = BanditPredictor()

        assertThrows<NoActionsDefinedException> {
            banditPredictor.calculateRecommendedActionFor(
                TEST_CONTEXT
            )
        }
    }

    @Test
    fun `calculateRecommendedActionFor - returns only action`() {
        val banditPredictor = BanditPredictor()
        banditPredictor.registerAction(ACTION_A)
        val recommendedAction = banditPredictor.calculateRecommendedActionFor(TEST_CONTEXT)
        assertThat(recommendedAction).isEqualTo(ACTION_A)
    }

    @Test
    fun `calculateRecommendedActionFor - returns action randomly if no data exists`() {

        val banditPredictor = BanditPredictor(random = Random(seed = 1))

        banditPredictor.registerAction(ACTION_A)
        banditPredictor.registerAction(ACTION_B)

        val recommendedActions: MutableSet<ModelAction> = mutableSetOf()

        repeat(2) {
            recommendedActions.add(banditPredictor.calculateRecommendedActionFor(TEST_CONTEXT))
        }

        assertThat(recommendedActions).isEqualTo(setOf(ACTION_A, ACTION_B))
    }

    @Test
    fun `calculateRecommendedActionFor - returns the action that received some data and never actions that received no data`() {

        val banditPredictor = BanditPredictor(random = Random(seed = 1))

        banditPredictor.registerAction(ACTION_A)
        banditPredictor.registerAction(ACTION_B)

        banditPredictor.train(TEST_CONTEXT, ACTION_A, ModelReward(BigDecimal.ONE))

        val recommendedActions: MutableSet<ModelAction> = mutableSetOf()

        repeat(2) {
            recommendedActions.add(banditPredictor.calculateRecommendedActionFor(TEST_CONTEXT))
        }

        assertThat(recommendedActions).isEqualTo(setOf(ACTION_A))
    }

    @Test
    fun `calculateRecommendedActionFor - if both actions have the same reward then a random max reward action is returned`() {

        val banditPredictor = BanditPredictor(random = Random(seed = 1))

        banditPredictor.registerAction(ACTION_A)
        banditPredictor.registerAction(ACTION_B)

        banditPredictor.train(TEST_CONTEXT, ACTION_A, ModelReward(BigDecimal.ONE))
        banditPredictor.train(TEST_CONTEXT, ACTION_B, ModelReward(BigDecimal.ONE))

        val recommendedActions: MutableSet<ModelAction> = mutableSetOf()

        repeat(2) {
            recommendedActions.add(banditPredictor.calculateRecommendedActionFor(TEST_CONTEXT))
        }

        assertThat(recommendedActions).isEqualTo(setOf(ACTION_A, ACTION_B))
    }

    @Test
    fun `calculateRecommendedActionFor - training adapts reward slowly`() {

        val banditPredictor = BanditPredictor(random = Random(seed = 1))

        banditPredictor.registerAction(ACTION_A)
        banditPredictor.registerAction(ACTION_B)

        banditPredictor.train(TEST_CONTEXT, ACTION_A, ModelReward(BigDecimal.ONE))
        banditPredictor.train(TEST_CONTEXT, ACTION_B, ModelReward(BigDecimal.ZERO))

        // although the reward is slightly bigger than the reward of A, A should still be served because
        // the new data entry should gradual increase the reward
        banditPredictor.train(TEST_CONTEXT, ACTION_B, ModelReward(BigDecimal(1.01)))

        repeat(1) {
            banditPredictor.train(TEST_CONTEXT, ACTION_B, ModelReward(BigDecimal(2)))
        }

        val recommendedAction =
            banditPredictor.calculateRecommendedActionFor(TEST_CONTEXT)

        assertThat(recommendedAction).isEqualTo(ACTION_A)
    }

    @Test
    fun `calculateRecommendedActionFor - training adapts reward eventually`() {

        val banditPredictor = BanditPredictor(random = Random(seed = 1))

        banditPredictor.registerAction(ACTION_A)
        banditPredictor.registerAction(ACTION_B)

        banditPredictor.train(TEST_CONTEXT, ACTION_A, ModelReward(BigDecimal.ONE))
        banditPredictor.train(TEST_CONTEXT, ACTION_B, ModelReward(BigDecimal.ZERO))

        repeat(10) {
            banditPredictor.train(TEST_CONTEXT, ACTION_B, ModelReward(BigDecimal(2)))
        }

        val recommendedAction =
            banditPredictor.calculateRecommendedActionFor(TEST_CONTEXT)

        assertThat(recommendedAction).isEqualTo(ACTION_B)
    }
}
