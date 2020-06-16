#include <WiFi.h>
#include <PubSubClient.h>

// Define the pins for the hardware
#define PIN_TRIGGER 27
#define PIN_ECHO 26
#define PIN_LED 33
#define PIN_BUZZER 32
#define PIN_BUTTON 35

// This activates the sensor and actuators
bool isActive = false;

// Ultrasone variables
const float detectionRange = 50.0f;
float distance = 0.0f;
bool isDetecting = false;
unsigned long lastPulseTime;
const int pulseTimer = 100; // in ms


// LED PWM variables
const int freq = 5000;
const int ledChannel = 0;
const int resolution = 8;
const int signalAmplifier = 1500;

// Buzzer variables
const int bFreq = 100;
const int buzzerChannel = 1;
const int bResolution = 12;

// Button variables
unsigned long lastButtonTime;
const int buttonTimer = 1000; // in ms

// WIFI details
const char* WLAN_SSID = "Ziggo136B821";
const char* WLAN_ACCESS_KEY = "uXp4pueftJjw";

// MQTT variables
const char* MQTT_CLIENT_ID = "MQTTExampleTryout_dkosafjnfjkasndkjbadksafb";
const char* MQTT_BROKER_URL= "maxwell.bps-software.nl";
const int   MQTT_PORT = 1883;
const char* MQTT_USERNAME = "androidTI";
const char* MQTT_PASSWORD = "&FN+g$$Qhm7j";

const char* MQTT_TOPIC_DRAAK_IN = "B3/DraakIn";
const char* MQTT_TOPIC_DRAAK_OUT = "B3/DraakOut";
const int MQTT_QOS = 0;

WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);

void setup(){
  Serial.begin(9600);
  pinMode(PIN_TRIGGER, OUTPUT);
  pinMode(PIN_ECHO, INPUT);
  pinMode(PIN_BUZZER, OUTPUT);
  pinMode(PIN_BUTTON, INPUT);

  // Do the setup for the PWM led and buzzer
  ledcSetup(ledChannel, freq, resolution);
  ledcSetup(buzzerChannel, bFreq, resolution);

  // Attach the led pin
  ledcAttachPin(PIN_LED, ledChannel);
  ledcAttachPin(PIN_BUZZER, buzzerChannel);

  // Setting up WiFi connection
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  Serial.print("Connecting to: ");
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
  mqttClient.setCallback(startAction);
  

  if (mqttClient.connect(MQTT_CLIENT_ID, MQTT_USERNAME, MQTT_PASSWORD)) {
    Serial.println("Connected to MQTT broker");
  }else {
    Serial.println("Failed to connect to MQTT broker");
  }

  if (mqttClient.subscribe(MQTT_TOPIC_DRAAK_IN, MQTT_QOS)){
    
    Serial.println("Subscribed to topic ");
    Serial.println(MQTT_TOPIC_DRAAK_IN);
  }else {
    Serial.println("Failed to connect to topic ");
    Serial.println(MQTT_TOPIC_DRAAK_IN);
  }
}

void loop() {
  if ((lastPulseTime + pulseTimer) < millis()) {
  if(isActive){
   startUltrasone();
   distance = getDetectedDistance();
   isDetecting = checkIfInRange(distance);

   // If the ultrasone is detecting then calculate the PWM value
   int ledStrength = isDetecting? constrain(((1/distance)*signalAmplifier), 0, 255) : 0;
   //Serial.println(ledStrength);
   ledcWrite(ledChannel, ledStrength);

   if(isDetecting) {
    ledcWriteTone(buzzerChannel, ((1/distance)*signalAmplifier));
   } else {
    ledcWriteTone(buzzerChannel, 0);
   }
  }
  lastPulseTime = millis();

  mqttClient.loop();
 }

 if(isActive){
  if(digitalRead(PIN_BUTTON) && (lastButtonTime + buttonTimer) < millis()){
    endAction();
    lastButtonTime = millis();
  }
 }
}

void startUltrasone(){
  digitalWrite(PIN_TRIGGER, false);
  delayMicroseconds(2);
  
  digitalWrite(PIN_TRIGGER, true);
  delayMicroseconds(10);
  digitalWrite(PIN_TRIGGER, false);
}

float getDetectedDistance(){
  // get the time duration
  float duration = pulseIn(PIN_ECHO, true);
  // calculate the distance
  return (duration * 0.0343) / 2;
}

bool checkIfInRange(double distance){
  return (distance < detectionRange);
}

void freqout(int freq, int t)
{
  int hperiod;                             
  long cycles, i;
  pinMode(PIN_BUZZER, OUTPUT);               

  hperiod = (500000 / freq) - 7;           

  cycles = ((long)freq * (long)t) / 1000;   

  for (i=0; i<= cycles; i++){              
    digitalWrite(PIN_BUZZER, HIGH); 
    delayMicroseconds(hperiod);
    digitalWrite(PIN_BUZZER, LOW); 
    delayMicroseconds(hperiod - 1);     
  }
  pinMode(PIN_BUZZER, INPUT);                
}

void startAction(char* topic, byte* payload, unsigned int length){
  if(strcmp(topic, MQTT_TOPIC_DRAAK_IN) == 0){
    isActive = true;
    Serial.println("Got a message.");
  }
}

void endAction(){
  Serial.println("Pressed the button.");
  const char* message = "end";
  mqttClient.publish(MQTT_TOPIC_DRAAK_OUT, message);
  isActive = false;
}
