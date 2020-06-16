#include <WiFi.h>
#include <PubSubClient.h>
#include <LiquidCrystal_I2C.h>

//Zelf in te stellen voor je eigen WLAN
const char* WLAN_SSID = "";
const char* WLAN_ACCESS_KEY = "";

//client id moet uniek zijn, dus zelf aanpassesn (willekeurige cijfers en letters)
const char* MQTT_CLIENT_ID = "MQTTExampleTryout_hurdurahfiusdhfpauwhefasdfawe";
//gegevens van de MQTT broker die we wel in TI-1.4 kunnen gebruiken
const char* MQTT_BROKER_URL= "maxwell.bps-software.nl";
const int   MQTT_PORT = 1883;
const char* MQTT_USERNAME = "androidTI";
const char* MQTT_PASSWORD = "&FN+g$$Qhm7j";

// Definieer de MQTT topics
const char* MQTT_TOPIC_LCD = "B3/LCD";
// Definieer de te gebruiken Quality of Service (QOS)
const int MQTT_QOS = 0;

LiquidCrystal_I2C lcd(0x27, 16, 2);
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);

bool ledIsOn = true;

void mqttCallback(char* topic, byte* payload, unsigned int length) {
  Serial.println(topic);
  Serial.println(length);
  if (strcmp(topic, MQTT_TOPIC_LCD) == 0) {
    char txt[17];
    for (int i = 0; i < 17; i++) {txt[i] = '\0';}
    strncpy(txt, (const char*) payload, length > 16 ? 16 : length);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(txt);
  }
}

void setup() {
  Serial.begin(115200);
  Serial.println("LCD test");

  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, ledIsOn);

  lcd.init();
  lcd.backlight();
  lcd.setCursor(0,0);
  lcd.print("test");

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
  ledIsOn = !ledIsOn;
  digitalWrite(LED_BUILTIN, ledIsOn);
  delay(100);

}
