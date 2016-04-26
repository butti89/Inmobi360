package lens.inmo360.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class SyncActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://inmobi360demo.azurewebsites.net/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        final ListView propertyBaseList = (ListView)findViewById(R.id.propertyBaseList);

        HttpManager httpManager = HttpManager.getInstance(this);

        PropertyAPIInterface apiService =
                httpManager.getRetrofit().create(PropertyAPIInterface.class);

        Call<List<Property>> call = apiService.getAllPropertiesForCompany("1");

        call.enqueue(new Callback<List<Property>>() {
            @Override
            public void onResponse(Call<List<Property>> call, Response<List<Property>> response) {
                int statusCode = response.code();
                List<Property> properties = response.body();

                final BasePropertyAdapter adapter = new BasePropertyAdapter(getApplicationContext(), properties);
                propertyBaseList.setAdapter(adapter);

                // TEST DOWNLOADING IMAGE
                DownloadImageTask task = new DownloadImageTask();
                task.execute(properties.get(1));

                propertyBaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
//                        final String item = (String) parent.getItemAtPosition(position);
//                        view.animate().setDuration(2000).alpha(0)
//                                .withEndAction(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        list.remove(item);
//                                        adapter.notifyDataSetChanged();
//                                        view.setAlpha(1);
//                                    }
//                                });
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Property>> call, Throwable t) {
                // Log error here since request failed
                Log.d("Error en call", call.toString());
            }
        });
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
            return syncManager.downloadPropertyImage(getApplicationContext(),
                    params[0].getImages().get(1), params[0].getAddress());
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d("Downloaded file: ", success.toString());
        }
    }
}
