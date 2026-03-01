

# HexHive

HexHive is a full-stack IoT project that connects a Spring Boot backend with an ESP32 to control and monitor an LED in real time.

It provides REST APIs for digital control, PWM brightness adjustment, analog value simulation, and serial logging. LED state is persisted using JPA so it survives application restarts.

---

## 🚀 Features

* LED ON/OFF control
* PWM brightness control (0–255)
* Analog value simulation (0–1023)
* Serial log message handling from ESP32
* RESTful API endpoints
* Simple web interface
* Persistent state using JPA

---

## 🛠 Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA
* Maven
* ESP32 (Arduino sketch included)

---

## 📂 Project Structure

```
hexhive/
├── src/main/java/com/example/hexhive
│   ├── controller
│   ├── service
│   ├── repository
│   └── model
├── src/main/resources
│   ├── templates
│   └── application.properties
├── esp32_led_control.ino
└── pom.xml
```

---

## 🔌 API Endpoints

Base URL:

```
http://localhost:8080/api
```

### Get Current State

```
GET /state
```

### Set Digital Value

```
POST /digital?value=true
```

### Set PWM Brightness

```
POST /pwm?value=128
```

### Set Analog Value

```
POST /analog?value=700
```

### Send Serial Log Message

```
POST /log?message=HelloESP32
```

---

## ▶️ Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/your-username/hexhive.git
cd hexhive
```

### 2. Run with Maven

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The server will start at:

```
http://localhost:8080
```

---

## 🔧 ESP32 Setup

1. Open `esp32_led_control.ino` in the Arduino IDE.
2. Select your ESP32 board.
3. Update WiFi credentials if needed.
4. Upload the sketch.
5. Make sure the ESP32 points to your backend server IP address.

---

## 💡 How It Works

* The backend maintains a single LED state record in the database.
* Each API call updates that state.
* The ESP32 syncs LED behavior based on backend responses.
* The web interface reflects the current LED status.

---

## 🧪 Testing

```bash
./mvnw test
```

---

## 📌 Future Improvements

* WebSocket-based real-time updates
* Authentication and access control
* Support for multiple devices
* Improved dashboard UI

---

## 📄 License

HexHive
