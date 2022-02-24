package org.fullsack101.bandit.executor

import org.fullsack101.bandit.context.Context
import org.fullsack101.bandit.event.RewardEvent
import org.fullsack101.bandit.explorationexploitation.ExplorationExploitationGreedySelector
import org.fullsack101.bandit.explorationexploitation.PredictionType
import org.fullsack101.bandit.predictor.BanditPredictor
import org.fullsack101.bandit.predictor.ModelAction

class BanditExecutor(
    private val banditPredictor: BanditPredictor,
    private val explorationExploitationSelector: ExplorationExploitationGreedySelector,
) {

    fun process(rewardEvent: RewardEvent) {
        banditPredictor.train(
            context = rewardEvent.context,
            target = rewardEvent.action,
            reward = rewardEvent.reward
        )
    }

    fun calculateTargetToServeFor(context: Context): ModelAction {

        val nextPredictionType =
            explorationExploitationSelector.calculateNextPredictionType()

        return if (nextPredictionType == PredictionType.EXPLORATION) {
            banditPredictor.calculateRandomActionFor(context)
        } else {
            banditPredictor.calculateRecommendedActionFor(context = context)
        }
    }

    fun register(modelAction: ModelAction) {
        banditPredictor.registerAction(modelAction)
    }
}
