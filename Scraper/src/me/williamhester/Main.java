package me.williamhester;

import me.williamhester.network.TwitterApi;
import twitter4j.auth.AccessToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {

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

    public static void main(String[] args) throws IOException {
        /*CourseCodes.setup();
        DeanList.read();*/
        TwitterApi.setAccount();
        twitterTest7();
        //PeopleFinder.getPerson();
    }
}
