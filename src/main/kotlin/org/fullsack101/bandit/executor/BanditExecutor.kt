package org.fullsack101.bandit.executor

import org.fullsack101.bandit.context.Context
import org.fullsack101.bandit.event.RewardEvent
import org.fullsack101.bandit.model.BanditModel
import org.fullsack101.bandit.model.ModelAction

class BanditExecutor(private val banditModel: BanditModel, private val knownActions: Set<String>) {

    fun process(rewardEvent: RewardEvent) {
        banditModel.train(
            context = rewardEvent.context,
            target = rewardEvent.action,
            reward = rewardEvent.reward
        )
    }

    fun calculateTargetToServeFor(context: Context): ModelAction {

        // TODO: decide of exploration or exploitation

        // if exploitation, get the best performing target

        return banditModel.predict(context = context)
    }
}
