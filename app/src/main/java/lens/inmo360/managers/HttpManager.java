package lens.inmo360.managers;

import android.content.Context;

import lens.inmo360.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class HttpManager {

    private static HttpManager instance;
    private Retrofit mRetrofit;

    private HttpManager(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public synchronized static HttpManager getInstance(Context ctx) {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ctx.getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            instance = new HttpManager(retrofit);
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
