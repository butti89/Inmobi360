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

        if(mProperties.get(position).getDescription() != null){
            viewHolder.detailsDescription.setText(String.format(viewHolder.detailsDescription.getText().toString(), mProperties.get(position).getDescription()));
        }
        else {
            viewHolder.detailsDescription.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getProvince() != null){
            String pName = mProperties.get(position).getProvince().getName();
            viewHolder.detailsProvince.setText(String.format(viewHolder.detailsProvince.getText().toString(), pName));
        }
        else{
            viewHolder.detailsProvince.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getCity() != null){
            String cName = mProperties.get(position).getCity().getName();
            viewHolder.detailsCity.setText(String.format(viewHolder.detailsCity.getText().toString(), cName));
        }
        else{
            viewHolder.detailsCity.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getType() != null){
            viewHolder.detailsType.setText(String.format(viewHolder.detailsType.getText().toString(), mProperties.get(position).getType()));
        }
        else{
            viewHolder.detailsType.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getRooms() != null && mProperties.get(position).getRooms() != 0){
            String rooms = mProperties.get(position).getRooms().toString();
            viewHolder.detailsRooms.setText(String.format(viewHolder.detailsRooms.getText().toString(), rooms));
        }
        else{
            viewHolder.detailsRooms.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getSquaredMeters() != null && mProperties.get(position).getSquaredMeters() != 0){
            String meters = mProperties.get(position).getSquaredMeters().toString();
            viewHolder.detailsMeters.setText(String.format(viewHolder.detailsMeters.getText().toString(),meters));
        }
        else{
            viewHolder.detailsMeters.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getHasGarage() != null){
            Boolean hGarage = mProperties.get(position).getHasGarage();
            String hG;
            if(hGarage){
                hG = "Si";
            }
            else {
                hG = "No";
            }
            viewHolder.detailsGarage.setText(String.format(viewHolder.detailsGarage.getText().toString(), hG));
        }
        else{
            viewHolder.detailsGarage.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getAntiquity() != null && mProperties.get(position).getAntiquity() != 0){
            String ant = mProperties.get(position).getAntiquity().toString();
            viewHolder.detailsYear.setText(String.format(viewHolder.detailsYear.getText().toString(), ant));
        }
        else{
            viewHolder.detailsYear.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getOperation() != null){
            viewHolder.detailsOperation.setText(String.format(viewHolder.detailsOperation.getText().toString(), mProperties.get(position).getOperation()));
        }
        else{
            viewHolder.detailsOperation.setVisibility(View.GONE);
        }

        if(mProperties.get(position).getPrice() != null && mProperties.get(position).getPrice() != 0){
            String price = mProperties.get(position).getPrice().toString();
            viewHolder.detailsPrice.setText(String.format(viewHolder.detailsPrice.getText().toString(), price));
        }
        else{
            viewHolder.detailsPrice.setVisibility(View.GONE);
        }

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

