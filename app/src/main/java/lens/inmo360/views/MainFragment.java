package lens.inmo360.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.util.ArrayList;

import lens.inmo360.R;
import lens.inmo360.adapters.BasePropertyAdapter;
import lens.inmo360.managers.SyncManager;
import lens.inmo360.model.Property;

/**
 * Created by estebanbutti on 4/26/16.
 */
public class MainFragment extends android.support.v4.app.Fragment{

    ListView propertyBaseList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private com.github.clans.fab.FloatingActionButton downloadFloatingButton;
    private com.github.clans.fab.FloatingActionButton deleteFloatingButton;
    BasePropertyAdapter adapter;
    MaterialDialog loadingDialog;
    SyncManager mSyncManager = new SyncManager();

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

        File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        ArrayList<Property> properties = new ArrayList<Property>();
        String[] directories = externalFilesDir.list();

        for (int i = 0; i < directories.length; i++) {
            String directory = externalFilesDir.toString()+directories[i];
//            for (int j = 0; j < directory.list().length; j++) {
//                String  = directory.list()[j];
//
//            }

        }


//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), SyncActivity.class);
//                startActivity(intent);
//            }
//        });
        FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(),CardboardViewActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
