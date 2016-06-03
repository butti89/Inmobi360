package lens.inmo360.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.jdeferred.DoneCallback;
import org.jdeferred.Promise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lens.inmo360.R;
import lens.inmo360.adapters.BasePropertyAdapter;
import lens.inmo360.daos.PropertiesDAO;
import lens.inmo360.helpers.DialogHelper;
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
public class SyncServerDataFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton downloadFloatingButton;
    MaterialDialog loadingDialog;
    SyncManager mSyncManager = new SyncManager();
    ArrayList<Property> mProperties;
    String companyID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sync_tab_server, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.server_property_base_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadingDialog = new MaterialDialog.Builder(getActivity())
            .title(R.string.downloading)
            .content(R.string.please_wait)
            .progress(true, 0)
            .cancelable(false)
            .progressIndeterminateStyle(true)
            .build();
        loadingDialog.show();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        companyID = sharedPreferences.getString("CompanyID", null);

        if(companyID != null){
            updateProperties();
        }else{
            loadingDialog.dismiss();
            DialogHelper.showInvalidCompanyIDDialog(getActivity());
        }


        downloadFloatingButton = (FloatingActionButton) view.findViewById(R.id.download_floating_button);
        downloadFloatingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            new MaterialDialog.Builder(getActivity())
                .title(R.string.syncDialogTitle)
                .content(R.string.downloadConfirmationContent)
                .positiveText(R.string.downloadConfirmationPositiveButton)
                .negativeText(R.string.cancelNegativeButton)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    ArrayList<Property> selectedProperties = new ArrayList<>();
                    ArrayList<Property> properties = ((BasePropertyAdapter) mAdapter).getProperties();

                    for (int i = 0; i < properties.size(); i++) {
                        Property property = properties.get(i);
                        if (property.isDownloaded()) {
                            selectedProperties.add(property);
                        }
                    }

                    Promise promise = mSyncManager.downloadProperties(getActivity(), selectedProperties);
                    promise.done(new DoneCallback() {
                        @Override
                        public void onDone(Object result) {
                            if((Boolean) result){
                                loadingDialog.show();
                                updateProperties();
                            }
                        }
                    });
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

    public void updateProperties(){

        HttpManager httpManager = HttpManager.getInstance();
        PropertyAPIInterface apiService =
                httpManager.getRetrofit().create(PropertyAPIInterface.class);

        Call<List<Property>> call = apiService.getAllPropertiesForCompany(companyID);

        call.enqueue(new Callback<List<Property>>() {
            @Override
            public void onResponse(Call<List<Property>> call, Response<List<Property>> response) {
                int statusCode = response.code();

                if(statusCode == 200){
                    mProperties = (ArrayList<Property>) response.body();

                    ArrayList<Property> downloadedProperties = PropertiesDAO.GetAll();

                    for (Iterator<Property> iterator = downloadedProperties.iterator(); iterator.hasNext();) {
                        Property downloadedProperty = iterator.next();
                        for (Iterator<Property> iterator2 = mProperties.iterator(); iterator2.hasNext();) {
                            Property property = iterator2.next();
                            if (downloadedProperty.getId().equals(property.getId())) {
                                // Remove the current element from the iterator and the list.
                                iterator2.remove();
                            }
                        }
                    }

                    mAdapter = new BasePropertyAdapter(mProperties);

                    // set the adapter object to the Recyclerview
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    DialogHelper.showNoConnectionDialog(getActivity());
                }

                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Property>> call, Throwable t) {
                // Log error here since request failed
                Log.d("Error en call", call.toString());

                if(t.getClass().equals(IllegalArgumentException.class)){
                    DialogHelper.showInvalidCompanyIDDialog(getActivity());
                }else{
                    DialogHelper.showNoConnectionDialog(getActivity());
                }
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            updateProperties();
        }else{
            // fragment is no longer visible
        }
    }

    // Container Activity must implement this interface
    public interface ServerFragmentUpdateListener {
        public void onPropertiesAdded();
    }
}
