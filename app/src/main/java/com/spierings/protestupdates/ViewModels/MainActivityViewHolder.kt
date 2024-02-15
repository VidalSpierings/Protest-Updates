package com.spierings.protestupdates.ViewModels

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.client.Firebase
import com.spierings.protestupdates.Constants.Constants
import com.spierings.protestupdates.Database.MainActivityDatabase
import com.spierings.protestupdates.Misc.MyRecyclerViewAdapter
import com.spierings.protestupdates.Misc.StatItem
import com.spierings.protestupdates.Misc.StatItemSpacingDecoration
import com.spierings.protestupdates.Models.MainActivityModel
import com.spierings.protestupdates.Observables.MainActivityObservables
import com.spierings.protestupdates.R
import com.spierings.protestupdates.Views.MainActivityView
import java.util.Locale

class MainActivityViewHolder constructor(
    var observables: MainActivityObservables,
    var database: MainActivityDatabase,
    var mainActivityView: MainActivityView,
    var mainActivityModel: MainActivityModel
): MainActivityViewHolderInterface {

    fun updateItemsOnNewInput(appCompatActivity: AppCompatActivity, context: Context, s: String, search_results_recyclerView: RecyclerView?, g_list: ArrayList<StatItem>) {

        if (s.isNotEmpty() && s == Constants.NoSearchQueryDefault) {

            repopulateWithOriginalItems()

        }

        else {

            mainActivityModel.repopulateWithNewQuery(s)

            val other = arrayListOf<StatItem>()

            initRecyclerViewFunc(appCompatActivity, context, search_results_recyclerView, other)

        }

    }

    private fun repopulateWithNewQuery(s: String){

        if (s.isNotEmpty() && s != Constants.NoSearchQueryDefault) {

            observables.getGList().clear()

            val search = s.toLowerCase(Locale.getDefault())
            observables.getItems().forEach{


                if (it.display_name.toLowerCase(Locale.getDefault()).contains(search)) {

                    observables.getGList().add(it)

                }

            }

        }

    }

    private fun repopulateWithOriginalItems(){

        observables.getGList().clear()
        observables.getGList().addAll(observables.getItems())

    }

    private fun refreshList(appCompatActivity: AppCompatActivity, context: Context, search_results_recyclerview: RecyclerView){

        myAdapter = MyRecyclerViewAdapter(observables.getGList(), appCompatActivity, context, mainActivityView)
        myAdapter.submitList(observables.getGList())
        search_results_recyclerview.adapter = myAdapter

        search_results_recyclerview.adapter!!.notifyDataSetChanged()

    }

    private lateinit var myAdapter: MyRecyclerViewAdapter

    private var callBack: MyTestInterface? = null

    fun retrieveAllStatisticNames(
        appCompatActivity: AppCompatActivity,
        context: Context,
        search_results_recyclerview: RecyclerView?,
        progress_bar: ProgressBar?,
        type: String,
        searchQuery: String) {

        Firebase.setAndroidContext(context)

        database.getData(
            context, this, observables.getfirebaseCallback(),
            appCompatActivity, progress_bar, search_results_recyclerview,
            type, searchQuery)

    }

    fun initCallBack(appCompatActivity: AppCompatActivity, context: Context, progress_bar: ProgressBar?, search_results_recyclerview: RecyclerView?, g_list: ArrayList<StatItem>, type: String, searchQuery: String){

        callBack = object : MyTestInterface {

            override fun onCallBack(list: ArrayList<StatItem>) {

                updateItemsOnNewInput(appCompatActivity, context, searchQuery, search_results_recyclerview, observables.getGList())

                mainActivityView.enableSearch(appCompatActivity, context)

                mainActivityView.setSuggestedButtonsOnClickListeners(appCompatActivity, context)

            }

        }

        observables.setfirebaseCallback(callBack as MyTestInterface)

    }

    fun replaceProgressBarWithRecyclerView(
        appCompatActivity: AppCompatActivity,
        progress_bar: ProgressBar, search_results_recyclerview: RecyclerView,
        list: ArrayList<StatItem>, type: String){

        progress_bar.visibility = View.GONE

        initRecyclerView(appCompatActivity, search_results_recyclerview, observables.getGList(), type)

    }

    private fun initRecyclerView(appCompatActivity: AppCompatActivity, search_results_recyclerview: RecyclerView, list: ArrayList<StatItem>, type: String){

        search_results_recyclerview.apply {

            addItemDivider(resources, search_results_recyclerview)

            if (type == Constants.type_text_only_head || type == Constants.type_boolean_states_head) {

                initRecyclerViewFunc(appCompatActivity, context, this, observables.getGList())

            }

            if (type == "" || type == Constants.type_text_Only_item || type == Constants.type_boolean_states_item) {

                initRecyclerViewFunc2(appCompatActivity, context, this, observables.getGList())

            }

        }

    }

    fun initRecyclerViewFunc(appCompatActivity: AppCompatActivity, context: Context, search_results_recyclerview: RecyclerView?, list: ArrayList<StatItem>){

        Log.d("observ", "GList observable: " + observables.getGList())
        Log.d("observ", "Items List observable: " + observables.getItems())

        // OBSERVABLE NOT POPULATED

        search_results_recyclerview!!.layoutManager = LinearLayoutManager(context)

        myAdapter = MyRecyclerViewAdapter(observables.getGList(), appCompatActivity, context, mainActivityView)
        myAdapter.submitList(observables.getGList())
        search_results_recyclerview.adapter = myAdapter

        search_results_recyclerview.adapter!!.notifyDataSetChanged()

    }

    fun emptyRecyclerView(appCompatActivity: AppCompatActivity, context: Context, search_results_recyclerview: RecyclerView?){

        search_results_recyclerview!!.layoutManager = LinearLayoutManager(context)

        val emptyList = arrayListOf<StatItem>()
        val emptyListlist = listOf<StatItem>()

        myAdapter = MyRecyclerViewAdapter(emptyListlist, appCompatActivity, context, mainActivityView)
        myAdapter.submitList(emptyList)
        search_results_recyclerview.adapter = myAdapter

    }

    fun initRecyclerViewFunc2(appCompatActivity: AppCompatActivity, context: Context, search_results_recyclerview: RecyclerView?, list: ArrayList<StatItem>){

        search_results_recyclerview!!.layoutManager = LinearLayoutManager(context)

        myAdapter = MyRecyclerViewAdapter(observables.getItems2(), appCompatActivity, context, mainActivityView)
        myAdapter.submitList(observables.getItems2())
        search_results_recyclerview.adapter = myAdapter

    }

    private fun addItemDivider(
        resources: Resources,
        search_results_recyclerview: RecyclerView
    ){

        val divider = StatItemSpacingDecoration(
            resources.getDimension(R.dimen.stat_list_item_margin).toInt())

        search_results_recyclerview.addItemDecoration(divider)

    }

    interface MyTestInterface{

        fun onCallBack(list: ArrayList<StatItem>)

    }

}