package lens.inmo360.managers;

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

    public synchronized static HttpManager getInstance() {
        if (instance == null) {
            //todo Find a way to store URL in constant without passing Context.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://inmobi360demo.azurewebsites.net/api/")
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
