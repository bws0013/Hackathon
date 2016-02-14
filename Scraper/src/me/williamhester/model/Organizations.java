package me.williamhester.model;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by william on 2/14/16.
 */
public class Organizations {

    private static HashMap<String, String> organizations = new HashMap<>();

    private static void init() {
        try {
            Scanner in = new Scanner(new File("orgsRenamed.txt"));
            while (in.hasNextLine()) {
                String[] s = in.nextLine().split(" # ");
                organizations.put(s[0], s[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getOrg(String name) {
        if (organizations.size() == 0) {
            init();
        }
        return organizations.containsKey(name) ? organizations.get(name) : name;
    }

}
