package lens.inmo360.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import lens.inmo360.MainActivity;
import lens.inmo360.R;
import lens.inmo360.adapters.PropertyAdapter;
import lens.inmo360.daos.PropertiesDAO;
import lens.inmo360.model.Property;

/**
 * Created by estebanbutti on 4/26/16.
 */
public class MainFragment extends android.support.v4.app.Fragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private View view;
    private Activity activity;
    private ArrayList<Property> properties = new ArrayList<>();

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        properties = PropertiesDAO.GetAll();

        if(properties.size() > 0){
            view = inflater.inflate(R.layout.content_main, container, false);

            mRecyclerView = (RecyclerView) view.findViewById(R.id.availablePropertiesList);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // create an Object for Adapter
            mAdapter = new PropertyAdapter(getActivity().getApplicationContext(),properties);

            // set the adapter object to the Recyclerview
            mRecyclerView.setAdapter(mAdapter);
        }else{
            view = inflater.inflate(R.layout.empty_house_list_card, container, false);

            Button syncButton = (Button) view.findViewById(R.id.noHousesSyncButton);

            syncButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create a new fragment and specify the fragment to show based on nav item clicked
                    Fragment fragment = null;

                    try {
                        fragment = (Fragment) SyncFragment.class.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ((MainActivity) getActivity()).onSyncFragmentSelected();
                    // Insert the fragment by replacing any existing fragment

                    Bundle bundle = new Bundle();
                    bundle.putInt("selectedTab", 1);
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentsContainer, fragment).commit();
                }
            });
        }


//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getApplicationContext(),CardboardViewActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    public interface OnSyncFragmentSelectedListener{
        public void onSyncFragmentSelected();
    }

    public void UpdatePropertiesToShow(ArrayList<Property> props){
        // create an Object for Adapter
        mAdapter = new PropertyAdapter(getActivity().getApplicationContext(),props);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }
    
    public ArrayList<String> getPropertiesProvince(){
        ArrayList<String> locations = new ArrayList<>();

        for (Property p: properties){
            if(p.getProvince() != null && !locations.contains(p.getProvince().getName())){
                locations.add(p.getProvince().getName());
            }
        }
        return locations;
    }

    public ArrayList<String> getPropertiesCities(){
        ArrayList<String> locations = new ArrayList<>();

        for (Property p: properties){
            if(p.getCity() != null && !locations.contains(p.getCity().getName())){
                locations.add(p.getCity().getName());
            }
        }
        return locations;
    }

    public ArrayList<String> getPropertiesCitiesByProvince(String prov){
        ArrayList<String> locations = new ArrayList<>();

        for (Property p: properties){
            if(p.getProvince() != null && p.getProvince().getName().equals(prov)){
                if(p.getCity() != null && !locations.contains(p.getCity().getName())){
                    locations.add(p.getCity().getName());
                }
            }
        }
        return locations;
    }
}
