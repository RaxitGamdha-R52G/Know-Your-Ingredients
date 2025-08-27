package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged


enum class Nutrient(override val offTag: String, val typicalUnit: Unit) : OffTagged {
    SALT("salt", Unit.G),
    SODIUM("sodium", Unit.G),
    FIBER("fiber", Unit.G),
    SUGARS("sugars", Unit.G),
    ADDED_SUGARS("added-sugars", Unit.G),
    FAT("fat", Unit.G),
    SATURATED_FAT("saturated-fat", Unit.G),
    PROTEINS("proteins", Unit.G),
    ENERGY_KCAL("energy-kcal", Unit.KCAL),
    ENERGY_KJ("energy-kj", Unit.KJ),
    CARBOHYDRATES("carbohydrates", Unit.G),
    CAFFEINE("caffeine", Unit.G),
    CALCIUM("calcium", Unit.MILLI_G),
    IRON("iron", Unit.MILLI_G),
    VITAMIN_C("vitamin-c", Unit.MILLI_G),
    MAGNESIUM("magnesium", Unit.MILLI_G),
    PHOSPHORUS("phosphorus", Unit.MILLI_G),
    POTASSIUM("potassium", Unit.MILLI_G),
    ZINC("zinc", Unit.MILLI_G),
    COPPER("copper", Unit.MILLI_G),
    SELENIUM("selenium", Unit.MICRO_G),
    VITAMIN_A("vitamin-a", Unit.MICRO_G),
    VITAMIN_E("vitamin-e", Unit.MILLI_G),
    VITAMIN_D("vitamin-d", Unit.MICRO_G),
    VITAMIN_B1("vitamin-b1", Unit.MILLI_G),
    VITAMIN_B2("vitamin-b2", Unit.MILLI_G),
    VITAMIN_PP("vitamin-pp", Unit.MILLI_G),
    VITAMIN_B6("vitamin-b6", Unit.MILLI_G),
    VITAMIN_B12("vitamin-b12", Unit.MICRO_G),
    VITAMIN_B9("vitamin-b9", Unit.MICRO_G),
    VITAMIN_K("vitamin-k", Unit.MICRO_G),
    CHOLESTEROL("cholesterol", Unit.MILLI_G),
    BUTYRIC_ACID("butyric-acid", Unit.G),
    CAPROIC_ACID("caproic-acid", Unit.G),
    CAPRYLIC_ACID("caprylic-acid", Unit.G),
    CAPRIC_ACID("capric-acid", Unit.G),
    LAURIC_ACID("lauric-acid", Unit.G),
    MYRISTIC_ACID("myristic-acid", Unit.G),
    PALMITIC_ACID("palmitic-acid", Unit.G),
    STEARIC_ACID("stearic-acid", Unit.G),
    OLEIC_ACID("oleic-acid", Unit.G),
    LINOLEIC_ACID("linoleic-acid", Unit.G),
    DOCOSAHEXAENOIC_ACID("docosahexaenoic-acid", Unit.G),
    EICOSAPENTAENOIC_ACID("eicosapentaenoic-acid", Unit.G),
    ERUCIC_ACID("erucic-acid", Unit.G),
    MONOUNSATURATED_FAT("monounsaturated-fat", Unit.G),
    POLYUNSATURATED_FAT("polyunsaturated-fat", Unit.G),
    ALCOHOL("alcohol", Unit.PERCENT),
    PANTOTHENIC_ACID("pantothenic-acid", Unit.MILLI_G),
    BIOTIN("biotin", Unit.MICRO_G),
    CHLORIDE("chloride", Unit.MILLI_G),
    CHROMIUM("chromium", Unit.MICRO_G),
    FLUORIDE("fluoride", Unit.MILLI_G),
    IODINE("iodine", Unit.MICRO_G),
    MANGANESE("manganese", Unit.MILLI_G),
    MOLYBDENUM("molybdenum", Unit.MICRO_G),
    OMEGA_3("omega-3-fat", Unit.MILLI_G),
    OMEGA_6("omega-6-fat", Unit.MILLI_G),
    OMEGA_9("omega-9-fat", Unit.MILLI_G),
    BETA_CAROTENE("beta-carotene", Unit.G),
    BICARBONATE("bicarbonate", Unit.MILLI_G),
    SUGAR_ALCOHOL("polyols", Unit.G),
    ALPHA_LINOLENIC_ACID("alpha-linolenic-acid", Unit.G),
    ARACHIDIC_ACID("arachidic-acid", Unit.G),
    ARACHIDONIC_ACID("arachidonic-acid", Unit.G),
    BEHENIC_ACID("behenic-acid", Unit.G),
    CEROTIC_ACID("cerotic-acid", Unit.G),
    DIHOMO_GAMMA_LINOLENIC_ACID("dihomo-gamma-linolenic-acid", Unit.G),
    ELAIDIC_ACID("elaidic-acid", Unit.G),
    GAMMA_LINOLENIC_ACID("gamma-linolenic-acid", Unit.G),
    GONDOIC_ACID("gondoic-acid", Unit.G),
    LIGNOCERIC_ACID("lignoceric-acid", Unit.G),
    MEAD_ACID("mead-acid", Unit.G),
    MELISSIC_ACID("melissic-acid", Unit.G),
    MONTANIC_ACID("montanic-acid", Unit.G),
    NERVONIC_ACID("nervonic-acid", Unit.G),
    TRANS_FAT("trans-fat", Unit.G);

    companion object {

        fun fromOffTag(offTag: String?): Nutrient? {
            if (offTag == "energy") return ENERGY_KJ
            return entries.firstOrNull { it.offTag == offTag }
        }
    }

    fun getOffTagPerSize(perSize: PerSize): String = "${offTag}_${perSize.offTag}"
}