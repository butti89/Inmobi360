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
import lens.inmo360.adapters.PropertyAdapter;
import lens.inmo360.daos.PropertiesDAO;
import lens.inmo360.managers.SyncManager;
import lens.inmo360.model.Property;
import lens.inmo360.model.PropertyImage;

/**
 * Created by estebanbutti on 4/26/16.
 */
public class MainFragment extends android.support.v4.app.Fragment{

    ListView propertyBaseList;
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

        File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        ArrayList<Property> properties = PropertiesDAO.GetAll();

        /*String[] directories = externalFilesDir.list();

        if (directories != null && directories.length > 0){
            for (int i = 0; i < directories.length; i++) {
                String houseDirectory = externalFilesDir.toString()+'/'+directories[i];
                File houseDirectoryFile = new File(houseDirectory);
                String[] houseImages = houseDirectoryFile.list();
                ArrayList<PropertyImage> propertyImages = new ArrayList<>();
                Property property = new Property();
                property.setTitle("House "+ i);
                property.setAddress("Address "+ i);

                for (int j = 0; j < houseImages.length; j++) {
                    String houseImageDirectory = houseDirectoryFile.toString()+'/'+houseImages[j];
                    PropertyImage image = new PropertyImage();
                    image.setTitle(houseImages[j]);
                    image.setLocalPath(houseImageDirectory);
                    propertyImages.add(image);
                }
                property.setImages(propertyImages);
                properties.add(property);
            }
        }*/

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
