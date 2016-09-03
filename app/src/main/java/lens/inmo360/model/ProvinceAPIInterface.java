package lens.inmo360.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by estebanbutti on 4/25/16.
 */
public interface ProvinceAPIInterface {
    @GET("property/provinces}")
    Call<List<Province>> getProvinces();
}
