#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

/* ------------------- WIFI CONFIG ------------------- */
const char* ssid = "Toji";
const char* password = "22051977";
const char* serverUrl = "http://10.49.78.34/api";

/* ------------------- HARDWARE ------------------- */
const int builtinLed = 2;   // GPIO 2 (Built-in LED)
const int potPin = 34;      // Analog input (ADC1 pin)

/* ------------------- PWM SETTINGS ------------------- */
const int freq = 5000;
const int resolution = 8;   // 8-bit = 0-255

/* ------------------- SETUP ------------------- */
void setup() {
  Serial.begin(115200);

  pinMode(builtinLed, OUTPUT);
  // Note: GPIO 34 is Input only, no pinMode needed for ADC, but we'll ensure it's clean
  
  // Set ADC resolution to 12-bit (0-4095) for full range
  analogReadResolution(12);
  // Set attenuation to 11dB to read 0 - 3.3V
  analogSetAttenuation(ADC_11db);

  // ✅ ESP32 Core 3.x PWM setup
  ledcAttach(builtinLed, freq, resolution);

  connectWiFi();
  sendLog("ESP32 Booted Successfully");
}

/* ------------------- LOOP ------------------- */
void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    updateState();
  } 
  else {
    Serial.println("WiFi Lost. Reconnecting...");
    connectWiFi();
  }

  delay(1000);  // 1 second
}

/* ------------------- WIFI CONNECT ------------------- */
void connectWiFi() {
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nWiFi Connected!");
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP());
}

/* ------------------- GET STATE FROM SERVER ------------------- */
void updateState() {
  WiFiClient client;
  HTTPClient http;

  String url = String(serverUrl) + "/state";
  http.begin(client, url);

  int httpCode = http.GET();

  if (httpCode > 0) {
    String payload = http.getString();
    Serial.println("Server Response: " + payload);

    StaticJsonDocument<200> doc;
    DeserializationError error = deserializeJson(doc, payload);

    if (!error) {
      bool digital = doc["digital"];
      int pwm = doc["pwm"];
      pwm = constrain(pwm, 0, 255);

      if (!digital) {
        ledcWrite(builtinLed, 0);
      } else {
        ledcWrite(builtinLed, pwm);
      }

      Serial.print("Digital: ");
      Serial.print(digital);
      Serial.print(" | PWM: ");
      Serial.println(pwm);

      // 🔄 SIMULATED ANALOG READ (Software Loopback)
      // Since no physical potentiometer is connected, we simulate the 12-bit ADC reading
      // by mapping the PWM brightness (0-255) to a 12-bit range (0-4095).
      int simulatedAnalog = map(pwm, 0, 255, 0, 4095);
      
      Serial.print("Simulated Analog (12-bit): ");
      Serial.println(simulatedAnalog);

      // Send this back to the server so the website card updates!
      sendAnalogValue(simulatedAnalog);
    } 
    else {
      Serial.println("JSON Parse Failed!");
    }
  } 
  else {
    Serial.print("HTTP Error: ");
    Serial.println(httpCode);
  }

  http.end();
}


/* ------------------- SEND ANALOG VALUE ------------------- */
void sendAnalogValue(int value) {
  WiFiClient client;
  HTTPClient http;

  // Use URL parameter as handled by the Spring Boot controller
  String url = String(serverUrl) + "/analog?value=" + String(value);
  http.begin(client, url);

  int httpCode = http.POST("");

  if (httpCode > 0) {
    Serial.print("Analog Sent: ");
    Serial.println(value);
  } 
  else {
    Serial.print("Analog POST Error: ");
    Serial.println(httpCode);
  }

  http.end();
}

/* ------------------- SEND LOG ------------------- */
void sendLog(String msg) {
  WiFiClient client;
  HTTPClient http;

  msg.replace(" ", "%20");
  String url = String(serverUrl) + "/log?message=" + msg;

  http.begin(client, url);
  int httpCode = http.POST("");

  if (httpCode > 0) {
    Serial.println("Log Sent Successfully");
  } 
  else {
    Serial.println("Log Failed");
  }

  http.end();
}
