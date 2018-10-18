package com.example.android.newsapppart1;

/**
 * This fragment presents only US Politics in its list
 * Implementation based on the Soonami and Earthquake app's MainActivity classes from the udacity nano-degree.
 * The source code for those projects can be found here:
 * https://github.com/udacity/ud843_Soonami/blob/solution/app/src/main/java/
 * and here:
 * https://github.com/udacity/ud843-QuakeReport
 * <p>
 * This Fragment class is based upon the fragments examples as introduced for the Miwok App.
 * Source Code found here: https://github.com/udacity/ud839_Miwok
 */


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class USPoliticsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsData>> {


    //The url String containing the Guardian API call
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/us-news/us-politics?order-by=newest&show-tags=contributor&api-key=df012d12-90e6-43da-8efc-9d3771d6956c\n";

    //Constant value for the newsloader ID in case we want to use multiple loaders in future.
    private static final int NEWS_LOADER_ID = 1;
    //The newsArrayAdapter inside the PoliticsFragment
    private NewsArrayAdapter newsAdapter;
    private View rootView;
    private ListView listView;

    /**
     * TextView that is displayed when the news list is empty
     */
    private TextView emptyStateView;

    public USPoliticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_list, container, false);
        listView = rootView.findViewById(R.id.news_list);

        //Setup empty state view
        emptyStateView = rootView.findViewById(R.id.empty_state);
        listView.setEmptyView(emptyStateView);

        //Create a {@link NewsArrayAdapter} with a list of {@link NewsData}s
        newsAdapter = new NewsArrayAdapter(getActivity(), new ArrayList<NewsData>());

        //Find the ListView object from news_list.xml layout file
        listView = rootView.findViewById(R.id.news_list);

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


        //Gets the activity context so it can call connection manger to check network connection
        ConnectivityManager connectManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectManager.getActiveNetworkInfo();
        //If there is no network connectivity. Don't load list and set each to empty state
        if (netInfo != null && netInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View prog = rootView.findViewById(R.id.progressBar);
            prog.setVisibility(View.GONE);
            emptyStateView.setText(R.string.no_connection);
        }

        return rootView;
    }

    @Override
    public Loader<List<NewsData>> onCreateLoader(int id, Bundle args) {
        //Create a new NewsLoader for the Guardian Request URL
        return new NewsLoader(getContext(), GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, List<NewsData> data) {
        //Removes indeterminate progress bar from view since loading is finshed
        View prog = rootView.findViewById(R.id.progressBar);
        prog.setVisibility(View.GONE);

        //Once loading is finished, need to clear the data from the old news stories
        newsAdapter.clear();

        //If there is a list of news data (i.e. not null or empty), add it to the NewsArrayAdapter for display
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
        //Sets empty view state in case nothing is loaded
        emptyStateView.setText(R.string.no_news);

    }

    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {
        //On reset, need to clear the data from the old news stories
        newsAdapter.clear();
    }

}
