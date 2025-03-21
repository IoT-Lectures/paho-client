package bg.swu.example.iot.paho.client;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

public class MqttPublisher {
    private final MqttClient mqttClient;

    public MqttPublisher(MqttClient client) {
        mqttClient = client;
    }

    public void publish(String topic, String message) {
        final MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(2);
        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void dispose() {
        try {
            if (mqttClient.isConnected()) {
                mqttClient.disconnect();
            }
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
