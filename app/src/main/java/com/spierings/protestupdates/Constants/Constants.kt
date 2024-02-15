package com.spierings.protestupdates.Constants

class Constants (){

    companion object {

        const val NoSearchQueryDefault = "ѩ"

        const val db_link = "https://protest-updates-default-rtdb.europe-west1.firebasedatabase.app/"

        const val statistics_table = "statistics"
        const val stat_name_table = "statName"
        const val stat_name2_table = "name"
        const val support_for_protests_table = "supportForProtests"
        const val stat_description_table = "description"
        const val stat_source_table = "source"
        const val state_code_table = "stateCode"
        const val general_value_table = "value"

        const val stat_type_reference = "statType"

        const val type_text_only_head = "textOnlyHead"
        const val type_text_Only_item = "textOnlyItem"
        const val type_skyline_chart_head = "skylineChartHead"
        const val type_boolean_states_head = "booleanStatesHead"
        const val type_boolean_states_item = "booleanStatesItem"
        const val population_turnout_per_state = "population_turnout_per_state"

        const val top_layer_data = "top_layer_data"
        const val second_layer_data = "second_layer_data"

        // statistics types constants, every constant changes statistics layouts accordingly

    }

}