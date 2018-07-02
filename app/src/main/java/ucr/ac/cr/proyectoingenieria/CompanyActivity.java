package ucr.ac.cr.proyectoingenieria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ucr.ac.cr.proyectoingenieria.model.Company;
import ucr.ac.cr.proyectoingenieria.remote.APIUtils;
import ucr.ac.cr.proyectoingenieria.remote.CompanyService;

public class CompanyActivity extends AppCompatActivity {

    CompanyService companyService;
    EditText etCName;
    EditText etCAddress;
    EditText etCPhone;
    EditText etCEmail;
    Spinner spCType;
    Button btnSaveCompany;
    Button btnDeleteCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        setTitle("Compañías");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etCName = (EditText) findViewById(R.id.etCName);
        etCAddress = (EditText) findViewById(R.id.etCAddress);
        etCPhone = (EditText) findViewById(R.id.etCPhone);
        etCEmail = (EditText) findViewById(R.id.etCEmail);

        spCType = (Spinner) findViewById(R.id.spCType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCType.setAdapter(adapter);

        btnSaveCompany = (Button) findViewById(R.id.btnSaveCompany);
        btnDeleteCompany = (Button) findViewById(R.id.btnDeleteCompany);

        companyService = APIUtils.getCompanyService();

        Bundle extras = getIntent().getExtras();
        final String companyId = extras.getString("company_id");
        String companyName = extras.getString("company_name");
        String companyAddress = extras.getString("company_address");
        String companyPhone = extras.getString("company_phone");
        String companyEmail = extras.getString("company_email");
        String companyType = extras.getString("company_type");

        etCEmail.setText(companyEmail);
        etCName.setText(companyName);
        etCAddress.setText(companyAddress);
        etCPhone.setText(companyPhone);

        if(!(companyId != null && companyId.trim().length() > 0)){
            btnDeleteCompany.setVisibility(View.INVISIBLE);
        }

        btnSaveCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Company company = new Company();
                company.setName(etCName.getText().toString());
                company.setAddress(etCAddress.getText().toString());
                company.setEmail(etCEmail.getText().toString());
                company.setPhone(etCPhone.getText().toString());
                company.setType(spCType.getSelectedItem().toString());
                if (companyId != null && companyId.trim().length() > 0){
                    company.setId(Long.parseLong(companyId));
                    updateCompany(company);
                } else {
                    addCompany(company);
                }
            }
        });

        btnDeleteCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCompany(Long.parseLong(companyId));

                Intent intent = new Intent(CompanyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addCompany(Company company) {
        Call<Company> call = companyService.addCompany(company);
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(CompanyActivity.this,
                            "Compañía creada exitosamente",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CompanyActivity.this,
                            "Ha ocurrido un error",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateCompany (Company company) {
        Call<Company> call = companyService.updateCompany(company);
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(CompanyActivity.this,
                            "Compañía actualizada exitosamente",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CompanyActivity.this,
                            "Ha ocurrido un error",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteCompany(long id) {
        Call<Company> call = companyService.deleteCompany(id);
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(CompanyActivity.this,
                            "Compañía borrada exitosamente",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CompanyActivity.this,
                            "Ha ocurrido un error",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
