#include <WiFi.h>
#include <PubSubClient.h>

//Zelf in te stellen voor je eigen WLAN
const char* WLAN_SSID = "Open Adan";
const char* WLAN_ACCESS_KEY = "ADANadan@4";

//client id moet uniek zijn, dus zelf aanpassesn (willekeurige cijfers en letters)
const char* MQTT_CLIENT_ID = "MQTTExampleTryout_hurdurahfiusdhfpauwhefasdfawe3";
//gegevens van de MQTT broker die we wel in TI-1.4 kunnen gebruiken
const char* MQTT_BROKER_URL= "maxwell.bps-software.nl";
const int   MQTT_PORT = 1883;
const char* MQTT_USERNAME = "androidTI";
const char* MQTT_PASSWORD = "&FN+g$$Qhm7j";

// Definieer de MQTT topics
const char* MQTT_TOPIC_LCD = "B3/DOORBELL";
// Definieer de te gebruiken Quality of Service (QOS)
const int MQTT_QOS = 0;

String trigger = "";

WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);



const int bFreq = 100;
const int buzzerChannel = 1;
const int bResolution = 8;
const int pinBuzzer = 22;

void setup() {
  pinMode(pinBuzzer, OUTPUT);
  ledcSetup(buzzerChannel, bFreq, bResolution);
  ledcAttachPin(pinBuzzer, buzzerChannel);

  Serial.begin(115200);

  //Zet WiFI verbinding op
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  Serial.println("Connecting to ");
  Serial.println(WLAN_SSID);
  WiFi.begin(WLAN_SSID, WLAN_ACCESS_KEY);
  uint8_t i = 0;
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    if ((++i % 16) == 0)
    {
      Serial.println(F(" still trying to connect"));
    }
  }
  Serial.println("WiFi connected");
  Serial.println("IP Adress: ");
  Serial.println(WiFi.localIP());

  //zet MQTT client op
  mqttClient.setServer(MQTT_BROKER_URL, MQTT_PORT);
  mqttClient.setCallback(mqttCallback);

  if (!mqttClient.connect(MQTT_CLIENT_ID, MQTT_USERNAME, MQTT_PASSWORD)) {
    Serial.println("Failed to connect to MQTT broker");
  }else {
    Serial.println("Connected to MQTT broker");
  }

  if (!mqttClient.subscribe(MQTT_TOPIC_LCD, MQTT_QOS)){
    Serial.println("Failed to connect to topic ");
    Serial.println(MQTT_TOPIC_LCD);
  }else {
    Serial.println("Subscribed to topic ");
    Serial.println(MQTT_TOPIC_LCD);
  }
}

void loop() {
  mqttClient.loop();
  
  if (trigger != "") {
    
    ledcWriteTone(buzzerChannel, 50);
    delay(500);
    ledcWriteTone(buzzerChannel, 0);
    delay(500);
    ledcWriteTone(buzzerChannel, 50);
    delay(500);
    ledcWriteTone(buzzerChannel, 0);
    trigger = "";
  }
  
  delay(50);
}

void mqttCallback(char* topic, byte* payload, unsigned int length) {
  Serial.println(topic);
  Serial.println(length);
  if (strcmp(topic, MQTT_TOPIC_LCD) == 0) {
    char txt[17];
    for (int i = 0; i < 17; i++) {txt[i] = '\0';}
    strncpy(txt, (const char*) payload, length > 16 ? 16 : length);
    trigger = txt;
  }
}
