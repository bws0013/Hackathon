package me.williamhester.network;

import me.williamhester.model.Person;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by william on 2/13/16.
 */
public class PeopleFinder {

    public static void getPerson() {
        FormBody requestBody = new FormBody.Builder()
                .add("gensearch", "weh")
                .add("submit", "Search")
                .add("person_code", "All")
                .build();
        Request request = new Request.Builder()
                .url("http://peoplefinder.auburn.edu/peoplefinder/index.php")
                .post(requestBody)
                .header("Cookie", "_ga=GA1.2.1822171430.1429040176; __utma=145219947.1822171430.1429040176." +
                        "1454909466.1454966508.6; __utmz=145219947.1454966508.6.5.utmcsr=google|utmccn=(organic)" +
                        "|utmcmd=organic|utmctr=(not%20provided); PHPSESSID=qdfb0j858qif8djset61v8tg44")
                .build();

        try {

            Document doc = Jsoup.parse(NetworkSingleton.getOkHttpClient().newCall(request).execute().body().string());
            Elements table = doc.select("tbody").first().select("tr");

            ArrayList<Person> people = new ArrayList<>();
            for (Element e : table) {
                Element e2 = e.select("td").first();
                String name = e2.text();
                String url = "http://peoplefinder.auburn.edu/peoplefinder/" + e2.select("a").first().attr("href");
                people.add(new Person(name, url));
            }
            for (Person p : people) {
                System.out.println(p.getName() + " " + p.getUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getPerson(Person person) {
        Request request = new Request.Builder()
                .url(person.getUrl())
                .header("Cookie", "_ga=GA1.2.1822171430.1429040176; __utma=145219947.1822171430.1429040176." +
                        "1454909466.1454966508.6; __utmz=145219947.1454966508.6.5.utmcsr=google|utmccn=(organic)" +
                        "|utmcmd=organic|utmctr=(not%20provided); PHPSESSID=qdfb0j858qif8djset61v8tg44")
                .build();
        try {
            Document doc = Jsoup.parse(NetworkSingleton.getOkHttpClient().newCall(request).execute().body().string());
            System.out.print(doc);
            Elements table = doc.select("tbody").first().select("tr");

            for (Element e : table) {
                String[] s = e.text().split(":");
                switch (s[0]) {
                    case "Role":

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}