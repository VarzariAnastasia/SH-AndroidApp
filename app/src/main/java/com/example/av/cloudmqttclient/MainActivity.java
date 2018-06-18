package com.example.av.cloudmqttclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import MqttHelperPackage.CMqttHelper;

public class MainActivity extends AppCompatActivity {

    Button mainActivityBtn;
    CMqttHelper mqttHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityBtn = (Button) findViewById(R.id.mainActivityBtn);

        mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity(getApplicationContext());
            }
        });
    }

    public void openRegisterActivity(Context context){
        SharedPreferences preference = context.getSharedPreferences("prevStarted", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = preference.edit();

        boolean previouslyStarted = preference.getBoolean("firstRun", false);
        if(!previouslyStarted) {
            Log.w("MainActicity", "!previouslyStarted");
            SharedPreferences.Editor editor = preference.edit();
            editor.putBoolean("firstRun", true);
            editor.apply();
            Log.w("MainActicity", "editor applied");
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            Log.w("MainActicity", "after register activity");
        }else{
            mqttHelper = new CMqttHelper(getApplicationContext());

            Log.w("RegisterActivity", "getData");
            SharedPreferences sharePreferences = getSharedPreferences("sharedPref",Context.MODE_PRIVATE);
            String username = sharePreferences.getString("usernamePref", " ");
            String clientId = sharePreferences.getString("clientIdPref", " ");
            String serverUri = sharePreferences.getString("serverUriPref", " ");
            String password = sharePreferences.getString("passwordPref", " ");
            mqttHelper.setData(serverUri, clientId, username, password);
            mqttHelper.initFcn(getApplicationContext());
            Intent intent = new Intent(this, ListOfSensors.class);
            startActivity(intent);
        }
    }
}
