package com.kyi.openfoodfactsapi.models.knowledgepanel

/**
 * Evaluation of the panel, depicting whether the content of the panel is
 * good/bad/neutral for the topic to which the panel applies.
 */
enum class Evaluation {
    GOOD,
    NEUTRAL,
    AVERAGE,
    BAD,
    UNKNOWN;
}