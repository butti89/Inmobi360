package lens.inmo360.views;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lens.inmo360.R;
import lens.inmo360.adapters.BasePropertyAdapter;
import lens.inmo360.helpers.ModelHelper;
import lens.inmo360.managers.CouchBaseManager;
import lens.inmo360.managers.HttpManager;
import lens.inmo360.managers.SyncManager;
import lens.inmo360.model.Property;
import lens.inmo360.model.PropertyAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by estebanbutti on 4/26/16.
 */
public class SyncFragment extends android.support.v4.app.Fragment {

//    @BindView(R.id.propertyBaseList)
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton downloadFloatingButton;
    private FloatingActionButton deleteFloatingButton;
    BasePropertyAdapter adapter;
    MaterialDialog loadingDialog;
    SyncManager mSyncManager = new SyncManager();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sync, container, false);

//        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.propertyBaseList);

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

        downloadFloatingButton = (FloatingActionButton) view.findViewById(R.id.downloadFloatingButton);
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
                        ArrayList<Property> properties = ((BasePropertyAdapter) mAdapter)
                                .getProperties();

                        for (int i = 0; i < properties.size(); i++) {
                            Property property = properties.get(i);
                            if (property.isDownloaded()) {
                                selectedProperties.add(property);
                            }
                        }

                        mSyncManager.downloadProperties(getActivity(), selectedProperties);


                        for (int i = 0; i < selectedProperties.size(); i++) {
                            ModelHelper.save(CouchBaseManager.GetDataBase(),selectedProperties.get(i));
                        }
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

        deleteFloatingButton = (FloatingActionButton) view.findViewById(R.id.deleteFloatingButton);
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

        // When item is tapped, toggle checked properties of CheckBox and Property.
//        propertyBaseList.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View item,
//                                    int position, long id)
//            {
//                Property property = adapter.getItem(position);
//                property.toggleIsDownloaded();
//                BasePropertyViewHolder viewHolder = (BasePropertyViewHolder) item
//                        .getTag();
//                viewHolder.getIsDownloadedCheckbox().setChecked(property.isDownloaded());
//            }
//        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {

        menu.clear();

        inflater.inflate(R.menu.menu_sync, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_sync_all) {
//
//            //SYNC ALL
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
