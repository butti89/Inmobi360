package lens.inmo360.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import lens.inmo360.R;
import lens.inmo360.model.Property;

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

    // Create new views
    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
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
    public void onBindViewHolder(PropertyViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.propertyTitle.setText(mProperties.get(position).getTitle());

        viewHolder.propertyAddress.setText(mProperties.get(position).getAddress());

        String path = mProperties.get(position).getImages().get(0).getLocalPath();
        File pathFile = new File(path);
        Glide.with(mContext)
                .load(pathFile)
        .into(viewHolder.propertyImage);

//        viewHolder.propertyImage.(mProperties.get(position).getImages().get(0));

//        viewHolder.isDownloadedCheckbox.setTag(mProperties.get(position));


//        viewHolder.isDownloadedCheckbox.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                CheckBox cb = (CheckBox) v;
//                Property property = (Property) cb.getTag();
//
//                property.setIsDownloaded(cb.isChecked());
//                mProperties.get(pos).setIsDownloaded(cb.isChecked());
//            }
//        });

    }
}

