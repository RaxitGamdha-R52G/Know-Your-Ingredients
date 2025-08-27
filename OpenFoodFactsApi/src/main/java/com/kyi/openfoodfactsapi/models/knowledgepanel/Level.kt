package com.kyi.openfoodfactsapi.models.knowledgepanel

/**
 * Level of information conveyed by this KnowledgePanel.
 *
 * Client may choose to display the panel based on the level.
 */
enum class Level {
    TRIVIA,
    INFO,
    HELPFUL,
    WARNING,
    ALERT,
    UNKNOWN;
}