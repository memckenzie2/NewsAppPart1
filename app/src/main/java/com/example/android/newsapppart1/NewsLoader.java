package com.example.android.newsapppart1;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.example.android.newsapppart1.NewsData;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsData>> {

    public NewsLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsData> loadInBackground() {
        // TODO: Implement this method
    }
}