package com.spierings.protestupdates.Misc

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.spierings.protestupdates.R
import com.spierings.protestupdates.Views.MainActivityView

open class MyRecyclerViewAdapter constructor(private val statItemList: List<StatItem>, var appCompatActivity: AppCompatActivity, val context: Context, var mainActivityView: MainActivityView): RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder>(),
    Filterable {

    private var items: ArrayList<StatItem> = ArrayList()

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, position: Int){

        val currentItem = statItemList

        holder.bind(appCompatActivity, context, items[position])

        initHolderMetaData(position, currentItem, holder)

    }

    private fun initHolderMetaData(position: Int, currentItem: List<StatItem>, holder: MyRecyclerViewHolder){

        holder.type = currentItem[position].type
        holder.metaType = currentItem[position].metaType
        holder.tableDatabasePath = currentItem[position].tableDatabasePath

    }

    override fun getItemCount(): Int {

        return items.size

    }

    open fun submitList(list: ArrayList<StatItem>){

        items = list
    }

    override fun getFilter(): Filter {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return MyRecyclerViewHolder(inflater, parent, mainActivityView)

    }

    class MyRecyclerViewHolder(inflater: LayoutInflater, parent: ViewGroup, private var mainActivityView: MainActivityView) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)){

        private var textView: TextView? = null
        var type: String = ""
        var metaType: String = ""
        var tableDatabasePath: String = ""

        init {

            textView = itemView.findViewById(R.id.stat_title)

        }

        fun bind (appCompatActivity: AppCompatActivity, context: Context, listItem: StatItem) {

            initListItemMetaData(listItem)

            textView?.setOnClickListener {

                mainActivityView.decideOnClickProtocol(appCompatActivity, context, type, metaType, tableDatabasePath)

            }

        }

        private fun initListItemMetaData(listItem: StatItem){

            textView?.text = listItem.display_name

            type = listItem.type

            metaType = listItem.metaType

            tableDatabasePath = listItem.tableDatabasePath


        }
    }


}