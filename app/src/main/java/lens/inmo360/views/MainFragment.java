package lens.inmo360.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.availablePropertiesList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Property> properties = PropertiesDAO.GetAll();

        // create an Object for Adapter
        mAdapter = new PropertyAdapter(getActivity().getApplicationContext(),properties);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(),CardboardViewActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
