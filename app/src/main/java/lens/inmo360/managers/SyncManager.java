package lens.inmo360.managers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lens.inmo360.model.Property;
import lens.inmo360.model.PropertyAPIInterface;
import lens.inmo360.model.PropertyImage;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class SyncManager {

    public boolean downloadProperty(Context ctx, Property property){
        boolean success = true;

        ArrayList<PropertyImage> images = property.getImages();

        for (int i = 0; i < images.size(); i++) {
            PropertyImage propertyImage = images.get(i);
            boolean imageSuccess = this.downloadPropertyImage(ctx, propertyImage, property.getAddress());
            success = success && imageSuccess;
        }

        return success;
    }

    public boolean downloadPropertyImage(Context ctx, final PropertyImage image, String propertyAddress){
        boolean success;

        HttpManager httpManager = HttpManager.getInstance(ctx.getApplicationContext());

        PropertyAPIInterface apiService =
                httpManager.getRetrofit().create(PropertyAPIInterface.class);

        Call<ResponseBody> call = apiService.downloadImage(image.getURL());

        String fileDirectory = propertyAddress.replace(" ","_").toLowerCase();

        File directory = getAlbumStorageDir(ctx,fileDirectory);

        if(!directory.exists()) {
            if(directory.mkdir()); //directory is created;
        }

        try {
            ResponseBody imageBody = call.execute().body();
            File file = new File(directory, image.getTitle().toLowerCase()+".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            IOUtils.write(imageBody.bytes(), fileOutputStream);

            Log.d("Downloaded", file.getAbsolutePath());

            success = true;

        } catch (IOException e) {
            Log.e("ERROR", "Error while writing file!");
            Log.e("ERROR", e.toString());

            success = false;
        }

        return success;
    }

    public File getAlbumStorageDir(Context context, String directoryName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), directoryName);
        if (!file.mkdirs()) {
            Log.e("ERROR", "Directory not created");
        }
        return file;
    }
}
