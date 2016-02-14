package me.williamhester.network;

import me.williamhester.model.AuburnPerson;
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

        ArrayList<AuburnPerson> people = new ArrayList<>();

        int NUM_PAGES = 355;

        for (int i = 1; i <= 5; i++) {

            //jSystem.out.println("READING " + i + "!");
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
                    Elements entries = row.select("td");
                    int idx = 0;
                    String[] rowData = new String[12];
                    for (Element entry : entries) {
                        rowData[idx] = entry.text();
                        idx++;
                    }
                    people.add(new AuburnPerson(rowData));
                }

                for (AuburnPerson p : people) {
                    System.out.println(p.getSummary());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
