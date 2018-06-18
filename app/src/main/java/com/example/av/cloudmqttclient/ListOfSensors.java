package com.example.av.cloudmqttclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListOfSensors extends AppCompatActivity {

    Button lightSensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_sensors);
        lightSensor = (Button) findViewById(R.id.lightBtn);

        lightSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLightActivity();
            }
        });
    }

    public void openLightActivity()
    {
        Intent intent = new Intent(this, LightActivity.class);
        startActivity(intent);
    }
}
