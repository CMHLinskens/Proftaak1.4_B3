package com.example.esstelingapp.mqtt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.esstelingapp.Story;
import com.example.esstelingapp.data.DataSingleton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

import static android.content.ContentValues.TAG;

public class MQTTController {
    private static MQTTController INSTANCE;
    private MqttAndroidClient client;
    private String received;

    public MQTTController() {
    }

    public synchronized static MQTTController getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MQTTController();
        }
        return INSTANCE;
    }

    public void connectToServer(Context context) {
        String clientId = MqttClient.generateClientId();

        client = new MqttAndroidClient(context.getApplicationContext(),"tcp://maxwell.bps-software.nl:1883",clientId);
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("androidTI");
            options.setPassword("&FN+g$$Qhm7j".toCharArray());
            final IMqttToken token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG,"Succesfully connected");
                    subscribeToCodes();
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

    public void sendRawMessage(String topic) {
        try {
            client.publish(topic,new MqttMessage("Run".getBytes("UTF-8")));
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void subscribeToCodes(){
        for(Story story : DataSingleton.getInstance().getStories()){
            try {
                client.subscribe("B3/Codes/" + story.getMqttTopic(), 2, new IMqttMessageListener() {
                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        String storyString = topic.substring(topic.lastIndexOf('/') + 1);
                        DataSingleton.getInstance().putUnlockCodes(storyString, message + "");
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void readRawMessage(String topic){
        try {
            received = "";
            client.subscribe(topic, 2, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    received = message.toString();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public String waitForMessage(String topic){
        if(!received.isEmpty()){
            try {
                client.unsubscribe(topic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
            String message = received;
            received = "";
            return message;
        }
        return received;
    }
}
