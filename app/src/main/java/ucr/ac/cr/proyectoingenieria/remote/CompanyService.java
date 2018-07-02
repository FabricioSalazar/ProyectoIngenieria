package ucr.ac.cr.proyectoingenieria.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ucr.ac.cr.proyectoingenieria.model.Company;

/**
 * Created by Fabricio on 1/7/18.
 */

public interface CompanyService {

    @GET("company/")
    Call<List<Company>> getCompanies();

    @POST("company/")
    Call<Company> addCompany(@Body Company company);

    @PUT("company/")
    Call<Company> updateCompany(@Body Company company);

    @DELETE("company/{id}")
    Call<Company> deleteCompany(@Path("id") long id);
}
