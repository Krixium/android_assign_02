package ca.bcit.ass2.wang_xia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryDetailsActivity extends AppCompatActivity {

    // TODO: Visualize Data (Must)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        Bundle extras;
        String[] countryDetails;
        String[] countryBorders;
        String borders;
        ImageView flagImage;
        TextView countryNameText;
        TextView countryCapitalText;
        TextView countryRegionText;
        TextView countryPopulationText;
        TextView countryAreaText;
        TextView countryBorderText;

        extras = getIntent().getExtras();
        countryDetails = extras.getStringArray(getResources().getString(R.string.countryDetailExtra));
        countryBorders = extras.getStringArray(getResources().getString(R.string.countryBordersExtra));
        Log.d("COUNTRY NAME", countryDetails[0]);
        Log.d("COUNTRY CAPITAL", countryDetails[1]);
        Log.d("COUNTRY REGION", countryDetails[2]);
        Log.d("COUNTRY POPULATION", countryDetails[3]);
        Log.d("COUNTRY AREA", countryDetails[4]);

        countryNameText = (TextView) findViewById(R.id.countryNameText);
        countryCapitalText = (TextView) findViewById(R.id.countryCapitalText);
        countryRegionText = (TextView) findViewById(R.id.countryRegionText);
        countryPopulationText = (TextView) findViewById(R.id.countryPopulationText);
        countryAreaText = (TextView) findViewById(R.id.countryAreaText);
        flagImage = (ImageView) findViewById(R.id.countryFlagImage);
        countryBorderText = (TextView) findViewById(R.id.countryBordersText);

        if (countryDetails != null) {
            countryNameText.setText(String.format(
                getResources().getString(R.string.countryNameLocale), countryDetails[0]
            ));
            countryCapitalText.setText(String.format(
                getResources().getString(R.string.countryCapitalLocale), countryDetails[1]
            ));
            countryRegionText.setText(String.format(
                getResources().getString(R.string.countryRegionLocale), countryDetails[2]
            ));
            countryPopulationText.setText(String.format(
                getResources().getString(R.string.countryPopulationLocale), countryDetails[3]
            ));
            countryAreaText.setText(String.format(
                getResources().getString(R.string.countryAreaLocale), countryDetails[4]
            ));
            //flag test          'v' pass in imgView ref    'v' pass in img URL
            View root = findViewById(R.id.rootLayout);
            new ImageDownloader( flagImage, root ).execute( countryDetails[5] );
        }

        if (countryBorders != null) {
            borders = getResources().getString(R.string.countryBorders);

            for (int i = 0; i < countryBorders.length - 1; i++) {
                borders += " " + countryBorders[i] + ",";
            }
    //arrayIndexOutOfBounds without this check, could go into above for loop as well but must continue or break
            if (countryBorders.length != 0) {
                borders += " " + countryBorders[countryBorders.length - 1];
            }
            countryBorderText.setText(borders);
        }
    }
}
