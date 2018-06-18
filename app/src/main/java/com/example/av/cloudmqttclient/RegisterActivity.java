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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import MqttHelperPackage.CMqttHelper;

public class RegisterActivity extends AppCompatActivity {

    CMqttHelper mqttHelper;

    Button connectToMqttServerBtn;

    EditText serverPlainText, clientIdPlainText, usernamePlainText, passwordPlainText;
    public String serverUri, clientId, username, password;

    public static final String preferencesName = "sharedPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        connectToMqttServerBtn = (Button) findViewById(R.id.connectToMqttServerBtn);
        serverPlainText = (EditText) findViewById(R.id.serverPlainText);
        clientIdPlainText = (EditText) findViewById(R.id.clientIdPlainText);
        usernamePlainText = (EditText) findViewById(R.id.usernamePlainText);
        passwordPlainText = (EditText) findViewById(R.id.passwordPlainText);
        Log.w("Reg", "OnCreate in register");

        connectToMqttServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Reg", "OnClick in register");
                setData();
                getData();
                mqttHelper = new CMqttHelper(getApplicationContext());
                mqttHelper.setData(serverUri, clientId, username, password);
                mqttHelper.initFcn(getApplicationContext());
                openListOfSensorsActivity();
            }
        });

    }

    public void openListOfSensorsActivity(){
        Log.w("RegisterActivity", "open ListOfSensors");
        Intent intent = new Intent(this, ListOfSensors.class);
        startActivity(intent);
    }

    public void setData()
    {
        Log.w("RegisterActivity", "set data");
        SharedPreferences sharePreferences = getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePreferences.edit();
        editor.putString("usernamePref", usernamePlainText.getText().toString());
        editor.putString("serverUriPref", serverPlainText.getText().toString());
        editor.putString("clientIdPref", clientIdPlainText.getText().toString());
        editor.putString("passwordPref", passwordPlainText.getText().toString());
        editor.apply();
    }

    public void getData()
    {
        Log.w("RegisterActivity", "getData");
        SharedPreferences sharePreferences = getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        username = sharePreferences.getString("usernamePref", " ");
        serverUri = sharePreferences.getString("serverUriPref", " ");
        password = sharePreferences.getString("passwordPref", " ");
    }

    public String getClientId(){
        SharedPreferences sharePreferences = getSharedPreferences("sharedPref",Context.MODE_PRIVATE);
        clientId = sharePreferences.getString("clientIdPref", " ");
        return clientId;
    }
}
