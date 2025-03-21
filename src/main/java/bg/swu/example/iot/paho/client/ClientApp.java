package bg.swu.example.iot.paho.client;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;

public class ClientApp {
    public static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
    public static final String TOPIC = "test-topic-123";

    public static void main(String[] args) {
        try {
            final MqttSubscriber subscriber = new MqttSubscriber(connectToBroker("test-subscriber"));
            subscriber.subscribe(TOPIC);

            Thread.sleep(3000);

            final MqttPublisher publisher = new MqttPublisher(connectToBroker("test-publisher"));
            publisher.publish(TOPIC, "Hello MQTT");

            Thread.sleep(3000);
            publisher.dispose();
            subscriber.dispose();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static MqttClient connectToBroker(String clientId) {
        try {
            final MqttClient client = new MqttClient(BROKER_URL, clientId);
            client.connect();
            final MqttConnectionOptions options = new MqttConnectionOptions();
            options.setCleanStart(true);
            options.setKeepAliveInterval(60); // in seconds

            if (!client.isConnected()) {
                client.connect(options);
            }
            return client;
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
