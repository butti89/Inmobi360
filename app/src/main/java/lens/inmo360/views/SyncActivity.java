//package lens.inmo360.views;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import lens.inmo360.R;
//import lens.inmo360.managers.SyncManager;
//import lens.inmo360.model.Property;
//
//public class SyncActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sync);
//    }
//
//    public boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
//    }
//
//    private class DownloadImageTask extends AsyncTask<Property, Void,
//            Boolean> {
//        SyncManager syncManager;
//
//        @Override
//        protected void onPreExecute() {
//            syncManager = new SyncManager();
//        }
//
//        @Override
//        protected Boolean doInBackground(Property... params) {
//            return syncManager.downloadPropertyImage(params[0].getImages().get(1), params[0].getAddress());
//        }
//
//        @Override
//        protected void onPostExecute(Boolean success) {
//            Log.d("Downloaded file: ", success.toString());
//        }
//    }
//}
