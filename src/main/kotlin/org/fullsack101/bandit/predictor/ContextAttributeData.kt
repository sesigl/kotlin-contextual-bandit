package org.fullsack101.bandit.predictor

import org.fullsack101.bandit.context.Context
import org.fullsack101.bandit.context.ContextIdentifier
import org.fullsack101.bandit.predictor.round.Rounding
import org.fullsack101.bandit.predictor.round.rounding
import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap

class ContextAttributeData {
    private val data: ConcurrentHashMap<ContextIdentifier, ModelReward> = ConcurrentHashMap()

    fun store(context: Context, action: ModelAction, reward: ModelReward) {
        val contextWithAction = context.cloneAndAddAction(action)
        data.putIfAbsent(contextWithAction.getUniqueIdentifier(), reward)

        val existingReward = data.getValue(contextWithAction.getUniqueIdentifier())

        val updatedReward =
            (existingReward.reward * BigDecimal("0.9")) + (reward.reward * BigDecimal("0.1"))
                .rounding(Rounding.REWARD)

        data[contextWithAction.getUniqueIdentifier()] = ModelReward(updatedReward)
    }

    fun getRewardFor(context: Context, action: ModelAction): ModelReward? {
        val contextWithAction = context.cloneAndAddAction(action)
        val modelReward = data[contextWithAction.getUniqueIdentifier()]
        return modelReward
    }

    fun isEmpty(): Boolean {
        return data.isEmpty()
    }
}
