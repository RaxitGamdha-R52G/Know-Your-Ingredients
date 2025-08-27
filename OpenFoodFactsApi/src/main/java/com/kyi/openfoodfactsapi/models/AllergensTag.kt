package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged

/**
 * Main allergens.
 */
enum class AllergensTag(override val offTag: String) : OffTagged {
    GLUTEN("en:gluten"),
    MILK("en:milk"),
    EGGS("en:eggs"),
    NUTS("en:nuts"),
    PEANUTS("en:peanuts"),
    SESAME_SEEDS("en:sesame-seeds"),
    SOYBEANS("en:soybeans"),
    CELERY("en:celery"),
    MUSTARD("en:mustard"),
    LUPIN("en:lupin"),
    FISH("en:fish"),
    CRUSTACEANS("en:crustaceans"),
    MOLLUSCS("en:molluscs"),
    SULPHUR_DIOXIDE_AND_SULPHITES("en:sulphur-dioxide-and-sulphites");
}
