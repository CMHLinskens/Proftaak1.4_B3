#define PIN_TRIGGER 27
#define PIN_ECHO 26

float detectionRange = 50.0f;
float distance = 0.0f;
bool isDetecting = false;

void setup(){
  Serial.begin(9600);
  pinMode(PIN_TRIGGER, OUTPUT);
  pinMode(PIN_ECHO, INPUT);
}

void loop() {
  startUltrasone();
  distance = getDetectedDistance();
  isDetecting = checkIfInRange(distance);
  
  Serial.print(isDetecting);
  Serial.print('\t');
  Serial.println(distance);
  
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
