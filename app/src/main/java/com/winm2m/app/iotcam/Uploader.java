package com.winm2m.app.iotcam;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Uploader {
    static final String attachmentName = "file";
    static final String attachmentFileName = "file.dummy";
    static final String crlf = "\r\n";
    static final String twoHyphens = "--";
    static final String boundary =  "*****";

    private final String apiURL;

    public Uploader(String apiURL) {
        this.apiURL = apiURL;
    }

    public String upload(byte[] data, String path) throws IOException {
        HttpURLConnection httpUrlConnection = null;
        URL url = new URL(apiURL + "?path=" + URLEncoder.encode(path, "UTF-8"));
        httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setUseCaches(false);
        httpUrlConnection.setDoOutput(true);

        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
        httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream request = new DataOutputStream(
                httpUrlConnection.getOutputStream());

        request.writeBytes(twoHyphens + boundary + crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName
                + "\";filename=\"" + attachmentFileName + "\"" + crlf);
        request.writeBytes(crlf);
        request.write(data);
        request.writeBytes(crlf);
        request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
        request.writeBytes(crlf);
        request.writeBytes(crlf);

        request.flush();
        request.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(httpUrlConnection.getInputStream())));
        StringBuilder sb = new StringBuilder();
        for(String line; (line = reader.readLine()) != null;) sb.append(line).append("\n");
        reader.close();
        String result = sb.toString();

        httpUrlConnection.disconnect();
        return result;
    }
}
