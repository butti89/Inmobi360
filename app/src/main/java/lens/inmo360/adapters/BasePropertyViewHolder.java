package lens.inmo360.adapters;

/**
 * Created by estebanbutti on 4/27/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import lens.inmo360.R;

/** Holds child views for one row. */
public class BasePropertyViewHolder extends RecyclerView.ViewHolder
{
    protected TextView propertyTitle;
    protected CheckBox isDownloadedCheckbox;
    protected TextView propertyAddress;

    public BasePropertyViewHolder(View itemLayoutView) {
        super(itemLayoutView);

        propertyTitle = (TextView) itemLayoutView.findViewById(R.id.propertyTitle);
        propertyAddress = (TextView) itemLayoutView.findViewById(R.id.propertyAddress);
        isDownloadedCheckbox = (CheckBox) itemLayoutView
                .findViewById(R.id.isDownloadedCheckBox);

    }

    public CheckBox getIsDownloadedCheckbox()
    {
        return isDownloadedCheckbox;
    }

    public void setIsDownloadedCheckbox(CheckBox isDownloadedCheckbox)
    {
        this.isDownloadedCheckbox = isDownloadedCheckbox;
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
