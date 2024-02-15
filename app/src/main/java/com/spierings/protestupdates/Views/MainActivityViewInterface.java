package com.spierings.protestupdates.Views;

import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public interface MainActivityViewInterface {

    void initData(AppCompatActivity appCompatActivity,
                  Context context, LinearLayoutCompat suggested_and_search_stats_layout,
                  FrameLayout search_results_layout, ProgressBar progressBar,
                  SearchView searchView, RecyclerView search_results_recyclerView,
                  CardView search_statistics_layout, Button top_bottom, Button middle_button,
                  Button bottom_button);

    void make_sugg_and_search_layout_visible (
            AppCompatActivity appCompatActivity, CardView search_statistics_layout,
            LinearLayoutCompat suggested_and_search_stats_layout,
            FrameLayout search_results_layout);

    void decideOnClickProtocol(AppCompatActivity appCompatActivity, Context context, String type, String metaType, String tableDatabasePath);

    void onBackPressedMainActivity(AppCompatActivity appCompatActivity, Context context,
                                   CardView search_statistics_layout,
                                   LinearLayoutCompat suggested_and_search_stats_layout,
                                   FrameLayout search_results_layout);

    void enableSearch(AppCompatActivity appCompatActivity, Context context);

    void setSuggestedButtonsOnClickListeners(AppCompatActivity appCompatActivity, Context context);

    void makeSuggAndSearchViewVisible(CardView search_statistics_layout,
                                      LinearLayoutCompat suggested_and_search_stats_layout,
                                      FrameLayout search_results_layout);

    void refreshRetrieveStatistics(AppCompatActivity appCompatActivity, Context context, RecyclerView search_results_recyclerView, ProgressBar progress_bar);


}
