package lens.inmo360.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import lens.inmo360.R;
import lens.inmo360.model.Property;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class BasePropertyAdapter extends ArrayAdapter<Property> {
    private final Context context;
    private final List<Property> mProperties;

    public BasePropertyAdapter(Context context, List<Property> values) {
        super(context, -1, values);
        this.context = context;
        this.mProperties = values;
    }

    static class ViewHolder {
        protected TextView propertyTitle;
        protected CheckBox isDownloadedCheckbox;
        protected TextView propertyAddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.base_property_row, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.propertyTitle = (TextView) rowView.findViewById(R.id.propertyTitle);
            viewHolder.propertyAddress = (TextView) rowView.findViewById(R.id.propertyAddress);
            viewHolder.isDownloadedCheckbox = (CheckBox) rowView.findViewById(R.id.isDownloadedCheckBox);

            viewHolder.isDownloadedCheckbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            Property element = (Property) viewHolder.isDownloadedCheckbox.getTag();
                            element.setIsDownloaded(buttonView.isChecked());

                        }
                    });
            rowView.setTag(viewHolder);
            viewHolder.isDownloadedCheckbox.setTag(mProperties.get(position));
        } else {
            rowView = convertView;
            ((ViewHolder) rowView.getTag()).isDownloadedCheckbox.setTag(mProperties.get(position));
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.propertyTitle.setText(mProperties.get(position).getTitle());
        holder.propertyAddress.setText(mProperties.get(position).getAddress());
        holder.isDownloadedCheckbox.setChecked(mProperties.get(position).isDownloaded());

        return rowView;
    }
}