package com.winm2m.app.iotcam;

public class Config {
    String mqttHost;
    String mqttPort;
    String mqttUser;
    String mqttPass;
    String serial;
    String uploadUrl;
    String format;

    final static String DEFAULT_MQTT_HOST = "hub.winm2m.com";
    final static String DEFAULT_MQTT_PORT = "46083";
    final static String DEFAULT_SERIAL = "2ic00000";


    public String getMqttPort() {
        return mqttPort;
    }

    public void setMqttPort(String mqttPort) {
        this.mqttPort = mqttPort;
    }

    public String getMqttUser() {
        return mqttUser;
    }

    public void setMqttUser(String mqttUser) {
        this.mqttUser = mqttUser;
    }

    public String getMqttPass() {
        return mqttPass;
    }

    public void setMqttPass(String mqttPass) {
        this.mqttPass = mqttPass;
    }

    public String getMqttHost() {
        return mqttHost;
    }

    public void setMqttHost(String mqttHost) {
        this.mqttHost = mqttHost;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    private Config setDefault() {
        setMqttHost(DEFAULT_MQTT_HOST);
        setMqttPort(DEFAULT_MQTT_PORT);
        setSerial(DEFAULT_SERIAL);
        setUploadUrl("http://172.20.110.18:14000/api/upload");
        setFormat("{sessionId}-a.jpg");
        return this;
    }

    /* TODO : develop */
    public static Config load() {
        return new Config().setDefault();
    }

    public void save() {

    }
}
