package com.example.android.newsapppart1;

/**
 * This NewsArrayAdapter class is based upon the Adapter class introduced in the Miwok App.
 * Source Code found here: https://github.com/udacity/ud839_Miwok
 * <p>
 * Also based upon previously submitted AudioBook and Library Tour App. Source Code found
 * * here: https://github.com/memckenzie2/AudiobookApp
 * * and here: https://github.com/memckenzie2/CentralLibraryTour
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        //Updates the date TextView for the current news story in news_story_item.xml
        String date =  currentNewsItem.getDate();
        TextView dateView = newsView.findViewById(R.id.date);
        //If no date (i.e null), sets date TextView to gone
        if(date == null){
            dateView.setVisibility(View.GONE);
        }else{
            dateView.setText(date);
        }

        //Updates the title TextView for the current news story in news_story_item.xml
        String title = currentNewsItem.getTitle();
        TextView titleView = newsView.findViewById(R.id.title);
        titleView.setText(title);

        //Updates the author TextView for the current news story in news_story_item.xml
        String author = currentNewsItem.getAuthor();
        TextView authorView = newsView.findViewById(R.id.author);
        //If no author (i.e null), sets date TextView to gone
        if(author == null){
            authorView.setVisibility(View.GONE);
        }else{
            authorView.setText(author);
            authorView.setVisibility(View.VISIBLE);
        }

        //Updates the section TextView for the current news story in news_story_item.xml
        String section = currentNewsItem.getSection();
        TextView sectionView = newsView.findViewById(R.id.section);
        sectionView.setText(section);

        return newsView;
    }
}
