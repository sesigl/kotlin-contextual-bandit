package org.fullsack101.bandit.predictor

import org.fullsack101.bandit.context.Context

class NoActionsDefinedException(context: Context) :
    RuntimeException("No actions defined for context '${context.getUniqueIdentifier()}'. Some data needs to be stored before recommendations can be requested.")
