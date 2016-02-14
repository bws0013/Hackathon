package me.williamhester;

import me.williamhester.network.CourseCodes;
import me.williamhester.network.DeanList;
import me.williamhester.network.TwitterApi;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static void twitterTest1() throws IOException, TwitterException {
        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("YvHpP4i1s3LNq5S1gqGTyE5TU", "SZKksZSJMraTIloGYJHO597E0b2MwcMjp12N0p0q4hZmVozQqF");
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.println("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
            String pin = br.readLine();
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                } else {
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    System.out.println("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
        }
        //persist to the accessToken for future reference.
        //storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
    }

    private static void twitterTest2() {
        ArrayList<String> user = new ArrayList<>(TwitterApi.getUser("John Harrison"));
        for (String s : user) {
            System.out.println(s);
        }
    }

    private static void twitterTest4() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the name of the person's birthday below:");
        String name = in.readLine();
        List<String> user = TwitterApi.findBirthday(name);
        for (String s : user) {
            System.out.println(s);
        }
    }

    private static void twitterTest5() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the name of the person below:");
        String name = in.readLine();
        ArrayList<String> location = new ArrayList<>(TwitterApi.getRecentLocation(name));
        for (String s : location) {
            System.out.println(s);
        }

    }

    private static void twitterTest7() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Put in the hashtag below:");
        String name = in.readLine();
        ArrayList<String> location = new ArrayList<>(TwitterApi.getTweets(name));
        for (String s : location) {
            System.out.println(s);
        }

    }

    private static void storeAccessToken(long useId, AccessToken accessToken) {
        //store accessToken.getToken()
        //store accessToken.getTokenSecret()
    }

    public static void main(String[] args) {
        CourseCodes.setup();
        DeanList.read();
        //PeopleFinder.getPerson();
    }
}
