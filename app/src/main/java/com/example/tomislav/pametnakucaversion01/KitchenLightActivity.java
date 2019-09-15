package com.example.tomislav.pametnakucaversion01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

import helpers.MqttHelper;


public class KitchenLightActivity extends AppCompatActivity {
    static boolean flagLight=true,flagLight2=true,flagPlug1=true,flagPlug2=true,flagPlug3=true,flagActivity=true;

    MqttHelper mqttHelper;
    ImageButton lightButton,light2Button,plugButton,plug2Button,plug3Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMqtt();
        setContentView(R.layout.activity_kuhinja_svijetla);
        lightButton=(ImageButton)findViewById(R.id.light);
        light2Button=(ImageButton)findViewById(R.id.light2);
        plugButton=(ImageButton)findViewById(R.id.plug1);
        plug2Button=(ImageButton)findViewById(R.id.plug2);
        plug3Button=(ImageButton)findViewById(R.id.plug3);

        if(flagLight)
            lightButton.setBackground(getResources().getDrawable(R.drawable.zarulja3));
        else
            lightButton.setBackground(getResources().getDrawable(R.drawable.zarulja2));

        if(flagLight2)
            light2Button.setBackground(getResources().getDrawable(R.drawable.zarulja3));
        else
            light2Button.setBackground(getResources().getDrawable(R.drawable.zarulja2));

        if(flagPlug1)
            plugButton.setBackground(getResources().getDrawable(R.drawable.uticnicaon));
        else
            plugButton.setBackground(getResources().getDrawable(R.drawable.uticnica));

        if(flagPlug2)
            plug2Button.setBackground(getResources().getDrawable(R.drawable.uticnicaon));
        else
            plug2Button.setBackground(getResources().getDrawable(R.drawable.uticnica));

        if(flagPlug3)
            plug3Button.setBackground(getResources().getDrawable(R.drawable.uticnicaon));
        else
            plug3Button.setBackground(getResources().getDrawable(R.drawable.uticnica));

    }

    public void kitchenLight(View view){


        String topic = "house/kitchen/light1";

        if(flagLight==true){
            view.setBackground(getResources().getDrawable(R.drawable.zarulja2));

            String payload = "on";
            byte[] encodedPayload = new byte[0];
            try {
                encodedPayload = payload.getBytes("UTF-8");
                MqttMessage message = new MqttMessage(encodedPayload);
                message.setRetained(true);
                mqttHelper.mqttAndroidClient.publish(topic, message);
            } catch (UnsupportedEncodingException | MqttException e) {
                e.printStackTrace();
            }
            flagLight=false;

        }else {
            view.setBackground(getResources().getDrawable(R.drawable.zarulja3));
            String payload = "off";
            byte[] encodedPayload = new byte[0];
            try {
                encodedPayload = payload.getBytes("UTF-8");
                MqttMessage message = new MqttMessage(encodedPayload);
                message.setRetained(true);
                mqttHelper.mqttAndroidClient.publish(topic, message);
            } catch (UnsupportedEncodingException | MqttException e) {
                e.printStackTrace();
            }
            flagLight=true;
        }
    }

    public void kitchenLight2(View view){

        String topic = "house/kitchen/light2";

        if(flagLight2==true){
            view.setBackground(getResources().getDrawable(R.drawable.zarulja2));

            String payload = "on";
            byte[] encodedPayload = new byte[0];
            try {
                encodedPayload = payload.getBytes("UTF-8");
                MqttMessage message = new MqttMessage(encodedPayload);
                message.setRetained(true);
                mqttHelper.mqttAndroidClient.publish(topic, message);
            } catch (UnsupportedEncodingException | MqttException e) {
                e.printStackTrace();
            }
            flagLight2=false;

        }else {
            view.setBackground(getResources().getDrawable(R.drawable.zarulja3));
        String payload = "off";
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            message.setRetained(true);
            mqttHelper.mqttAndroidClient.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
            flagLight2=true;
        }
    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("Debug","Connected");
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug",mqttMessage.toString());
                if(topic.toString().equals("house/kitchen/light")&& mqttMessage.toString().equals("off")) {
                    flagLight2 = true;
                    if(!flagActivity){
                        flagActivity=true;
                        recreate(); }
                }else {
                    flagLight2 = false;
                    if(flagActivity){
                        flagActivity=false;
                        recreate();
                    }
                }

            }


            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }

        });
    }

    public void kitchenPlug1(View view) {

        if(flagPlug1==true){
            view.setBackground(getResources().getDrawable(R.drawable.uticnicaon));
            flagPlug1=false;
        }else{
            view.setBackground(getResources().getDrawable(R.drawable.uticnica));
            flagPlug1=true;
        }

    }
    public void kitchenPlug2(View view) {

        if(flagPlug2==true){
            view.setBackground(getResources().getDrawable(R.drawable.uticnicaon));
            flagPlug2=false;
        }else{
            view.setBackground(getResources().getDrawable(R.drawable.uticnica));
            flagPlug2=true;
        }

    }
    public void kitchenPlug3(View view) {

       if(flagPlug3==true){
            view.setBackground(getResources().getDrawable(R.drawable.uticnicaon));
            flagPlug3=false;
        }else{
            view.setBackground(getResources().getDrawable(R.drawable.uticnica));
            flagPlug3=true;
        }

    }
}
