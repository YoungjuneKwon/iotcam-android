package com.winm2m.app.iotcam;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {
    String mqttHost;
    String mqttPort;
    String mqttUser;
    String mqttPass;
    String serial;
    String uploadUrl;
    String format;

    final static String DEFAULT_MQTT_HOST = "mqtt.host";
    final static String DEFAULT_MQTT_PORT = "mqtt.port";
    final static String DEFAULT_SERIAL = "dummy";
    final static String CONFIG_PATH = "/data/data/com.winm2m.app.iotcam/config";


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

    public static Config load() {
        Config c = new Config().setDefault();
        if (!new File(CONFIG_PATH).exists()) c.save();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CONFIG_PATH));
            Map<String, String> m = new HashMap<>();
            for(String line=""; (line=reader.readLine())!=null && line.trim().length() > 0;){
                m.put(line.split("=")[0].trim(), line.split("=")[1].trim());
            }
            reader.close();
            if (m.containsKey("mqtt_host")) {c.setMqttHost(m.get("mqtt_host"));}
            if (m.containsKey("mqtt_port")) {c.setMqttPort(m.get("mqtt_port"));}
            if (m.containsKey("mqtt_user")) {c.setMqttUser(m.get("mqtt_user"));}
            if (m.containsKey("mqtt_pass")) {c.setMqttPass(m.get("mqtt_pass"));}
            if (m.containsKey("serial")) {c.setSerial(m.get("serial"));}
            if (m.containsKey("upload_url")) {c.setUploadUrl(m.get("upload_url"));}
            if (m.containsKey("format")) {c.setFormat(m.get("format"));}
        } catch (IOException e){}
        return c;
    }

    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH));
            writer.write("mqtt_host=" + this.mqttHost);
            writer.newLine();
            writer.write("mqtt_port=" + this.mqttPort);
            writer.newLine();
            writer.write("mqtt_user=" + this.mqttUser);
            writer.newLine();
            writer.write("mqtt_pass=" + this.mqttPass);
            writer.newLine();
            writer.write("serial=" + this.serial);
            writer.newLine();
            writer.write("upload_url=" + this.uploadUrl);
            writer.newLine();
            writer.write("format=" + this.format);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
