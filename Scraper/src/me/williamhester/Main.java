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

    public static void main(String[] args) throws Exception {
        TwitterApi.setAccount();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-info":
                    name = args[++i];
                    printInfo();
                    break;
                case "-trends":
                    TwitterApi.getTrending();
                    break;
                case "-find":
                    TwitterApi.findBirthday(args[++i]);
                    break;
                case "-loc":
                    TwitterApi.getRecentLocation(args[++i]);
                    break;
                case "-hash":
                    TwitterApi.getTweets(args[++i]);
                    break;
            }
        }
    }

    private static void printInfo() throws Exception {
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
                System.out.println("Could not find anyone matching " + name + ".");
            }
            statement.close();
        }
    }

    private static String getName() {
        return AuburnPerson.makeId(name.toUpperCase().replace("'", "^"), name.toUpperCase().replace("'", "^")).replace("_", "");
    }
}
