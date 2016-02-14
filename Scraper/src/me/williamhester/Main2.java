package me.williamhester;

import me.williamhester.model.AuburnPerson;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by william on 2/14/16.
 */
public class Main2 {

    public static void main(String[] args) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Connection conn;
        String url = "jdbc:mysql://127.0.0.1:3306/hackathon";
        String user = "root";
        try {
            conn = DriverManager.getConnection(url, user, "");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        HashSet<String> seenBefore = new HashSet<>();

        if (conn != null) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM people;");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                seenBefore.add(new AuburnPerson(set).getId());
            }
            statement.close();
        }

        Scanner in = new Scanner(new File("outputRev.txt"));

        TreeMap<String, List<String>> map = new TreeMap<>();

        String name = in.nextLine();
        while (in.hasNextLine()) {
            List<String> list = new ArrayList<>();
            map.put(name.replace("'", "^"), list);
            String club = in.nextLine();
            while (club.startsWith("$") && in.hasNextLine()) {
                list.add(club.substring(1));
                if (in.hasNextLine()) {
                    club = in.nextLine();
                }
            }
            if (name.contains("Emily Tripp")) {
                list.add("zewclub");
            }
            name = club;
        }

        System.out.println("USE hackathon;");
        for (String s : map.keySet()) {
            name = s.replace("'", "^");
            if (seenBefore.contains(toId(name))) {
                System.out.printf("UPDATE people SET organizations='%s' WHERE id='%s';\n", listToCsv(map.get(name), name),
                        toId(name));
            } else {
                System.out.printf("INSERT INTO people (`id`, `name`, `organizations`) VALUES ('%s', '%s', '%s');\n",
                        toId(name), name, listToCsv(map.get(name), name));
            }
        }
    }

    private static String listToCsv(List<String> clubs, String name) {
        StringBuilder sb = new StringBuilder();
        for (String s : clubs) {
            sb.append(s).append(',');
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 1, sb.length());
        } else {
            System.out.println(name);
        }
        return sb.toString();
    }

    private static String toId(String s) {
        return AuburnPerson.makeId(s.toUpperCase().replace("'", "^"), s.toUpperCase().replace("'", "^")).replace("_", "");
    }

}
