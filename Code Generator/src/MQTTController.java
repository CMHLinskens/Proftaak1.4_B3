import org.eclipse.paho.client.mqttv3.*;

import java.io.UnsupportedEncodingException;

public class MQTTController {
    private static MQTTController INSTANCE;
    private MqttClient client;
    private final String topic = "B3/Codes/";

    public MQTTController() {
    }

    public synchronized static MQTTController getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MQTTController();
        }
        return INSTANCE;
    }

    public void connectToServer(){
        String clientId = MqttClient.generateClientId();

        try {
            client = new MqttClient("tcp://maxwell.bps-software.nl:1883", clientId);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setUserName("androidTI");
            connectOptions.setPassword("&FN+g$$Qhm7j".toCharArray());
            IMqttToken token = client.connectWithResult(connectOptions);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    System.out.println("Succesfully connected!");
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable exception) {
                    System.out.println(("Failed to connect: " + exception.getMessage()));
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendCode(String story, String code){
        try {
            client.publish(topic + story, new MqttMessage(code.getBytes("UTF-8")));
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
