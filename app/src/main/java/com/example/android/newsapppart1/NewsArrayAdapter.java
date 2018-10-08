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

        String date =  currentNewsItem.getDate();
        TextView dateView = newsView.findViewById(R.id.date);
        if(date == null){
            dateView.setVisibility(View.GONE);
        }else{
            dateView.setText(date);
        }

        String title = currentNewsItem.getTitle();
        TextView titleView = newsView.findViewById(R.id.title);
        titleView.setText(title);

        String author = currentNewsItem.getAuthor();
        TextView authorView = newsView.findViewById(R.id.author);
        if(author == null){
            authorView.setVisibility(View.GONE);
        }else{
            authorView.setText(author);
            authorView.setVisibility(View.VISIBLE);
        }

        String section = currentNewsItem.getSection();
        TextView sectionView = newsView.findViewById(R.id.section);
        sectionView.setText(section);

        int pictureID = currentNewsItem.getStoryPictureId();
        ImageView imageView = newsView.findViewById(R.id.story_image);
        if(pictureID == -1){
            imageView.setVisibility(View.GONE);
        }else{
            imageView.setImageResource(pictureID);
            imageView.setVisibility(View.VISIBLE);
        }

        return newsView;
    }
}
