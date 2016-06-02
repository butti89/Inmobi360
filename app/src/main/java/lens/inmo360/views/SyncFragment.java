package lens.inmo360.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import lens.inmo360.R;
import lens.inmo360.adapters.SyncTabsAdapter;

/**
 * Created by estebanbutti on 4/26/16.
 */
public class SyncFragment extends android.support.v4.app.Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static SyncTabsAdapter mAdapter;
    public static int selectedTabIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sync_fragment, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            selectedTabIndex = getArguments().getInt("selectedTab",-1);
        }
        
        if(getActivity().getActionBar() != null){
            getActivity().getActionBar().setElevation(0);
        }

        tabLayout = (TabLayout) view.findViewById(R.id.sync_tabs);
        viewPager = (ViewPager) view.findViewById(R.id.sync_viewpager);

        mAdapter = new SyncTabsAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(0);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);

                if(selectedTabIndex > -1){
                    TabLayout.Tab tab = tabLayout.getTabAt(selectedTabIndex);
                    if(tab != null){
                        tab.select();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        super.onCreateOptionsMenu(menu,inflater);
    }
}
