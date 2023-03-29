package com.app.sushi.tei.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;


public class MaintenanceActivity extends AppCompatActivity {

    TextView maintenanceTextView, goBackTextView, sorryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        maintenanceTextView = (TextView) findViewById(R.id.maintenanceTextView);
        goBackTextView = (TextView) findViewById(R.id.goBackTextView);
        sorryTextView = (TextView) findViewById(R.id.sorryTextView);

        Intent intent = getIntent();

        String title = "";
        String data = "";

        if (intent != null) {

            String messgae_title = intent.getStringExtra("messgae_title");
            String messgae_desc = intent.getStringExtra("messgae_desc");


            if (messgae_title != null) {
                title = messgae_title;
                sorryTextView.setText(title);
            }

            if (messgae_desc != null) {
                data = messgae_desc;
                maintenanceTextView.setText(data);
            }


        }

        goBackTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          //      onBackPressed();
                finish();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(MaintenanceActivity.this, "IS_ACTIVE","1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(MaintenanceActivity.this, "OREO_UPDATE","1");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(MaintenanceActivity.this, "IS_ACTIVE","0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(MaintenanceActivity.this, "OREO_UPDATE","1");
        }
    }
}
