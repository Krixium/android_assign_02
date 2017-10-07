package ca.bcit.ass2.wang_xia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        textView = (TextView) findViewById(R.id.testField);

        textView.setText(getIntent().getExtras().getString("test"));
    }
}
