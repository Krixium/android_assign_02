package ca.bcit.ass2.wang_xia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //will this die?
    private ShareActionProvider shareActionProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setSharedActionIntent("this is for my lab, please understand");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_request_info:
                Intent intent = new Intent(this, InfoRequestActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setSharedActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> regionsList;
        final ListView listView;
        ArrayAdapter<String> adapter;
        final ProgressBar progressBar;

        listView = (ListView) findViewById(R.id.regionsListView);
        regionsList = new ArrayList<>();
        Collections.addAll(regionsList, getResources().getStringArray(R.array.regionsArray));
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, regionsList);

        listView.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.MainProgressBar);
        progressBar.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                progressBar.setVisibility(View.VISIBLE);
                String urlString = String.format(getResources().getString(R.string.regionApiLocale),
                        listView.getItemAtPosition(i));
                urlString = DataParser.convertSpaces(urlString);
                try {
                    new ContinentAsyncTask().execute(new URL(urlString));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.MainProgressBar);
        if (progressBar.getVisibility() != View.GONE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private class ContinentAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            return DataParser.GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] countries = null;
            Intent intent = new Intent(MainActivity.this, CountriesActivity.class);

            try {
                countries = DataParser.parseJSONArrayToCountry(new JSONArray(result));
            } catch (Exception e) {
                e.printStackTrace();
            }

            intent.putExtra(getResources().getString(R.string.countriesExtra), countries);
            startActivity(intent);
        }
    }
}
