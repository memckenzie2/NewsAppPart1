package com.example.android.newsapppart1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import java.util.ArrayList;
import java.util.List;

public class PoliticsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsData>>{

    //The newsArrayAdapter inside the PoliticsFragment
    private NewsArrayAdapter newsAdapter;

    //The url String containing the Guardian API call
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/politics?&show-tags=contributor&api-key=test";

    //Constant value for the newsloader ID in case we want to use multiple loaders in future.
     private static final int NEWS_LOADER_ID = 1;

    private View rootView;
    private ListView listView;



    public PoliticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.news_list);

        //Create a {@link NewsArrayAdapter} with a list of {@link NewsData}s
        newsAdapter = new NewsArrayAdapter(getActivity(), new ArrayList<NewsData>());

        //Find the ListView object from news_list.xml layout file
        listView = (ListView) rootView.findViewById(R.id.news_list);

        //Point the listview to the LocationArrayAdapter we created above so it displays our list of locations
        listView.setAdapter(newsAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open the news story on the guardian website using the user's preferred web browser
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news story that was clicked on
                NewsData currentStory = newsAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsStoryUri = Uri.parse(currentStory.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsStoryUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<List<NewsData>> onCreateLoader(int id, Bundle args) {
        //Create a new NewsLoader for the Guardian Request URL
        return new NewsLoader(getContext(), GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, List<NewsData> data) {

        //Once loading is finished, need to clear the data from the old news stories
        newsAdapter.clear();

        //If there is a list of news data (i.e. not null or empty), add it to the NewsArrayAdapter for display
        if (data != null && !data.isEmpty()){
            newsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {
        //On reset, need to clear the data from the old news stories
        newsAdapter.clear();
    }

}
