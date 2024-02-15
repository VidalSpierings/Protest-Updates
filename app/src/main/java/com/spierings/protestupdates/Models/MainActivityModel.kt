package com.spierings.protestupdates.Models

import com.spierings.protestupdates.Constants.Constants
import com.spierings.protestupdates.Observables.MainActivityObservables
import java.util.Locale

class MainActivityModel (var observables: MainActivityObservables): MainActivityModelInterface {
    override fun repopulateWithNewQuery(s: String?) {

        if (s?.isNotEmpty() == true && s != Constants.NoSearchQueryDefault) {

            observables.getGList().clear()

            val search = s.lowercase(Locale.getDefault())
            observables.getItems().forEach{


                if (it.display_name.toLowerCase(Locale.getDefault()).contains(search)) {

                    observables.getGList().add(it)

                }

            }

        }

    }


}