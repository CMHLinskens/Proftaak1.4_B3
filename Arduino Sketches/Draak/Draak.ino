#define PIN_TRIGGER 27
#define PIN_ECHO 26
#define PIN_LED 32


// Ultrasone variables
const static float detectionRange = 50.0f;
float distance = 0.0f;
bool isDetecting = false;


// PWM variables
const int freq = 5000;
const int ledChannel = 0;
const int resolution = 8;
const int signalAmplifier = 1500;

void setup(){
  Serial.begin(9600);
  pinMode(PIN_TRIGGER, OUTPUT);
  pinMode(PIN_ECHO, INPUT);

  // Do the setup for the PWM led
  ledcSetup(ledChannel, freq, resolution);

  // Attach the led pin
  ledcAttachPin(PIN_LED, ledChannel);
}

void loop() {
  startUltrasone();
  distance = getDetectedDistance();
  isDetecting = checkIfInRange(distance);

  // if the ultrasone is detecting then calculate the PWM value
  int ledStrength = isDetecting? constrain(((1/distance)*signalAmplifier), 0, 255) : 0;
  Serial.println(ledStrength);
  ledcWrite(ledChannel, ledStrength);
  
  delay(100);
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
