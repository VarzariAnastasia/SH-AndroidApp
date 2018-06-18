package com.example.av.cloudmqttclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PubSubActivity extends AppCompatActivity {

    Button publishActivityBtn, subscribeActivityBtn, listOfSensors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_sub);

        publishActivityBtn = (Button) findViewById(R.id.publishActivityBtn);
        subscribeActivityBtn = (Button) findViewById(R.id.subscribeActivityBtn);
        listOfSensors = (Button) findViewById(R.id.listOfSensors);

        listOfSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListOfSensors();
            }
        });
        publishActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPublishActivity();
            }
        });

        subscribeActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSubscribeActivity();
            }
        });
    }

    public void openListOfSensors()
    {
        Intent intent = new Intent(this, ListOfSensors.class);
        startActivity(intent);
    }

    public void openPublishActivity(){
        Intent intent = new Intent(this, PublishActivity.class);
        startActivity(intent);
    }

    public void openSubscribeActivity(){
        Intent intent = new Intent(this, SubscribeActivity.class);
        startActivity(intent);
    }
}
