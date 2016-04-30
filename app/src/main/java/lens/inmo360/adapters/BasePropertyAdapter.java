package lens.inmo360.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

import lens.inmo360.R;
import lens.inmo360.model.Property;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class BasePropertyAdapter extends RecyclerView.Adapter<BasePropertyViewHolder> {
    private Context context;
    private ArrayList<Property> mProperties;
    private BasePropertyViewHolder viewHolder;

    public BasePropertyAdapter(ArrayList<Property> properties) {
        this.mProperties = properties;
    }

    // Create new views
    @Override
    public BasePropertyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.base_property_row, null);

        // create ViewHolder
        BasePropertyViewHolder viewHolder = new BasePropertyViewHolder(itemLayoutView);

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
    public void onBindViewHolder(BasePropertyViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.propertyTitle.setText(mProperties.get(position).getTitle());

        viewHolder.propertyAddress.setText(mProperties.get(position).getAddress());

        viewHolder.isDownloadedCheckbox.setChecked(mProperties.get(position).isDownloaded());

        viewHolder.isDownloadedCheckbox.setTag(mProperties.get(position));


        viewHolder.isDownloadedCheckbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Property property = (Property) cb.getTag();

                property.setIsDownloaded(cb.isChecked());
                mProperties.get(pos).setIsDownloaded(cb.isChecked());
            }
        });

    }
}

