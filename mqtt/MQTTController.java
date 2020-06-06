package com.example.esstelingapp.mqtt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

import static android.content.ContentValues.TAG;

public final class MQTTController {
    private static MQTTController INSTANCE;
    private MqttAndroidClient client;
    private Context context;
    private String _topic;

    public MQTTController() {
    }

    public static MQTTController getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MQTTController();
        }
        return INSTANCE;
    }

    public void connectToServer() {
        String clientId = MqttClient.generateClientId();

        client = new MqttAndroidClient(context.getApplicationContext(),"tcp://maxwell.bps-software.nl:1883",clientId);
        try {

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("androidTI");
            options.setPassword("&FN+g$$Qhm7j".toCharArray());
            IMqttToken token = client.connect(options);


            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG,"Succesfully connected");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d(TAG,"Failed to connect: " + exception.getMessage());
                }
            });

        }
        catch(MqttException e){
            e.printStackTrace();
        }

    }

    public void sendRawMessage(String string) {
        try {
            client.publish(_topic,new MqttMessage(string.getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
