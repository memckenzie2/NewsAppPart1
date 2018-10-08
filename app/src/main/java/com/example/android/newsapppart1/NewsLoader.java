package com.example.android.newsapppart1;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.newsapppart1.NewsData;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsData>> {

    private static final String LOG_TAG_MSSG = NewsLoader.class.getName();

    private String newsUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        newsUrl = url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
    }

    @Override
    public List<NewsData> loadInBackground() {
        //If url is null, return null and don't try to fetch API data
        if (newsUrl == null) {
            return null;
        }
        //Fetch the News data - perform a network request, parse it, and extract the lise of news from the raw JSON data
        List<NewsData> newsList = QueryUtils.fetchNewsData(newsUrl);
        return newsList;
    }
}