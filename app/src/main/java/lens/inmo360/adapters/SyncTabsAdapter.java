package lens.inmo360.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import lens.inmo360.views.SyncLocalDataFragment;
import lens.inmo360.views.SyncServerDataFragment;

/**
 * Created by estebanbutti on 5/9/16.
 */
public class SyncTabsAdapter extends FragmentPagerAdapter {

    public static int int_items = 2 ;
//    ArrayList<Property> propertiesToUpdate;

    public SyncTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return fragment with respect to Position .
     */

    @Override
    public Fragment getItem(int position)
    {
        switch (position){
            case 0 : return new SyncLocalDataFragment();
            case 1 : return new SyncServerDataFragment();
        }
        return null;
    }

    @Override
    public int getCount() {

        return int_items;

    }

    /**
     * This method returns the title of the tab according to the position.
     */

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0 :
                return "Downloaded";
            case 1 :
                return "Not downloaded";
        }
        return null;
    }
}
