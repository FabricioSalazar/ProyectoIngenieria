package ucr.ac.cr.proyectoingenieria;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ucr.ac.cr.proyectoingenieria.model.Company;

/**
 * Created by Fabricio on 1/7/18.
 */

public class CompanyAdapter extends ArrayAdapter<Company> {

    private Context context;
    private List<Company> companies;

    public CompanyAdapter(Context context, @LayoutRes int resource, @NonNull List<Company> companies) {
        super(context, resource, companies);
        this.context = context;
        this.companies = companies;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_company, parent, false);

        final String companyId = String.valueOf(companies.get(position).getId());
        final String companyName = companies.get(position).getName();
        final String companyAddress = companies.get(position).getAddress();
        final String companyPhone = companies.get(position).getPhone();
        final String companyEmail = companies.get(position).getEmail();
        final String companyType = companies.get(position).getType();

        TextView tvCompanyName = (TextView) rowView.findViewById(R.id.tvCompanyName);
        TextView tvCompanyEmail = (TextView) rowView.findViewById(R.id.tvCompanyEmail);
        TextView tvCompanyAddress = (TextView) rowView.findViewById(R.id.tvCompanyAddress);
        TextView tvCompanyPhone = (TextView) rowView.findViewById(R.id.tvCompanyPhone);
        TextView tvCompanyType = (TextView) rowView.findViewById(R.id.tvCompanyType);

        tvCompanyAddress.setText(companyAddress);
        tvCompanyEmail.setText(companyEmail);
        tvCompanyName.setText(companyName);
        tvCompanyPhone.setText(companyPhone);
        tvCompanyType.setText(companyType);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CompanyActivity.class);
                intent.putExtra("company_id", companyId);
                intent.putExtra("company_name", companyName);
                intent.putExtra("company_address", companyAddress);
                intent.putExtra("company_phone", companyPhone);
                intent.putExtra("company_email", companyEmail);
                intent.putExtra("company_type", companyType);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
