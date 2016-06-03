package lens.inmo360.managers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import org.apache.commons.io.IOUtils;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lens.inmo360.R;
import lens.inmo360.model.Property;
import lens.inmo360.model.PropertyAPIInterface;
import lens.inmo360.model.PropertyImage;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class SyncManager {
    private MaterialDialog loadingDialog;
    private File externalFilesDir;
    private CouchBaseManager couchBaseManager;
    private Deferred mDeferred;

    public boolean downloadProperty(Context ctx, Property property){
        boolean success = true;

        ArrayList<PropertyImage> images = property.getImages();

        for (int i = 0; i < images.size(); i++) {
            PropertyImage propertyImage = images.get(i);
            PropertyImage image = this.downloadPropertyImage(propertyImage, property.getAddress());
            success = success && (image != null);
        }

        return success;
    }

    public Promise downloadProperties(Context ctx, ArrayList<Property> properties){
        mDeferred = new DeferredObject();
        Promise promise = mDeferred.promise();

        loadingDialog = getLoadingDialog(ctx);
        loadingDialog.setCancelable(false);

        externalFilesDir = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        DownloadPropertiesTask task = new DownloadPropertiesTask();
        task.execute(properties);

        return promise;
    }

    public Promise deleteProperties(Context ctx, ArrayList<Property> properties){
        mDeferred = new DeferredObject();
        Promise promise = mDeferred.promise();

        loadingDialog = getLoadingDialog(ctx);
        loadingDialog.setCancelable(false);

        externalFilesDir = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        DeletePropertiesTask task = new DeletePropertiesTask();
        task.execute(properties);

        return promise;
    }

    public PropertyImage downloadPropertyImage( PropertyImage image, String propertyAddress){
        boolean success;

        HttpManager httpManager = HttpManager.getInstance();

        PropertyAPIInterface apiService =
                httpManager.getRetrofit().create(PropertyAPIInterface.class);

        Call<ResponseBody> call = apiService.downloadImage(image.getURL());

        String fileDirectory = propertyAddress.replace(" ","_").toLowerCase();

        File directory = getAlbumStorageDir(externalFilesDir,fileDirectory);

        if(!directory.exists()) {
            if(directory.mkdir()); //directory is created;
        }

        try {
            ResponseBody imageBody = call.execute().body();
            File file = new File(directory, image.getTitle().toLowerCase()+".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            IOUtils.write(imageBody.bytes(), fileOutputStream);

            image.setLocalPath(file.getAbsolutePath());

            Log.d("Downloaded", file.getAbsolutePath());

            success = true;

        } catch (IOException e) {
            Log.e("ERROR", "Error while writing file!");
            Log.e("ERROR", e.toString());

            success = false;
        }

        return image;
    }

    public boolean deletePropertyImage(final PropertyImage image, String propertyAddress){
        boolean success = true;

        HttpManager httpManager = HttpManager.getInstance();

        PropertyAPIInterface apiService =
                httpManager.getRetrofit().create(PropertyAPIInterface.class);

        String fileDirectory = propertyAddress.replace(" ","_").toLowerCase();

        File directory = getAlbumStorageDir(externalFilesDir,fileDirectory);

        if (directory.isDirectory())
        {
            String[] children = directory.list();
            for (int i = 0; i < children.length; i++)
            {
                success = success && new File(directory, children[i]).delete();
            }
            success = success && directory.delete();
        }else{
            success = directory.delete();
        }
        return success;
    }

    public File getAlbumStorageDir(File externalFilesDir, String directoryName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(externalFilesDir, directoryName);
        if (!file.mkdirs()) {
            Log.e("ERROR", "Directory not created");
        }
        return file;
    }

    public MaterialDialog getLoadingDialog(Context ctx){
        return new MaterialDialog.Builder(ctx)
                .title(R.string.downloading)
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();
    }

    private class DownloadPropertiesTask extends AsyncTask<ArrayList<Property>, Void,
            Boolean> {
        SyncManager syncManager;

        @Override
        protected void onPreExecute() {
            loadingDialog.show();
        }

        @Override
        protected Boolean doInBackground(ArrayList<Property>... params) {
            Boolean success = true;
            ArrayList<Property> properties = params[0];

            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                ArrayList<PropertyImage> pImages = new ArrayList<>();

                ArrayList<PropertyImage> images = property.getImages();

                for (int j = 0; j < images.size(); j++) {
                    PropertyImage result = downloadPropertyImage(images.get(j), property.getAddress());
                    pImages.add(result);

                    success = success && (result != null);
                }
                property.setImages(pImages);

                //Persist
                property.save();
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d("Downloaded file: ", success.toString());

            mDeferred.resolve(success);

            loadingDialog.dismiss();
        }
    }

    private class DeletePropertiesTask extends AsyncTask<ArrayList<Property>, Void,
            Boolean> {
        SyncManager syncManager;

        @Override
        protected void onPreExecute() {
            loadingDialog.show();
        }

        @Override
        protected Boolean doInBackground(ArrayList<Property>... params) {
            Boolean success = true;
            ArrayList<Property> properties = params[0];

            for (int i = 0; i < properties.size(); i++) {
                Property property = properties.get(i);
                property.delete();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d("Downloaded file: ", success.toString());

            mDeferred.resolve(success);

            loadingDialog.dismiss();
        }
    }
}
