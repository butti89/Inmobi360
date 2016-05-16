package lens.inmo360.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.jdeferred.DoneCallback;
import org.jdeferred.Promise;

import java.util.ArrayList;
import java.util.List;

import lens.inmo360.R;
import lens.inmo360.adapters.BasePropertyAdapter;
import lens.inmo360.daos.PropertiesDAO;
import lens.inmo360.managers.SyncManager;
import lens.inmo360.model.Property;

/**
 * Created by estebanbutti on 5/9/16.
 */
public class SyncLocalDataFragment extends android.support.v4.app.Fragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton deleteFloatingButton;
    MaterialDialog loadingDialog;
    SyncManager mSyncManager = new SyncManager();
    ArrayList<Property> mProperties;
    Boolean mViewLoaded = false;
    FragmentUpdateListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sync_tab_local, container, false);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (FragmentUpdateListener) getActivity();
//        } catch (ClassCastException e) {
//            throw new ClassCastException(getActivity().toString()
//                    + " must implement OnHeadlineSelectedListener");
//        }

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
                .cancelable(false)
                .progressIndeterminateStyle(true)
                .build();
        loadingDialog.show();

        mProperties = PropertiesDAO.GetAll();

        mAdapter = new BasePropertyAdapter(mProperties);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        mViewLoaded = true;
        loadingDialog.dismiss();

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
                    List<Property> properties = ((BasePropertyAdapter) mAdapter).getProperties();

                    for (int i = 0; i < properties.size(); i++) {
                        Property property = properties.get(i);
                        if (property.isDownloaded()) {
                            selectedProperties.add(property);
                        }
                    }

                    Promise promise = mSyncManager.deleteProperties(getActivity(), selectedProperties);
                    promise.done(new DoneCallback() {
                        @Override
                        public void onDone(Object result) {
                            if((Boolean) result){
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
        mProperties = PropertiesDAO.GetAll();

        mAdapter = new BasePropertyAdapter(mProperties);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mViewLoaded) {
            updateProperties();
        }else{
            // fragment is no longer visible
        }
    }

    // Container Activity must implement this interface
    public interface FragmentUpdateListener {
        public void onPropertiesDeleted(ArrayList<Property> properties);
    }
}
