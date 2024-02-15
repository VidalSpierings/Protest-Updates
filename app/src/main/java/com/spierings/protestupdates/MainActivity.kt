package com.spierings.protestupdates

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.spierings.protestupdates.Views.MainActivityView

class MainActivity : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private var search_statistics_layout: CardView? = null
    private var search_results_layout: FrameLayout? = null
    private var search_results_recyclerview: RecyclerView? = null
    private var progress_bar: ProgressBar? = null
    private var searchView: SearchView? = null
    private var top_button: Button? = null
    private var middle_button: Button? = null
    private var bottom_button: Button? = null
    private var suggested_and_search_stats_layout: LinearLayoutCompat? = null

    private var acView: MainActivityView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_statistics_layout = findViewById(R.id.search_statistics_layout)
        search_results_layout = findViewById(R.id.search_results_layout)
        search_results_recyclerview = findViewById(R.id.search_results_recyclerview)
        progress_bar = findViewById(R.id.progress_bar)
        searchView = findViewById(R.id.searchView)
        top_button = findViewById(R.id.top_button)
        middle_button = findViewById(R.id.middle_button)
        bottom_button = findViewById(R.id.bottom_button)
        suggested_and_search_stats_layout = findViewById(R.id.suggested_and_search_stats_layout)



        acView = MainActivityView()

        acView!!.initData(
            this, this, suggested_and_search_stats_layout,
            search_results_layout, progress_bar, searchView,
            search_results_recyclerview, search_statistics_layout,
            top_button, middle_button, bottom_button)

    }

    override fun onBackPressed() {

        acView!!.onBackPressedMainActivity(
            this, this, search_statistics_layout,
            suggested_and_search_stats_layout, search_results_layout)

    }

    override fun onRestart() {
        super.onRestart()

        acView!!.refreshRetrieveStatistics(this, applicationContext, search_results_recyclerview, progress_bar)

    }
}