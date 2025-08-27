package com.kyi.openfoodfactsapi.models.robotoff

import com.kyi.openfoodfactsapi.sources.OffTagged


enum class RobotoffQuestionOrder(override val offTag: String) : OffTagged {
    CONFIDENCE("confidence"),
    RANDOM("random"),
    POPULARITY("popularity");
}