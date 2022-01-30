package org.fullsack101.bandit.event

/**
 * Event that is processed to update the algorithm.
 *
 * For scalability, it might make sense to calculate an average reward in combination with a
 * weight for training in using an ETL service,like GCP dataflow or AWS data pipeline.
 *
 * For simplicity, we just go with a single event for now. A BatchRewardEvent needs to be implemented.
 */
data class RewardEvent(val reward: Float)
