package me.williamhester.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Mac on 2/13/16.
 */
public class TwitterAuthentication {
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

    public TwitterAuthentication(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public static TwitterAuthentication getAuthentication() {
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader("AccessToken.txt"));
            while (in.ready()) {
                String line = in.readLine();
                if (line.contains("ConsumerKey ")) {
                    a = line.replace("ConsumerKey = ", "");
                }

                else if (line.contains("ConsumerSecret ")) {
                    b = line.replace("ConsumerSecret = ", "");
                }

                else if (line.contains("AccessToken ")) {
                    c = line.replace("AccessToken = ", "");
                }

                else if (line.contains("AccessTokenSecret ")) {
                    d = line.replace("AccessTokenSecret = ", "");
                }
            }
            return new TwitterAuthentication(a, b, c, d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TwitterAuthentication(a, b, c, d);
    }
}
