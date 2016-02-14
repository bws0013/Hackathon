package me.williamhester;

import me.williamhester.model.AuburnPerson;
import me.williamhester.network.TwitterApi;
import twitter4j.auth.AccessToken;
import me.williamhester.network.CourseCodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static String name;

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

    private static String getName() {
        return AuburnPerson.makeId(name.toUpperCase().replace("'", "^"), name.toUpperCase().replace("'", "^")).replace("_", "");
    }

    public static void main(String[] args) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        name = "Mitchell Price";

        Connection conn;
        String url = "jdbc:mysql://127.0.0.1:3306/hackathon";
        String user = "root";
        try {
            conn = DriverManager.getConnection(url, user, "");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (conn != null) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM people WHERE id='" + getName() + "';");
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                System.out.println(new AuburnPerson(set));
            } else {
                System.out.println("Could not find anyone.");
            }
            statement.close();
        }
    }
}
