package org.fullsack101.bandit.model

import org.fullsack101.bandit.context.Context

/**
 * Stores the current model that can be created, updated, saved and stored.
 */
data class BanditModel(val contextAttributeData: ContextAttributeData) {
    fun train(context: Context, target: ModelAction, reward: ModelReward) {
        TODO("Not yet implemented")
    }

    fun predict(context: Context): ModelAction {
        TODO("Not yet implemented")
    }
}
