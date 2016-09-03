package lens.inmo360.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by estebanbutti on 4/25/16.
 */
public interface CityAPIInterface {
    @GET("property/cities/{provinceID}")
    Call<List<City>> getCitiesForProvince(@Path("provinceID") String provinceID);

//    @GET("property/details/{id}")
//    Call<Property> getProperty(@Path("id") String id);
//
//    @POST("account/login/")
//    Call<User> login(@Body User user);
//    @Streaming
//    @GET
//    Call<ResponseBody> downloadImage(@Url String url);
}
