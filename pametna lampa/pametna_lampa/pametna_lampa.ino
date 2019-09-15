#include <ESP8266WiFi.h>
#include <PubSubClient.h>


const char* ssid = "MW40V_7B62";
const char* password = "77547599";

//online server
const char* mqtt_server = "broker.hivemq.com";

const char* flag = "0";
long timeBetweenMessages = 1000 * 20 * 1;
long lastMsg = 0;

WiFiClient espClient;
PubSubClient client(espClient);








void setup_wifi() {
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  pinMode(5,OUTPUT);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("WiFi connected - ESP IP address: ");
  Serial.println(WiFi.localIP());
}


void callback(String topic, byte* message, unsigned int length) {
  Serial.print("Message arrived on topic: ");
  Serial.print(topic);
  Serial.print(". Message: ");
  String messageTemp;
  
  for (int i = 0; i < length; i++) {
    Serial.print((char)message[i]);
    messageTemp += (char)message[i];
  }
  Serial.println();

//promijeniti temu ovdje:
  if(topic=="house/kitchen/light"){
      Serial.print("Changing Room lamp to ");
      if(messageTemp == "0"){
        digitalWrite(BUILTIN_LED, HIGH);
        digitalWrite(5,HIGH);
        Serial.print("On");
        flag="0";
      }
      else if(messageTemp == "1"){
        digitalWrite(BUILTIN_LED, LOW);
        digitalWrite(5,LOW);
        Serial.print("Off");
        flag="1";
      }
  }
  Serial.println();
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
  
   //promijeniti ime kljenta, ne mogu biti dva kljenta istog ID-a
    if (client.connect("ESP8266Client2")) {
      Serial.println("connected");  
      //topic na koju se subscriba 
      client.subscribe("house/kitchen/light");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
}

void setup() {
  pinMode(BUILTIN_LED, OUTPUT);
  Serial.begin(115200);
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  // ovdje je publish kad se upali da inicijalizira svoje stanje drugim uređajima prijavljenim na temu
   if (client.publish("house/kitchen/light", flag)) {
      Serial.println("Publish ok");
    }

}


void loop() {
  if (!client.connected()) {
    reconnect();
  }
  if(!client.loop())
    client.connect("ESP8266Client");
  long now = millis();
  if (now - lastMsg > timeBetweenMessages ){
    lastMsg = now;
   /* if (client.publish("svjetlo/kuhinja", flag)) {
      Serial.println("Publish ok");
  }*/
}
} 
