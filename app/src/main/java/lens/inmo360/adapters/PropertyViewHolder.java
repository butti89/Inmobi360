package lens.inmo360.adapters;

/**
 * Created by estebanbutti on 4/27/16.
 */

import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lens.inmo360.R;
import lens.inmo360.helpers.ExpandAndCollapseViewUtil;

/** Holds child views for one row. */
public class PropertyViewHolder extends RecyclerView.ViewHolder
{

    private static final int DURATION = 250;

    @Bind(R.id.property_title)
    TextView propertyTitle;
    @Bind(R.id.property_image)
    ImageView propertyImage;
    @Bind(R.id.property_address)
    TextView propertyAddress;
    @Bind(R.id.no_image_layout)
    RelativeLayout noImageLayout;
    @Bind(R.id.property_details)
    RelativeLayout detailsLayout;

    @Bind(R.id.property_more_details)
    LinearLayout moreDetailsLayout;
    @Bind(R.id.property_details_img)
    ImageView propertyDetailsImage;

    @Bind(R.id.property_details_description)
    TextView detailsDescription;
    @Bind(R.id.property_details_province)
    TextView detailsProvince;
    @Bind(R.id.property_details_city)
    TextView detailsCity;
    @Bind(R.id.property_details_type)
    TextView detailsType;
    @Bind(R.id.property_details_rooms)
    TextView detailsRooms;
    @Bind(R.id.property_details_meters)
    TextView detailsMeters;
    @Bind(R.id.property_details_garage)
    TextView detailsGarage;
    @Bind(R.id.property_details_year)
    TextView detailsYear;
    @Bind(R.id.property_details_operation)
    TextView detailsOperation;
    @Bind(R.id.property_details_price)
    TextView detailsPrice;

    protected View mView;
    private int rotationAngle = 0;

    public PropertyViewHolder(View itemLayoutView) {
        super(itemLayoutView);

        mView = itemLayoutView;
        ButterKnife.bind(this, itemLayoutView);
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

    public void toogleDetails() {
        if (moreDetailsLayout.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(moreDetailsLayout, DURATION);
            //propertyDetailsImage.setImageResource(R.drawable.ic_keyboard_arrow_up_24dp);
            rotate();
        } else {
            ExpandAndCollapseViewUtil.collapse(moreDetailsLayout, DURATION);
            //propertyDetailsImage.setImageResource(R.drawable.ic_keyboard_arrow_up_24dp);
            rotate();
        }
    }

    public void rotate() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(propertyDetailsImage, "Rotation", rotationAngle, rotationAngle +180);
        anim.setDuration(DURATION);
        anim.start();
        rotationAngle += 180;
        rotationAngle = rotationAngle % 360;
//
//        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setFillAfter(true);
//        animation.setDuration(DURATION);
//        propertyDetailsImage.startAnimation(animation);
    }

//    @OnClick(R.id.property_details)
//    public  void onDetailsClick(){
//        toogleDetails();
//    }

    public RelativeLayout getNoImageLayout() {
        return noImageLayout;
    }

    public void setNoImageLayout(RelativeLayout noImageLayout) {
        this.noImageLayout = noImageLayout;
    }
}
