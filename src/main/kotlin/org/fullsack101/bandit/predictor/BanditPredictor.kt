package org.fullsack101.bandit.predictor

import org.fullsack101.bandit.context.Context

/**
 * Stores the current model that can be created, updated, saved and stored.
 *
 * A predictor is used to train predictions for:
 * - a given context with many context attributes like for example a user segment,
 * - a target that is one possible action out of many.
 *
 * The required recommendation for a given context is an action. Performance is measured
 * using a so called reward. Based on the best performing reward, the best action is recommended.
 *
 */
data class BanditPredictor(val contextAttributeData: ContextAttributeData) {
    fun train(context: Context, target: ModelAction, reward: ModelReward) {
        TODO("Not yet implemented")
    }

    fun calculateRecommendedActionFor(context: Context): ModelAction {
        TODO("Not yet implemented")
    }
}
