#define PIN_TRIGGER 27
#define PIN_ECHO 26
#define PIN_LED 33
#define PIN_BUZZER 32

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
unsigned long lastBuzzTime;
const int buzzTimer = 200; // in ms


void setup(){
  Serial.begin(9600);
  pinMode(PIN_TRIGGER, OUTPUT);
  pinMode(PIN_ECHO, INPUT);
  pinMode(PIN_BUZZER, OUTPUT);

  // Do the setup for the PWM led and buzzer
  ledcSetup(ledChannel, freq, resolution);
  ledcSetup(buzzerChannel, bFreq, resolution);

  // Attach the led pin
  ledcAttachPin(PIN_LED, ledChannel);
  ledcAttachPin(PIN_BUZZER, buzzerChannel);
}

void loop() {
  if ((lastPulseTime + pulseTimer) < millis()) {
  startUltrasone();
  distance = getDetectedDistance();
  isDetecting = checkIfInRange(distance);

  // If the ultrasone is detecting then calculate the PWM value
  int ledStrength = isDetecting? constrain(((1/distance)*signalAmplifier), 0, 255) : 0;
  Serial.println(ledStrength);
  ledcWrite(ledChannel, ledStrength);

    //delay(100);
  
  if(isDetecting) {
    ledcWriteTone(buzzerChannel, ((1/distance)*signalAmplifier));
    lastBuzzTime = millis();
  } else {
    ledcWriteTone(buzzerChannel, 0);
  }

  lastPulseTime = millis();
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

void freqout(int freq, int t)  // freq in hz, t in ms
{
  int hperiod;                               //calculate 1/2 period in us
  long cycles, i;
  pinMode(PIN_BUZZER, OUTPUT);                   // turn on output pin

  hperiod = (500000 / freq) - 7;             // subtract 7 us to make up for digitalWrite overhead

  cycles = ((long)freq * (long)t) / 1000;    // calculate cycles
 // Serial.print(freq);
 // Serial.print((char)9);                   // ascii 9 is tab - you have to coerce it to a char to work 
 // Serial.print(hperiod);
 // Serial.print((char)9);
 // Serial.println(cycles);

  for (i=0; i<= cycles; i++){              // play note for t ms 
    digitalWrite(PIN_BUZZER, HIGH); 
    delayMicroseconds(hperiod);
    digitalWrite(PIN_BUZZER, LOW); 
    delayMicroseconds(hperiod - 1);     // - 1 to make up for digitaWrite overhead
  }
pinMode(PIN_BUZZER, INPUT);                // shut off pin to avoid noise from other operations

}

void freqOut(int freq){
  digitalWrite(PIN_BUZZER, true);
  delayMicroseconds((500000 / freq) - 7);
  digitalWrite(PIN_BUZZER, false);
}
