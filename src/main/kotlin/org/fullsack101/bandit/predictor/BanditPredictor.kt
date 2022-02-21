package org.fullsack101.bandit.predictor

import org.fullsack101.bandit.context.Context
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

/**
 * Stores the current model that can be created, updated, saved and stored.
 *
 * A predictor is used to train predictions for:
 * - a given context with many context attributes like for example a user segment,
 * - a target that is one possible action out of many.
 *
 * The required recommendation for a given context is an action. Performance is measured
 * using a so-called reward. Based on the best performing reward, the best action is recommended.
 *
 * @param random can be set to make execution deterministic, like for example in tests
 *
 */
class BanditPredictor(private val random: Random = Random.Default) {

    private val contextAttributeData: ContextAttributeData = ContextAttributeData()
    private val knownActions: MutableSet<ModelAction> = ConcurrentHashMap.newKeySet()

    fun train(context: Context, target: ModelAction, reward: ModelReward) {
        contextAttributeData.store(context, target, reward)
    }

    fun calculateRecommendedActionFor(context: Context): ModelAction {

        if (contextAttributeData.isEmpty()) {
            return knownActions
                .asSequence()
                .shuffled(random)
                .find { true } ?: throw NoActionsDefinedException(context)
        }

        // values are filtered by != null and will never be null
        @Suppress("UNCHECKED_CAST")
        val actionsWithReward = knownActions
            .associateBy({ it }, { action -> contextAttributeData.getRewardFor(context, action) })
            .filterValues { reward -> reward != null } as Map<ModelAction, ModelReward>

        val maxReward = actionsWithReward
            .maxOf { entry -> entry.value.reward }

        val maxRewardActions = actionsWithReward.filterValues { value -> value.reward == maxReward }

        val randomAction = maxRewardActions
            .asSequence()
            .shuffled(random)
            .find { true } ?: throw IllegalStateException("Max reward actions empty")

        return randomAction.key
    }

    fun registerAction(action: ModelAction) {
        knownActions.add(action)
    }
}
