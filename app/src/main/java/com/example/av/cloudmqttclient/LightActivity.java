package com.example.av.cloudmqttclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import MqttHelperPackage.CMqttHelper;

public class LightActivity extends AppCompatActivity {

    CMqttHelper mqttHelper;
    ToggleButton kitchenLightToogleBtn, livingLightToogleBtn, outsideLightToogleBtn;
    SeekBar bedroomLightSeekBar;
    String message;
    String topic = "SH/light/";
    int lightProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        kitchenLightToogleBtn = (ToggleButton) findViewById(R.id.kitchenLightToogleBtn);
        livingLightToogleBtn = (ToggleButton) findViewById(R.id.livingLightToogleBtn);
        outsideLightToogleBtn = (ToggleButton) findViewById(R.id.outsideLightToogleBtn);

        setBedroomLightState();

    }

    public void setKitchenLightState(View view)
    {
        String localTopic = topic+"kitchen";
        boolean lightState = kitchenLightToogleBtn.isChecked();
        //message = bedroomToogleBtn.getText().toString();
        mqttHelper = new CMqttHelper(getApplicationContext());
        subscribe(localTopic);
        if(lightState) {
            Toast.makeText(getApplicationContext(), "Light state On", Toast.LENGTH_LONG).show();
            message = "On";
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Light state Off", Toast.LENGTH_LONG).show();
            message = "Off";
        }

        mqttHelper.publishToTopic(localTopic, message);
    }

    public void setLivingLightState(View view)
    {
        String localTopic = topic+"living";
        boolean lightState = livingLightToogleBtn.isChecked();
        //message = bedroomToogleBtn.getText().toString();
        mqttHelper = new CMqttHelper(getApplicationContext());
        subscribe(localTopic);
        if(lightState) {
            Toast.makeText(getApplicationContext(), "Light state On", Toast.LENGTH_LONG).show();
            message = "On";
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Light state Off", Toast.LENGTH_LONG).show();
            message = "Off";
        }

        mqttHelper.publishToTopic(localTopic, message);
    }

    public void setBedroomLightState()
    {
        mqttHelper = new CMqttHelper(getApplicationContext());
        final String localTopic = topic+"bedroom";
        subscribe(localTopic);
        bedroomLightSeekBar = (SeekBar) findViewById(R.id.bedroomLightSeekBar);
        bedroomLightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lightProgress = progress;
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Bedroom light off", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Bedroom light on", Toast.LENGTH_LONG).show();
                mqttHelper.publishToTopic(localTopic, String.valueOf(lightProgress));
            }
        });
    }

    /*void outsideLightClick(View viw){
        if(outsideLightToogleBtn.isChecked()){

        }
    }*/

    private void subscribe(String topic){
        mqttHelper.subscribeToTopic(topic);
        mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                TextView textV = (TextView) findViewById(R.id.textprobe1);
                switch(topic){
                    case "SH/light/bedroom": {
                        bedroomLightSeekBar.setProgress(Integer.parseInt(message.toString()));
                        textV.setText(message.toString());
                    }
                    break;
                    case "SH/light/kitchen": {
                        if(message.toString().equals("On")){
                            kitchenLightToogleBtn.setChecked(true);

                        }else
                        if(message.toString().equals("Off")){
                            kitchenLightToogleBtn.setChecked(false);
                        }
                        textV.setText(message.toString());
                    }
                    break;
                    case "SH/light/living": {
                        if(message.toString().equals("On")){
                            livingLightToogleBtn.setChecked(true);

                        }else
                        if(message.toString().equals("Off")){
                            livingLightToogleBtn.setChecked(false);
                        }
                        textV.setText(message.toString());
                    }
                    break;
                    case "SH/light/outside": {
                        if(message.toString().equals("On")){
                            livingLightToogleBtn.setChecked(true);

                        }else
                        if(message.toString().equals("Off")){
                            livingLightToogleBtn.setChecked(false);
                        }
                        textV.setText(message.toString());
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
