package com.spierings.protestupdates.Observables

import com.spierings.protestupdates.Constants.Constants
import com.spierings.protestupdates.Database.MainActivityDatabase
import com.spierings.protestupdates.Misc.StatItem
import com.spierings.protestupdates.ViewModels.MainActivityViewHolder
import java.util.ArrayList
import java.util.Observable

class MainActivityObservables: Observable() {

    private var items: ArrayList<StatItem> = ArrayList()

    private var itemNames: List<String> = ArrayList()

    private var type: String = ""

    private var items2: ArrayList<StatItem> = ArrayList()

    private var items2Names: List<String> = ArrayList()

    private var current_data_layer = Constants.top_layer_data

    private var firebaseCallback: MainActivityViewHolder.MyTestInterface? = null

    private var firebaseCallbackMainActivityDatabase: MainActivityDatabase.MyCallback? = null

    private var result: String = ""

    private var gList: ArrayList<StatItem> = ArrayList()


    fun setItems(items: ArrayList<StatItem>) {

        this.items = items
        this.setChanged()
        this.notifyObservers(items)
    }

    fun setGList(gList: ArrayList<StatItem>) {

        this.gList = gList
        this.setChanged()
        this.notifyObservers(gList)
    }

    fun setItems2(items2: ArrayList<StatItem>) {

        this.items2 = items2
        this.setChanged()
        this.notifyObservers(items2)
    }

    fun setItems2Names(items2Names: List<String>) {

        this.items2Names = items2Names
        this.setChanged()
        this.notifyObservers(items2Names)
    }

    fun setItemNames(itemNames: List<String>) {

        this.itemNames = itemNames
        this.setChanged()
        this.notifyObservers(itemNames)
    }

    fun setfirebaseCallback(firebaseCallback: MainActivityViewHolder.MyTestInterface) {

        this.firebaseCallback = firebaseCallback
        this.setChanged()
        this.notifyObservers(firebaseCallback)
    }

    fun setfirebaseCallbackDB(firebaseCallbackMainActivityDatabase: MainActivityDatabase.MyCallback) {

        this.firebaseCallbackMainActivityDatabase = firebaseCallbackMainActivityDatabase
        this.setChanged()
        this.notifyObservers(firebaseCallbackMainActivityDatabase)
    }

    fun setResult(result: String) {

        this.result = result
        this.setChanged()
        this.notifyObservers(result)
    }

    fun setType(type: String) {

        this.type = type
        this.setChanged()
        this.notifyObservers(type)
    }

    fun setCurrentDataLayer(current_data_layer: String) {

        this.current_data_layer = current_data_layer
        this.setChanged()
        this.notifyObservers(current_data_layer)
    }

    fun getCurrentDataLayer(): String? {
        return current_data_layer
    }

    fun getfirebaseCallback(): MainActivityViewHolder.MyTestInterface? {
        return firebaseCallback
    }

    fun getResult(): String? {
        return result
    }

    fun getType(): String? {
        return type
    }

    fun getfirebaseCallbackDB(): MainActivityDatabase.MyCallback? {
        return firebaseCallbackMainActivityDatabase
    }

    fun getItems(): ArrayList<StatItem> {
        return items
    }

    fun getGList(): ArrayList<StatItem> {
        return gList
    }

    fun getItems2(): ArrayList<StatItem> {
        return items2
    }

    fun getItems2Names(): List<String> {
        return items2Names
    }

    fun getItemNames(): List<String> {
        return itemNames
    }

}