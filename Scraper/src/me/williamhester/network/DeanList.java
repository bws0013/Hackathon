package me.williamhester.network;

import me.williamhester.model.Person;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Mitch on 2/13/2016.
 */
public class DeanList {

    public static void read() {

        ArrayList<String> people = new ArrayList<>();

        for (int i = 1; i <= 355; i++) {

            System.out.println("READING " + i + "!");
            Request request = new Request.Builder()
                    .url("http://cws.auburn.edu/ocm/deanslist/Home/Page?page=" + i)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .build();

            try {

                String response = NetworkSingleton.getOkHttpClient().newCall(request).execute().body().string();
                //   System.out.println(response);
                Document doc = Jsoup.parse(response);
                Elements table = doc.select("tbody").first().select("tr");


                for (Element row : table) {
                    String s = "";
                    Elements entries = row.select("td");
                    for (Element entry : entries) {
                        s += entry.text() + " ";
                    }
                    people.add(s);
                }

                for (String s : people) {
                    System.out.println(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
