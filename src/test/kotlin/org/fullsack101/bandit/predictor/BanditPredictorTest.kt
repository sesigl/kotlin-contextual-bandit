package org.fullsack101.bandit.predictor

import org.fullsack101.bandit.context.Context
import org.junit.jupiter.api.Test

private val TEST_CONTEXT = Context(contextAttributes = emptyList());

class BanditPredictorTest {

    @Test
    fun `calculateRecommendedActionFor - returns exception if no actions are know`() {
        val banditPredictor = BanditPredictor(ContextAttributeData())
        val calculateRecommendedActionFor =
            banditPredictor.calculateRecommendedActionFor(TEST_CONTEXT)
    }
}
