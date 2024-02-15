package com.spierings.protestupdates.Misc

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.spierings.protestupdates.R
import com.spierings.protestupdates.StatisticsActivity

open class MyRecyclerViewAdapterResults constructor(var appCompatActivity: AppCompatActivity): RecyclerView.Adapter<MyRecyclerViewAdapterResults.MyRecyclerViewResultsHolder>(),
    Filterable {

    private var items: List<String> = ArrayList()

    override fun onBindViewHolder(holder: MyRecyclerViewResultsHolder, position: Int){

        holder.bind(appCompatActivity, items[position])

    }

    override fun getItemCount(): Int {

        return items.size

    }

    open fun submitList(list: List<String>){

        items = list
    }

    override fun getFilter(): Filter {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewResultsHolder {

        val inflater = LayoutInflater.from(parent.context)
        return MyRecyclerViewResultsHolder(inflater, parent)

    }

    class MyRecyclerViewResultsHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)){

        private var textView: TextView? = null

        init {

            textView = itemView.findViewById(R.id.stat_title)

        }

        fun bind (appCompatActivity: AppCompatActivity, listItem: String) {

            textView?.text = listItem

            textView?.setOnClickListener {

                startStatisticsActivity(appCompatActivity)

            }

        }

        private fun startStatisticsActivity(appCompatActivity: AppCompatActivity){

            Log.d("itemName", textView?.text as String)

            val intent = Intent(appCompatActivity, StatisticsActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION

            appCompatActivity.startActivity(intent)

        }

    }


}