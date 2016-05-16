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
import lens.inmo360.model.PropertyImage;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class PropertyImagesAdapter extends RecyclerView.Adapter<PropertyImageViewHolder> {
    private Context mContext;
    private ArrayList<PropertyImage> mImages;
    private PropertyImageViewHolder viewHolder;

    public PropertyImagesAdapter(Context ctx, ArrayList<PropertyImage> images) {
        mContext = ctx;
        this.mImages = images;
    }

    @Override
    public void onViewRecycled(PropertyImageViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(viewHolder.getImage());
    }

    // Create new views
    @Override
    public PropertyImageViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.property_images_list_row, null);

        // create ViewHolder
        viewHolder = new PropertyImageViewHolder(itemLayoutView);

        return viewHolder;
    }

    // method to access in activity after updating selection
    public ArrayList<PropertyImage> getImages() {
        return mImages;
    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return mImages.size();
    }

    @Override
    public void onBindViewHolder(PropertyImageViewHolder viewHolder, final int position) {

        viewHolder.imageTitle.setText(mImages.get(position).getTitle());

//        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, CardboardViewActivity.class);
//                intent.putExtra(CardboardViewActivity.EXTRA_NAME, mImages.get(position).getId());
//
//                context.startActivity(intent);
//            }
//        });

        if(mImages.get(position).getLocalPath() != null){
            String path = mImages.get(position).getLocalPath();
            File pathFile = new File(path);

            Glide.with(mContext)
                    .load(pathFile)
                    .skipMemoryCache(true)
                    .override(1024,576)
                    .into(viewHolder.image);
        }
    }
}

