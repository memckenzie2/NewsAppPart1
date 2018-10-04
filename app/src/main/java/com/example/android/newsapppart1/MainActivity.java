package com.example.android.newsapppart1;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;

//Public Domain Image Sourced from https://commons.wikimedia.org/wiki/File:US-UK-blend.png

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Use main_activity.xml as layout
        setContentView(R.layout.activity_main);

        //View Pager to allow navigation between each news category
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Create an adapter that knows which news category should be shown on each page in viewPager
        TopicAdapter adapter = new TopicAdapter(this, getSupportFragmentManager());

        //Set the adapter created above to point to the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs for each floor fragment
        TabLayout tabLayout = (TabLayout) findViewById(R.id.news_tabs);

        // Connect the tab layout with the view pager. This allows for swiping and updating the tabLayouts header to hilight the
        //current floor's tab
        tabLayout.setupWithViewPager(viewPager);
    }
}
