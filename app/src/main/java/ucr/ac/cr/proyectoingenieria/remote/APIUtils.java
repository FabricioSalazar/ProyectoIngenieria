package ucr.ac.cr.proyectoingenieria.remote;

/**
 * Created by Fabricio on 1/7/18.
 */

public class APIUtils {
    private APIUtils(){
    };

    public static final String API_URL = "http://192.168.43.96:8080/api/";

    public static CompanyService getCompanyService(){
        return RetrofitClient.getClient(API_URL).create(CompanyService.class);
    }
}
