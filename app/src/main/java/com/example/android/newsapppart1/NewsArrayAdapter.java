package com.example.android.newsapppart1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsArrayAdapter extends ArrayAdapter<NewsData> {

    public NewsArrayAdapter(Context context, ArrayList<NewsData> loc) {
        super(context, 0, loc);
    }

    @Override
    public View getView(final int locationPosition, View convertableView, ViewGroup parentView) {
        View newsView = convertableView;

        //Inflates a view if no view has been made or reuses current view if it has.
        //Check to see if view exists
        if (newsView == null) {
            //inflates view
            newsView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_story_item, parentView, false);
        }

        // Get the NewsData object for the given position in the menu list
        final NewsData currentNewsItem = getItem(locationPosition);

        //Displays the LocationData's location name to the location_name TextView in location_list_item.xml
        TextView newsTitle = newsView.findViewById(R.id.title);
        //newsTitle.setText(currentLocItem.getStoryTitle());

        return newsView;
    }
}
