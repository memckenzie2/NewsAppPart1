package com.example.android.newsapppart1;

/**
 * This TopicAdapter class is based upon the FragmentPafeAdapter class introduced in the Miwok App.
 * Source Code found here: https://github.com/udacity/ud839_Miwok
 * <p>
 * Also based upon previously submitted AudioBook and Library Tour App. Source Code found
 * * here: https://github.com/memckenzie2/AudiobookApp
 * * and here: https://github.com/memckenzie2/CentralLibraryTour
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TopicAdapter extends FragmentPagerAdapter {
    private Context myContext;

    public TopicAdapter(Context context, FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            //The "all" (AKA US and UK) political news fragment
            return new PoliticsFragment();
        } else if (position == 1) {
            //The U.S. politics fragment
            return new USPoliticsFragment();
        } else {
            //The UK politics fragment
            return new UKPoliticsFragment();
        }
    }

    //Three fragments, one for each news category
    @Override
    public int getCount() {
        return 3;
    }

    //Points each position to the appropriate news category string
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return myContext.getString(R.string.politics);
        } else if (position == 1) {
            return myContext.getString(R.string.us_politics);
        } else {
            return myContext.getString(R.string.uk_politics);
        }
    }

}
