package lens.inmo360.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by estebanbutti on 4/25/16.
 */
public interface PropertyAPIInterface {
    @GET("property/getall/{companyID}")
    Call<List<Property>> getAllPropertiesForCompany(@Path("companyID") String companyID);

    @GET("property/details/{id}")
    Call<Property> getProperty(@Path("id") String id);

    @POST("account/login/")
    Call<User> login(@Body User user);

    @Streaming
    @GET
    Call<ResponseBody> downloadImage(@Url String url);
}
