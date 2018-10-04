package com.example.android.newsapppart1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;


public class PoliticsFragment extends Fragment {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG_MSSG = MainActivity.class.getSimpleName();

    //Create list of news stories
    public static final ArrayList<NewsData> stories = new ArrayList<NewsData>();

    public PoliticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list, container, false);

        new GetGuardianNews().execute();

        //Create a {@link NewsArrayAdapter} with a list of {@link NewsData}s
        final NewsArrayAdapter adapter = new NewsArrayAdapter(getActivity(), stories);

        //Find the ListView object from news_list.xml layout file
        ListView listView = (ListView) rootView.findViewById(R.id.news_list);


        //Point the listview to the LocationArrayAdapter we created above so it displays our list of locations
        listView.setAdapter(adapter);

       // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open the news story on the guardian website using the user's preferred web browser
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news story that was clicked on
                NewsData currentStory = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsStoryUri = Uri.parse(currentStory.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsStoryUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        return rootView;
    }

    /*
        Based on function code from Pokemon App: https://github.com/udacity/Pokemon/blob/master/app/src/main/java/udacity/pokemon/MainActivity.java
     */

    private class GetGuardianNews extends AsyncTask<Void, Void, Void> {
        private static final String GUARDIAN_REQUEST_URL =
                "https://content.guardianapis.com/politics?api-key=test";

        @Override
        protected Void doInBackground(Void... arg0) {
            HTTPHandler handler = new HTTPHandler();
            URL url = handler.makeUrl(GUARDIAN_REQUEST_URL);

            String jsonRawResponse = "";
            try {
                jsonRawResponse = handler.makeHTTPRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG_MSSG, "Problem making the HTTP request to Guardian API.", e);
            }

            Log.e(LOG_TAG_MSSG, "Response from url: " + jsonRawResponse);

            if (jsonRawResponse != null) {
                try {
                    //Create list of news stories
                    final ArrayList<NewsData> stories = new ArrayList<NewsData>();
                    JSONObject newsStories = new JSONObject(jsonRawResponse);
                    JSONObject responseObject = newsStories.getJSONObject("response");
                    JSONArray resultsArray = responseObject.getJSONArray("results");

                    // If there are results in the features array
                    for (int i = 0; i < resultsArray.length(); i++){
                        // Extract out the first result(which is a single news story)
                        JSONObject results = resultsArray.getJSONObject(i);

                        // Extract out the title, wen publication date, and story url
                        String title = results.getString("webTitle");
                        String date = results.getString("webPublicationDate");
                        String webURL = results.getString("webUrl");

                        // Create a new {@link Event} object
                         PoliticsFragment.stories.add(new NewsData(title, null, date , webURL));
                    }

                } catch (JSONException e) {
                    Log.e(LOG_TAG_MSSG, "Problem parsing the Guardian API JSON results", e);
                }
            }else{
                Log.e(LOG_TAG_MSSG, "Couldn't get JSON from Guardian API server.");
            }

            return null;
        }
    }
}
