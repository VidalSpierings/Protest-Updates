package com.spierings.protestupdates.Database

import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.spierings.protestupdates.Constants.Constants
import com.spierings.protestupdates.Misc.StatItem
import com.spierings.protestupdates.Observables.MainActivityObservables
import com.spierings.protestupdates.ViewModels.MainActivityViewHolder

@Suppress("NAME_SHADOWING")
class MainActivityDatabase constructor(val observables: MainActivityObservables): MainActivityDatabaseInterface {

    private var callBack: MyCallback? = null
    private var itemList: ArrayList<StatItem>? = null
    private lateinit var names: List<String>
    private lateinit var names2: List<String>

    fun getData(
        context: Context, mainActivityViewHolder: MainActivityViewHolder, myTestInterface: MainActivityViewHolder.MyTestInterface?,
        appCompatActivity: AppCompatActivity, progress_bar: ProgressBar?, search_results_recycylerview: RecyclerView?, type: String,
        searchQuery: String) {

        val fbRef: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
            Constants.db_link + Constants.statistics_table)

        names = ArrayList()

        names = emptyList()

        fbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                getAndInitialiseItems(dataSnapshot)

                observables.getGList() == observables.getItems()

                mainActivityViewHolder.initCallBack(
                    appCompatActivity, context, progress_bar,
                    search_results_recycylerview, observables.getGList(),
                    type, searchQuery)

                observables.setItemNames(names)

                observables.getfirebaseCallback()?.onCallBack(observables.getItems())

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    private fun getAndInitialiseItems(dataSnapshot: DataSnapshot){

        for (snapshot: DataSnapshot in dataSnapshot.children){

            addToNamesList(snapshot)

            addToItemsList(snapshot)

        }

    }

    private fun addToItemsList(snapshot: DataSnapshot){

        val item = StatItem("", "", "", "", "")

        item.display_name = snapshot.child(Constants.stat_name_table).getValue(String::class.java)!!
        item.type = snapshot.child(Constants.stat_type_reference).getValue(String::class.java)!!
        item.tableDatabasePath = snapshot.ref.toString()

        observables.getItems().add(item)

    }

    private fun addToNamesList(snapshot: DataSnapshot){

        names = names + snapshot.child(Constants.stat_name_table).getValue(String::class.java)!!

    }

    fun getDataSecondLayer(context: Context, mainActivityViewHolder: MainActivityViewHolder?, myTestInterface: MainActivityViewHolder.MyTestInterface?,
                                    appCompatActivity: AppCompatActivity, progress_bar: ProgressBar, search_results_recycylerview: RecyclerView, type: String, tableDatabasePath: String){

        initNames2List()

        val fbRef2: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
            tableDatabasePath)

        fbRef2.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                getNestedObjects(appCompatActivity, context, search_results_recycylerview, snapshot, mainActivityViewHolder!!)

            }

            override fun onCancelled(error: DatabaseError) {



            }

        })

    }

    private fun getNestedObjects(
        appCompatActivity: AppCompatActivity, context: Context,
        search_results_recycylerview: RecyclerView, snapshot: DataSnapshot,
        mainActivityViewHolder: MainActivityViewHolder
    ){

        observables.getItems2().clear()

        setLogs1(snapshot)

        for (snapshotLoop: DataSnapshot in snapshot.children){

            initNewItemObject(snapshotLoop, snapshot)

        }

        observables.setItems2Names(names2)

        mainActivityViewHolder.initRecyclerViewFunc2(appCompatActivity, context, search_results_recycylerview, observables.getItems2())

    }

    private fun setLogs1(snapshot: DataSnapshot){

        Log.d("item", "DISTINGUISABLE TEXT  itemCount: " + snapshot.childrenCount)

        Log.d("tablePath", "DISTINGUISABLE TEXT  ref: " + snapshot.ref)

    }

    private fun initNewItemObject(snapshotLoop: DataSnapshot, snapshot: DataSnapshot){

        val item = StatItem("", "", "", "", "")

        Log.d("loopID", "DISTINGUISABLE TEXT  snapshot ID: " + snapshotLoop.child(Constants.stat_type_reference).getValue(String::class.java))

        if (snapshotLoop.child(Constants.stat_type_reference).getValue(String::class.java) == Constants.type_text_Only_item) {

            setItemTextOnlyType(item, snapshotLoop)

        }

    }

    private fun initNames2List(){

        names2 = ArrayList()

        names2 = emptyList()

    }

    private fun setItemTextOnlyType(item: StatItem, snapshotLoop: DataSnapshot){

        item.display_name = snapshotLoop.child(Constants.stat_name2_table).getValue(String::class.java)!!
        item.type = snapshotLoop.child(Constants.stat_type_reference).getValue(String::class.java)!!
        item.metaType = snapshotLoop.child(Constants.stat_description_table).getValue(String::class.java)!!
        item.metaMetaType = snapshotLoop.child(Constants.stat_source_table).getValue(String::class.java)!!
        item.tableDatabasePath = snapshotLoop.ref.toString()

        observables.getItems2().add(item)

        names2 = names2 + snapshotLoop.child(Constants.stat_name2_table).getValue(String::class.java)!!

    }

    interface MyCallback {

        fun onCallBack(result: String)

    }

}