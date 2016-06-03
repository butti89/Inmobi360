package lens.inmo360.adapters;

/**
 * Created by estebanbutti on 4/27/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lens.inmo360.R;

/** Holds child views for one row. */
public class PropertyImageViewHolder extends RecyclerView.ViewHolder
{
    protected TextView imageTitle;
    protected ImageView image;
    protected View mView;

    public PropertyImageViewHolder(View itemLayoutView) {
        super(itemLayoutView);

        mView = itemLayoutView;
        imageTitle = (TextView) itemLayoutView.findViewById(R.id.property_image_title);
        image = (ImageView) itemLayoutView.findViewById(R.id.image_card_image);

    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(TextView imageTitle) {
        this.imageTitle = imageTitle;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }
}
