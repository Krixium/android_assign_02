package ca.bcit.ass2.wang_xia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryDetailsActivity extends AppCompatActivity {

    TextView countryName;

    // TODO: Visualize Data (Must)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        countryName = (TextView) findViewById(R.id.countryNameText);

        Bundle extras = getIntent().getExtras();
        String[] countryDetails = extras.getStringArray(getResources().getString(R.string.countryDetailExtra));
        String[] countryBorders = extras.getStringArray(getResources().getString(R.string.countryBordersExtra));
        String temp = "";

        for (String s : countryDetails) {
            temp = temp.concat(" " + s);
        }

        for (String s : countryBorders) {
            temp = temp.concat(" " + s);
        }

        countryName.setText(temp);
    }
}
