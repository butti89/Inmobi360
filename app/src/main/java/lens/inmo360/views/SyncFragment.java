package lens.inmo360.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import lens.inmo360.R;
import lens.inmo360.managers.SyncManager;
import lens.inmo360.model.Property;

/**
 * Created by estebanbutti on 4/26/16.
 */
public class SyncFragment extends android.support.v4.app.Fragment {

//    @BindView(R.id.propertyBaseList)
//    ListView propertyBaseList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sync, container, false);

//        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

//        final ListView propertyBaseList = (ListView) view.findViewById(R.id.propertyBaseList);
//
//        HttpManager httpManager = HttpManager.getInstance(getActivity().getApplicationContext());
//
//        PropertyAPIInterface apiService =
//                httpManager.getRetrofit().create(PropertyAPIInterface.class);
//
//        Call<List<Property>> call = apiService.getAllPropertiesForCompany("1");
//
//        call.enqueue(new Callback<List<Property>>() {
//            @Override
//            public void onResponse(Call<List<Property>> call, Response<List<Property>> response) {
//                int statusCode = response.code();
//                List<Property> properties = response.body();
//
//                final BasePropertyAdapter adapter = new BasePropertyAdapter(getActivity().getApplicationContext(), properties);
//                propertyBaseList.setAdapter(adapter);
//
//                // TEST DOWNLOADING IMAGE
//                DownloadImageTask task = new DownloadImageTask();
//                task.execute(properties.get(1));
//
//                propertyBaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, final View view,
//                                            int position, long id) {
//                        //                        final String item = (String) parent.getItemAtPosition(position);
//                        //                        view.animate().setDuration(2000).alpha(0)
//                        //                                .withEndAction(new Runnable() {
//                        //                                    @Override
//                        //                                    public void run() {
//                        //                                        list.remove(item);
//                        //                                        adapter.notifyDataSetChanged();
//                        //                                        view.setAlpha(1);
//                        //                                    }
//                        //                                });
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<List<Property>> call, Throwable t) {
//                // Log error here since request failed
//                Log.d("Error en call", call.toString());
//            }
//        });

        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

//        HttpManager httpManager = HttpManager.getInstance(getActivity().getApplicationContext());
//
//        PropertyAPIInterface apiService =
//                httpManager.getRetrofit().create(PropertyAPIInterface.class);
//
//        Call<List<Property>> call = apiService.getAllPropertiesForCompany("1");
//
//        call.enqueue(new Callback<List<Property>>() {
//            @Override
//            public void onResponse(Call<List<Property>> call, Response<List<Property>> response) {
//                int statusCode = response.code();
//                List<Property> properties = response.body();
//
//                final BasePropertyAdapter adapter = new BasePropertyAdapter(getActivity().getApplicationContext(), properties);
//                propertyBaseList.setAdapter(adapter);
//
//                // TEST DOWNLOADING IMAGE
//                DownloadImageTask task = new DownloadImageTask();
//                task.execute(properties.get(1));
//
//                propertyBaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, final View view,
//                                            int position, long id) {
//                        //                        final String item = (String) parent.getItemAtPosition(position);
//                        //                        view.animate().setDuration(2000).alpha(0)
//                        //                                .withEndAction(new Runnable() {
//                        //                                    @Override
//                        //                                    public void run() {
//                        //                                        list.remove(item);
//                        //                                        adapter.notifyDataSetChanged();
//                        //                                        view.setAlpha(1);
//                        //                                    }
//                        //                                });
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<List<Property>> call, Throwable t) {
//                // Log error here since request failed
//                Log.d("Error en call", call.toString());
//            }
//        });
//    }

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
        if (id == R.id.action_sync_all) {

            //SYNC ALL


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private class DownloadImageTask extends AsyncTask<Property, Void,
            Boolean> {
        SyncManager syncManager;

        @Override
        protected void onPreExecute() {
            syncManager = new SyncManager();
        }

        @Override
        protected Boolean doInBackground(Property... params) {
            return syncManager.downloadPropertyImage(getActivity().getApplicationContext(),
                    params[0].getImages().get(1), params[0].getAddress());
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d("Downloaded file: ", success.toString());
        }
    }
}
