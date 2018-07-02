package ucr.ac.cr.proyectoingenieria;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ucr.ac.cr.proyectoingenieria.model.Company;
import ucr.ac.cr.proyectoingenieria.remote.APIUtils;
import ucr.ac.cr.proyectoingenieria.remote.CompanyService;

public class MainActivity extends AppCompatActivity {

    Button btnAddCompany;
    Button btnGetCompaniesList;
    ListView listView;

    CompanyService companyService;
    List<Company> list = new ArrayList<Company>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Gestionar Empresas");

        btnAddCompany = (Button) findViewById(R.id.btnAddCompany);
        btnGetCompaniesList = (Button) findViewById(R.id.btnGetCompaniesList);
        listView = (ListView) findViewById(R.id.listView);
        companyService = APIUtils.getCompanyService();

        btnGetCompaniesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get companies list
                getCompaniesList();
            }
        });

        btnAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CompanyActivity.class);
                intent.putExtra("company_id", "");
                intent.putExtra("company_name", "");
                intent.putExtra("company_address", "");
                intent.putExtra("company_phone", "");
                intent.putExtra("company_email", "");
                intent.putExtra("company_type", "");
                startActivity(intent);
            }
        });
    }

    public void getCompaniesList() {
        Call<List<Company>> call = companyService.getCompanies();
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    Context context = MainActivity.this;
                    listView.setAdapter(new CompanyAdapter(context, R.layout.list_company, list));
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
}
