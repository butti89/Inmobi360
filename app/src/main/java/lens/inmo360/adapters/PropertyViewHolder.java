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
public class PropertyViewHolder extends RecyclerView.ViewHolder
{
    protected TextView propertyTitle;
    protected ImageView propertyImage;
    protected TextView propertyAddress;
    protected View mView;

    public PropertyViewHolder(View itemLayoutView) {
        super(itemLayoutView);

        mView = itemLayoutView;
        propertyTitle = (TextView) itemLayoutView.findViewById(R.id.property_title);
        propertyAddress = (TextView) itemLayoutView.findViewById(R.id.property_address);
        propertyImage = (ImageView) itemLayoutView.findViewById(R.id.property_image);
    }

    public ImageView getPropertyImage() {
        return propertyImage;
    }

    public void setPropertyImage(ImageView propertyImage) {
        this.propertyImage = propertyImage;
    }

    public TextView getPropertyTitle()
    {
        return propertyTitle;
    }

    public void setPropertyTitle(TextView propertyTitle)
    {
        this.propertyTitle = propertyTitle;
    }

    public TextView getPropertyAddress()
    {
        return propertyAddress;
    }

    public void setPropertyAddress(TextView propertyAddress)
    {
        this.propertyAddress = propertyAddress;
    }

}
