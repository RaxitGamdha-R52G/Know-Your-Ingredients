package com.kyi.openfoodfactsapi.models.knowledgepanel

/**
 * Grade of the panel, depicting the level of impact the product has on the
 * corresponding topic.
 *
 * Client can choose to color code the panel depending on how good/bad the grade is.
 */
enum class Grade {
    A,
    B,
    C,
    D,
    E,
    UNKNOWN;
}