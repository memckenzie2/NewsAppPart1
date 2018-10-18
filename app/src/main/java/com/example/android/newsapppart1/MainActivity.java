package com.example.android.newsapppart1;
/**
 * A News app that presents Political news from UK and US from the Guardian API. This app uses a Tab Layout to display three categories from a viewPager (Both Countries, UK only, and US only)
 * and each tab consists of a Fragment for that type of content.
 * <p>
 * Implementation of this app is based on the projects from the Udacity Android Basics nano-degree.
 * Each class has a list of the apps and or other projects that laid the foundation for that class including a link to the relevant source codes.
 * <p>
 * This MainActivity class is based upon the layout introduced for the Miwok App.
 * Source Code found here: https://github.com/udacity/ud839_Miwok
 * <p>
 * Also upon my previously submitted AudioBook and Library Tour App. Source Code found
 * here: https://github.com/memckenzie2/AudiobookApp
 * and here: https://github.com/memckenzie2/CentralLibraryTour
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

//Public Domain Image Sourced from https://commons.wikimedia.org/wiki/File:US-UK-blend.png

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Use main_activity.xml as layout.
        setContentView(R.layout.activity_main);

        //View Pager to allow navigation between each news category
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Create an adapter that knows which news category should be shown on each page in viewPager
        TopicAdapter adapter = new TopicAdapter(this, getSupportFragmentManager());

        //Set the adapter created above to point to the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs for each category of new's fragment
        TabLayout tabLayout = findViewById(R.id.news_tabs);

        // Connect the tab layout with the view pager. This allows for swiping and updating the tabLayouts header to hilight the
        //current floor's tab
        tabLayout.setupWithViewPager(viewPager);


    }
}
