package com.winm2m.app.iotcam;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttHandler {
    Context context;
    String serviceUri;
    String userName = null;
    String password = null;

    MqttAndroidClient mqttAndroidClient = null;
    boolean ready = false;
    String keepAliveMessage = "";

    public MqttHandler(Context context, String serviceURI, String userName, String password) {
        this.context = context;
        this.serviceUri = serviceURI;
        this.userName = userName;
        this.password = password;

        mqttAndroidClient = new MqttAndroidClient(context, serviceURI, MqttClient.generateClientId());
    }
    
    public void connect(final IMqttActionListener callback) {
        try {
            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());
                    callback.onSuccess(asyncActionToken);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    callback.onFailure(asyncActionToken, exception);
                }
            });
        } catch(MqttException e) {
        }
    }

    private MqttConnectOptions getMqttConnectionOption() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        if (this.userName != null && ! "null".equals(this.userName)) mqttConnectOptions.setUserName(userName);
        if (this.password != null && ! "null".equals(this.password)) mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setWill("argos-synspae", "keep alive".getBytes(), 1, true);
        return mqttConnectOptions;
    }

    public DisconnectedBufferOptions getDisconnectedBufferOptions() {
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(100);
        disconnectedBufferOptions.setPersistBuffer(true);
        disconnectedBufferOptions.setDeleteOldestMessages(false);
        return disconnectedBufferOptions;
    }

    public void subscribe(String topic, IMqttMessageListener listener) {
        try {
            mqttAndroidClient.subscribe(topic, 0, listener);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message) {
        try {
            mqttAndroidClient.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribe(String topic) {
        try {
            mqttAndroidClient.unsubscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttAndroidClient getMqttAndroidClient() {
        return mqttAndroidClient;
    }
}
