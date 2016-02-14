package me.williamhester;

import me.williamhester.model.AuburnPerson;
import me.williamhester.network.CourseCodes;
import me.williamhester.network.TwitterApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final String ZIPCODE = "zip";
    private static final String COUNTY = "county";
    private static final String CITY = "city";
    private static final String STATE = "state";

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
                    TwitterApi.getUser(args[++i]);
                    break;
                case "-bday":
                    TwitterApi.findBirthday(args[++i]);
                    break;
                case "-loc":
                    TwitterApi.getRecentLocation(args[++i]);
                    break;
                case "-hash":
                    TwitterApi.getTweets(args[++i]);
                    break;
                case "-mjpl":
                    whoIsInMajorFromPlace(args[++i], args[++i]);
                    break;
            }
        }
    }

    private static Connection getDbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String url = "jdbc:mysql://127.0.0.1:3306/hackathon";
        String user = "root";
        try {
            return DriverManager.getConnection(url, user, "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void whoIsInMajorFromPlace(String major, String place) throws Exception {
        String type;
        if (place.matches("[0-9]*")) {
            type = ZIPCODE;
        } else if (place.toLowerCase().endsWith(" county")) {
            type = COUNTY;
            place = place.substring(0, place.toLowerCase().indexOf(" county"));
        } else if (place.length() == 2) {
            type = STATE;
        } else {
            type = CITY;
        }

        major = major.toUpperCase();

        Connection connection = getDbConnection();
        if (connection != null) {

            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT * FROM people WHERE %s='%s' AND major='%s';", type, place, major));
            ResultSet set = statement.executeQuery();

            ArrayList<String> names = new ArrayList<>();
            if (set.next()) {
                names.add(new AuburnPerson(set).getName());
                while (set.next()) {
                    names.add(new AuburnPerson(set).getName());
                }
            } else {
                System.out.println("Sorry, I didn't find any matches.");
            }
            if (type.equals(COUNTY)) {
                place += " County";
            }
            if (names.size() > 0) {
                if (names.size() > 1) {
                    System.out.printf(makeAndString(names) + " are all from %s and are majoring in %s.\n",
                            place, CourseCodes.getDescription(major));
                } else {
                    System.out.printf(makeAndString(names) + " is from %s and is majoring in %s.\n",
                            place, CourseCodes.getDescription(major));
                }
            }
        }
    }

    private static void printInfo() throws Exception {
        Connection conn = getDbConnection();
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

    private static String makeAndString(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size() - 2; i++) {
            sb.append(strings.get(i)).append(", ");
        }
        if (strings.size() > 1) {
            sb.append(strings.get(strings.size() - 2)).append(" and ");
        }
        return sb.append(strings.get(strings.size() - 1)).toString();
    }
}
