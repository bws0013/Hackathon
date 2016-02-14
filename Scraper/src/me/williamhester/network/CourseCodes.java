package me.williamhester.network;

import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Mitch on 2/13/2016.
 */
public class CourseCodes {

    private static HashMap<String, String> courseCodes;
    private static HashMap<String, String> courseNames;

    public static void setup() {
        courseCodes = new HashMap<>();
        courseNames = new HashMap<>();
        try {
            Scanner readFile = new Scanner(new File("Courses.txt"));
            while (readFile.hasNext()) {
                String line = readFile.nextLine();
                int spIdx = line.indexOf(" ");
                String code = line.substring(0, spIdx);
                String desc = line.substring(spIdx + 1);
                System.out.println(code + " " + desc);
                courseCodes.put(code, desc);
                courseCodes.put(desc, code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setup_online() {
        courseCodes = new HashMap<>();
        courseNames = new HashMap<>();
        Request request = new Request.Builder()
                .url("https://web.auburn.edu/ir/dictionary/majorcodes.aspx").build();

        try {

            String response = NetworkSingleton.getOkHttpClient().newCall(request).execute().body().string();
            //   System.out.println(response);
            Document doc = Jsoup.parse(response);
            Elements table = doc.select("#display-data").first().select("tr");
            for (Element row : table) {
                String code = "", desc = "";
                Elements entries = row.select("td");
                int idx = 0;
                for (Element entry : entries) {
            //        s += entry.text() + " ";

                    if (idx == 1) {
                        code = entry.text();
                    } else if (idx == 2) {
                        desc = entry.text();
                    }
                    idx++;
                }
                System.out.println(code + " " + desc);
                courseCodes.put(code, desc);
                courseNames.put(desc, code);
         //       System.out.println(code + " -> " + desc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDescription(String code) {
        if (courseCodes.containsKey(code)) {
            return courseCodes.get(code);
        } else {
            return code;
        }
    }

    public static String getCode(String description) {
        if (courseNames.containsKey(description)) {
            return courseNames.get(description);
        } else {
            return "No Code";
        }
    }
}
