package ca.bcit.ass2.wang_xia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.net.URL;

public class CountriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countires);

        final ListView listView;
        String[] countries;
        ArrayAdapter<String> arrayAdapter;

        countries = getIntent().getExtras().getStringArray(getResources().getString(R.string.countriesExtra));
        listView = (ListView) findViewById(R.id.countriesListView);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                countries != null ? countries : new String[0]);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String urlString = String.format(getResources().getString(R.string.countryApiLocale),
                        listView.getItemAtPosition(i).toString());
                urlString = DataParser.convertSpaces(urlString);
                try {
                    new CountryDetailAsyncTask().execute(new URL(urlString));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // TODO: Add progress bar for loading (Must)
    private class CountryDetailAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            return DataParser.GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] countryDetails = null;
            String[] countryBorders = null;
            Intent intent = new Intent(CountriesActivity.this, CountryDetailsActivity.class);

            try {
                countryDetails = DataParser.parseJSONArrayForDetails(new JSONArray(result));
                countryBorders = DataParser.passeJSONArrayForBorders(new JSONArray(result));
            } catch (Exception e) {
                e.printStackTrace();
            }

            intent.putExtra(getResources().getString(R.string.countryDetailExtra), countryDetails);
            intent.putExtra(getResources().getString(R.string.countryBordersExtra), countryBorders);
            startActivity(intent);
        }
    }
}
