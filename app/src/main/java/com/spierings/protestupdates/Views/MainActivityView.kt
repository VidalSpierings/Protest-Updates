package com.spierings.protestupdates.Views

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.spierings.protestupdates.Animations.Animations
import com.spierings.protestupdates.Constants.Constants
import com.spierings.protestupdates.Database.MainActivityDatabase
import com.spierings.protestupdates.Models.MainActivityModel
import com.spierings.protestupdates.Observables.MainActivityObservables
import com.spierings.protestupdates.StatisticsActivity
import com.spierings.protestupdates.ViewModels.MainActivityViewHolder
import java.util.Random

class MainActivityView: MainActivityViewInterface{

    private var observables: MainActivityObservables = MainActivityObservables()

    var viewHolder: MainActivityViewHolder? = null

    var gSearchResultsRecyclerView: RecyclerView? = null

    private var gProgressBar: ProgressBar? = null

    private var database: MainActivityDatabase? = null

    private var mainActivityModel: MainActivityModel? = null

    var animations: Animations? = null

    var gSearchResultsLayout: FrameLayout? = null

    private var gSearchView: SearchView? = null

    private var gSuggestedAndSearchStatsLayout: LinearLayoutCompat? = null

    private var gSearchStatisticsLayout: CardView? = null

    private var gTop_button: Button? = null

    private var gMiddle_button: Button? = null

    private var gBottom_button: Button? = null

    private var intent: Intent? = null

    override fun initData(
        appCompatActivity: AppCompatActivity,
        context: Context, suggested_and_search_stats_layout: LinearLayoutCompat?,
        search_results_layout: FrameLayout?, progress_bar: ProgressBar?,
        searchView: SearchView?, search_results_recyclerView: RecyclerView?,
        search_statistics_layout: CardView?, top_button: Button?, middle_button: Button?,
        bottom_button: Button?
    ) {

        initClasses()

        initGlobalVariables(
            search_results_recyclerView, progress_bar, search_results_layout,
            searchView, search_statistics_layout, suggested_and_search_stats_layout,
            top_button, middle_button, bottom_button)

        refreshRetrieveStatistics(appCompatActivity, context, search_results_recyclerView, progress_bar)

        search_statistics_layout?.setOnClickListener {

            make_sugg_and_search_layout_visible(
                appCompatActivity, search_statistics_layout,
                suggested_and_search_stats_layout,
                search_results_layout)

        }

    }

    override fun refreshRetrieveStatistics(appCompatActivity: AppCompatActivity, context: Context, search_results_recyclerView: RecyclerView?, progress_bar: ProgressBar?){

        gTop_button!!.setOnClickListener(null)
        gMiddle_button!!.setOnClickListener(null)
        gBottom_button!!.setOnClickListener(null)

        observables.getGList().clear()
        observables.getItems().clear()

        viewHolder!!.retrieveAllStatisticNames(
            appCompatActivity, context, search_results_recyclerView,
            progress_bar, Constants.type_text_only_head, Constants.NoSearchQueryDefault)

    }

    private fun initClasses(){

        observables = MainActivityObservables()

        database = MainActivityDatabase(observables)

        mainActivityModel = MainActivityModel(observables)

        viewHolder = MainActivityViewHolder(observables, database!!, this, mainActivityModel!!)

        animations = Animations()

    }

    override fun setSuggestedButtonsOnClickListeners(appCompatActivity: AppCompatActivity, context: Context){

        require(observables.getGList().size >= 3) { "Can't ask for more numbers than are available" }
        val range = Random()

        val generated = LinkedHashSet<Int>()

        while (generated.size < 3) {
            val next = range.nextInt(observables.getGList().size) + 1

            generated.add(next)
        }

        val list = ArrayList<Int>(generated)

        val randomIndexTopButton = list[0]
        val randomIndexMiddleButton = list[1]
        val randomIndexBottomButton = list[2]

        setSuggestedButtonsText(randomIndexTopButton, randomIndexMiddleButton, randomIndexBottomButton)

        gTop_button!!.setOnClickListener{

            decideOnClickProtocol(
                appCompatActivity, context,
                observables.getGList()[randomIndexTopButton].type,
                observables.getGList()[randomIndexTopButton].metaType,
                observables.getGList()[randomIndexTopButton].tableDatabasePath)

        }

        gMiddle_button!!.setOnClickListener{

            decideOnClickProtocol(
                appCompatActivity, context,
                observables.getGList()[randomIndexMiddleButton].type,
                observables.getGList()[randomIndexMiddleButton].metaType,
                observables.getGList()[randomIndexMiddleButton].tableDatabasePath)

        }

        gBottom_button!!.setOnClickListener {

            decideOnClickProtocol(
                appCompatActivity, context,
                observables.getGList()[randomIndexBottomButton].type,
                observables.getGList()[randomIndexBottomButton].metaType,
                observables.getGList()[randomIndexBottomButton].tableDatabasePath)
        }

    }

    private fun setSuggestedButtonsText(randomIndexTopButton: Int, randomIndexMiddleButton: Int, randomIndexBottomButton: Int){

        gTop_button!!.text = observables.getGList()[randomIndexTopButton].display_name
        gMiddle_button!!.text = observables.getGList()[randomIndexMiddleButton].display_name
        gBottom_button!!.text = observables.getGList()[randomIndexBottomButton].display_name

    }

    private fun suggStatButtonOrSearchSubmitProtocol(appCompatActivity: AppCompatActivity){

        animations!!.fadeOutView(appCompatActivity, gSuggestedAndSearchStatsLayout as View, true, gSearchResultsLayout as View)

        gSearchStatisticsLayout!!.visibility = View.GONE

        gSearchResultsLayout!!.visibility = View.VISIBLE

        gSuggestedAndSearchStatsLayout!!.visibility = View.GONE

        gProgressBar!!.visibility = View.VISIBLE

        viewHolder!!.replaceProgressBarWithRecyclerView(appCompatActivity,
            gProgressBar!!, gSearchResultsRecyclerView!!, observables.getGList(), Constants.type_text_only_head)

    }


    override fun make_sugg_and_search_layout_visible(
        appCompatActivity: AppCompatActivity, search_statistics_layout: CardView?,
        suggested_and_search_stats_layout: LinearLayoutCompat?,
        search_results_layout: FrameLayout?
    ) {

        animations!!.fadeOutView(appCompatActivity, gSearchStatisticsLayout as View, true, gSuggestedAndSearchStatsLayout as View)

        makeSuggAndSearchViewVisible(search_statistics_layout, suggested_and_search_stats_layout, search_results_layout)

    }

    override fun makeSuggAndSearchViewVisible(search_statistics_layout: CardView?,
                                              suggested_and_search_stats_layout: LinearLayoutCompat?,
                                              search_results_layout: FrameLayout?){

        search_statistics_layout!!.visibility = View.GONE

        search_results_layout!!.visibility = View.GONE

        suggested_and_search_stats_layout!!.visibility = View.VISIBLE

    }

    override fun decideOnClickProtocol(appCompatActivity: AppCompatActivity, context: Context, type: String, metaType: String, tableDatabasePath: String){

        Log.d("result", "DISTUNGIUSABLE TEXT result: $type")

        if (type == Constants.type_text_only_head || type == Constants.type_boolean_states_head) {

            database!!.getDataSecondLayer(
                context, viewHolder, observables.getfirebaseCallback(),
                appCompatActivity, gProgressBar!!,
                gSearchResultsRecyclerView!!, type, tableDatabasePath)

            makeAccordingViewsVisibleInvisible()

        }

        if (type == Constants.type_text_Only_item ||  type == Constants.type_skyline_chart_head || type == Constants.type_boolean_states_item) {

            startStatisticsActivity(appCompatActivity, type, metaType, tableDatabasePath)

        }

    }

    private fun makeAccordingViewsVisibleInvisible(){

        gSearchStatisticsLayout!!.visibility = View.GONE

        gSearchResultsLayout!!.visibility = View.VISIBLE

        gSuggestedAndSearchStatsLayout!!.visibility = View.GONE

    }

    private fun startStatisticsActivity(appCompatActivity: AppCompatActivity, data: String, metaType: String, tableDatabasePath: String){

        Log.d("dbPath", "dbPath: $tableDatabasePath")

        val intent = Intent(appCompatActivity, StatisticsActivity::class.java)

        intent.putExtra("type", data)

        intent.putExtra("metaType", metaType)

        intent.putExtra("databaseReference", tableDatabasePath)

        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION

        appCompatActivity.startActivity(intent)

    }

    override fun onBackPressedMainActivity(appCompatActivity: AppCompatActivity, context: Context,
                                           search_statistics_layout: CardView?,
                                           suggested_and_search_stats_layout: LinearLayoutCompat?,
                                           search_results_layout: FrameLayout?){

        viewHolder!!.emptyRecyclerView(appCompatActivity, context, gSearchResultsRecyclerView)

        initActivityOnBackPressedFunc(
            appCompatActivity, search_statistics_layout,
            suggested_and_search_stats_layout, search_results_layout)

    }

    private fun initActivityOnBackPressedFunc(appCompatActivity: AppCompatActivity,
                                              search_statistics_layout: CardView?,
                                              suggested_and_search_stats_layout: LinearLayoutCompat?,
                                              search_results_layout: FrameLayout?){

        when {

            gSearchStatisticsLayout?.visibility == View.VISIBLE -> appCompatActivity.finishAffinity()

            gSearchResultsLayout?.visibility == View.VISIBLE -> makeSuggAndSearchViewVisible(search_statistics_layout, suggested_and_search_stats_layout, search_results_layout)

            suggested_and_search_stats_layout?.visibility == View.VISIBLE -> makeSearchStatisticsButtonVisible(
                search_statistics_layout, suggested_and_search_stats_layout,
                search_results_layout
            )
        }

    }

    private fun makeSearchStatisticsButtonVisible(
        search_statistics_layout: CardView?,
        suggested_and_search_stats_layout: LinearLayoutCompat?,
        search_results_layout: FrameLayout?
    ){

        search_statistics_layout!!.visibility = View.VISIBLE

        search_results_layout!!.visibility = View.GONE

        suggested_and_search_stats_layout!!.visibility = View.GONE

    }

    override fun enableSearch(appCompatActivity: AppCompatActivity, context: Context) {

        val listener = object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s: String): Boolean {

                suggStatButtonOrSearchSubmitProtocol(appCompatActivity)

                return true
            }

            override fun onQueryTextChange(s: String): Boolean {

                viewHolder!!.updateItemsOnNewInput(appCompatActivity, context, s, gSearchResultsRecyclerView, observables.getGList())

                return true
            }

        }

        gSearchView!!.setOnQueryTextListener(listener)

    }

    private fun initGlobalVariables(
        search_results_recyclerView: RecyclerView?, progress_bar: ProgressBar?,
        search_results_layout: FrameLayout?, searchView: SearchView?,
        search_statistics_layout: CardView?,
        suggested_and_search_stats_layout: LinearLayoutCompat?,
        top_button: Button?, middle_button: Button?, bottom_button: Button?
    ){

        gTop_button = top_button

        gMiddle_button = middle_button

        gBottom_button = bottom_button

        gSearchResultsRecyclerView = search_results_recyclerView

        gProgressBar = progress_bar

        gSearchResultsLayout = search_results_layout

        gSearchView = searchView

        gSearchStatisticsLayout = search_statistics_layout

        gSuggestedAndSearchStatsLayout = suggested_and_search_stats_layout

    }

}