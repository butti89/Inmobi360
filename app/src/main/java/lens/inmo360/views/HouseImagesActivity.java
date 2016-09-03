package lens.inmo360.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import lens.inmo360.R;
import lens.inmo360.adapters.PropertyImagesAdapter;
import lens.inmo360.daos.PropertiesDAO;
import lens.inmo360.model.Property;
import lens.inmo360.model.PropertyImage;

public class HouseImagesActivity extends AppCompatActivity {

    public static final String HOUSE_ID_EXTRA = "house_id";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_images);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        mRecyclerView = (RecyclerView) findViewById(R.id.houseImagesList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        String id = intent.getStringExtra(HOUSE_ID_EXTRA);

        Property property = PropertiesDAO.getById(id);
        ArrayList<PropertyImage> images = property.getImages();
        getSupportActionBar().setTitle(property.getTitle());

//        if(images.size() <= 0){
//            RelativeLayout card = (RelativeLayout)findViewById(R.id.no_images_in_card_layout);
//            if(card != null){
//                card.setVisibility(View.VISIBLE);
//            }
//        }

        // create an Object for Adapter
        mAdapter = new PropertyImagesAdapter(getApplicationContext(),images);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
    }
}
