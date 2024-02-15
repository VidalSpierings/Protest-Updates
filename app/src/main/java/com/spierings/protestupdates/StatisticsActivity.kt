package com.spierings.protestupdates

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.firebase.client.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.spierings.protestupdates.Constants.Constants

class StatisticsActivity : AppCompatActivity() {

    private var any_chart_view: AnyChartView? = null;
    private var stat_description: TextView? = null;

    private var statType: String = ""

    private var link: DatabaseReference?= null

    private var databaseReference: String? = null

    var data: ArrayList<DataEntry>? = null

    var pie: Pie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        Firebase.setAndroidContext(this)

        any_chart_view = findViewById(R.id.any_chart_view)
        stat_description = findViewById(R.id.stat_description)

        val type: String? = intent.getStringExtra("type")
        databaseReference = intent.getStringExtra("databaseReference")

        showLogs(type)

        link = FirebaseDatabase.getInstance().getReferenceFromUrl(databaseReference!!)

        when (type) {

            Constants.type_text_Only_item -> {

                Log.d("acProtocol", "protestsPerStateLayoutProtocol")

                protestsPerStateLayoutProtocol()

            }

            Constants.type_skyline_chart_head -> {

                Log.d("acProtocol", "skylineChartProtocol")

                skylineChartProtocol(baseContext)

            }

        }

    }

    private fun showLogs(type: String?){

        Log.d("dbRef", "ref: $databaseReference")
        Log.d("recType", "received type: $type")

    }

    private fun protestsPerStateLayoutProtocol(){

        link!!.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                makeAnyChartViewInvisible()

                stat_description?.visibility = View.VISIBLE

                stat_description?.text = dataSnapshot.child(Constants.stat_description_table).getValue(String::class.java)

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    private fun makeAnyChartViewInvisible(){

        val params = any_chart_view?.layoutParams
        params?.height = 0
        params?.width = 0
        any_chart_view?.layoutParams = params

        any_chart_view?.visibility = View.INVISIBLE

    }

    private fun skylineChartProtocol (context: Context){

        makeAnyChartViewVisible()

        stat_description?.visibility = View.GONE

        getDataFromForLoop(context)

    }

    private fun makeAnyChartViewVisible(){

        val params = any_chart_view?.layoutParams
        params?.height = 400
        params?.width = 250
        any_chart_view?.layoutParams = params

        any_chart_view?.visibility = View.VISIBLE

    }

    private fun getDataFromForLoop(context: Context){

        data = ArrayList<DataEntry>()

        val fbRef: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(databaseReference!!)

        fbRef.addValueEventListener(object : com.google.firebase.database.ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                addItemIfNotStatName(dataSnapshot)

                initPieChart(dataSnapshot)

                initAnyChartView(context)

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    private fun addItemIfNotStatName(dataSnapshot: DataSnapshot){

        for (snapshot: DataSnapshot in dataSnapshot.children) {

            if (snapshot.child(Constants.stat_name_table).getValue(String::class.java) != null) {

                data!!
                    .add(
                        ValueDataEntry(
                        snapshot.child(Constants.stat_name_table).getValue(String::class.java),
                        snapshot.child(Constants.general_value_table).getValue(Integer::class.java))
                    )

            }

        }

    }

    private fun initAnyChartView(context: Context){

        val dimenInt: Int = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 1F,
            context.resources.displayMetrics).toInt()

        val layoutParams: LinearLayoutCompat.LayoutParams = LinearLayoutCompat.LayoutParams(dimenInt * 300, dimenInt * 600, 4.0f)

        layoutParams.gravity = Gravity.CENTER

        any_chart_view?.layoutParams = layoutParams

        any_chart_view?.setChart(pie)

    }

    private fun initPieChart(dataSnapshot: DataSnapshot){

        pie = AnyChart.pie3d()

        pie!!.data(data as List<DataEntry>?)

        pie!!.title(dataSnapshot.child(Constants.stat_name_table).getValue(String::class.java))

    }

}