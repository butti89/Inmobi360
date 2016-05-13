package lens.inmo360.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import lens.inmo360.R;
import lens.inmo360.adapters.BasePropertyAdapter;
import lens.inmo360.managers.HttpManager;
import lens.inmo360.managers.SyncManager;
import lens.inmo360.model.Property;
import lens.inmo360.model.PropertyAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by estebanbutti on 5/9/16.
 */
public class SyncLocalDataFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton downloadFloatingButton;
    private FloatingActionButton deleteFloatingButton;
    MaterialDialog loadingDialog;
    SyncManager mSyncManager = new SyncManager();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sync_tab_local, container, false);

//        ButterKnife.bind(this, view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.local_property_base_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadingDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.downloading)
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();
        loadingDialog.show();

        HttpManager httpManager = HttpManager.getInstance();
        PropertyAPIInterface apiService =
                httpManager.getRetrofit().create(PropertyAPIInterface.class);

        Call<List<Property>> call = apiService.getAllPropertiesForCompany("1");

        call.enqueue(new Callback<List<Property>>() {
            @Override
            public void onResponse(Call<List<Property>> call, Response<List<Property>> response) {
                int statusCode = response.code();
                ArrayList<Property> properties = (ArrayList<Property>) response.body();

                // create an Object for Adapter
                mAdapter = new BasePropertyAdapter(properties);

                // set the adapter object to the Recyclerview
                mRecyclerView.setAdapter(mAdapter);

                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Property>> call, Throwable t) {
                // Log error here since request failed
                Log.d("Error en call", call.toString());
            }
        });

        deleteFloatingButton = (FloatingActionButton) view.findViewById(R.id.delete_floating_button);
        deleteFloatingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(getActivity())
                        .title(R.string.syncDialogTitle)
                        .content(R.string.deleteConfirmationContent)
                        .positiveText(R.string.deleteConfirmationPositiveButton)
                        .negativeText(R.string.cancelNegativeButton)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ArrayList<Property> selectedProperties = new ArrayList<>();
                                List<Property> properties = ((BasePropertyAdapter) mAdapter)
                                        .getProperties();

                                for (int i = 0; i < properties.size(); i++) {
                                    Property property = properties.get(i);
                                    if (property.isDownloaded()) {
                                        selectedProperties.add(property);
                                    }
                                }

                                mSyncManager.deleteProperties(getActivity(), selectedProperties);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        return view;
    }
}
