package lens.inmo360.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import lens.inmo360.R;
import lens.inmo360.model.Property;
import lens.inmo360.views.HouseImagesActivity;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class PropertyAdapter extends RecyclerView.Adapter<PropertyViewHolder> {
    private Context mContext;
    private ArrayList<Property> mProperties;
    private PropertyViewHolder viewHolder;

    public PropertyAdapter(Context ctx, ArrayList<Property> properties) {
        mContext = ctx;
        this.mProperties = properties;
    }

    @Override
    public void onViewRecycled(PropertyViewHolder holder) {
        Glide.clear(holder.getPropertyImage());
        super.onViewRecycled(holder);

    }

    // Create new views
    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.property_list_row, null);

        // create ViewHolder
        PropertyViewHolder viewHolder = new PropertyViewHolder(itemLayoutView);

        return viewHolder;
    }

    // method to access in activity after updating selection
    public ArrayList<Property> getProperties() {
        return mProperties;
    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return mProperties.size();
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder viewHolder, final int position) {

        viewHolder.propertyTitle.setText(mProperties.get(position).getTitle());

        viewHolder.propertyAddress.setText(mProperties.get(position).getAddress());

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, HouseImagesActivity.class);
                intent.putExtra(HouseImagesActivity.HOUSE_ID_EXTRA, mProperties.get(position).getId().toString());
                context.startActivity(intent);
            }
        });

        if(mProperties.get(position).getImages().size() > 0){
            String path = mProperties.get(position).getImages().get(0).getLocalPath();
            File pathFile = new File(path);

            Glide.with(mContext)
                    .load(pathFile)
                    .skipMemoryCache(true)
                    .override(1024,576)
                    .into(viewHolder.propertyImage);
        }else{
            viewHolder.noImageLayout.setVisibility(View.VISIBLE);
        }
    }
}

