package me.williamhester.network;

import me.williamhester.model.AuburnPerson;
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

    private static String cookie = "_ga=GA1.2.1822171430.1429040176; PHPSESSID=qdfb0j858qif8djset61v8tg44; __utma=145219947.1822171430.1429040176.1454966508.1455397604.7; __utmb=145219947.1.10.1455397604; __utmc=145219947; __utmz=145219947.1455397604.7.6.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)";

    public static void getPerson() {
        FormBody requestBody = new FormBody.Builder()
                .add("gensearch", "weh")
                .add("submit", "Search")
                .add("person_code", "All")
                .build();
        Request request = new Request.Builder()
                .url("http://peoplefinder.auburn.edu/peoplefinder/index.php")
                .post(requestBody)
                .header("Cookie", cookie)
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
                System.out.println(p.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AuburnPerson getPerson(String html) {
        AuburnPerson p = new AuburnPerson();
        try {
            Document doc = Jsoup.parse(html);
            p.setName(doc.select(".panel-heading").first().select("h3").text());

            Elements table = doc.select("tbody").first().select("tr");

            for (Element e : table) {
                String[] s = e.text().split(":");
                String data = s[1].trim();
                switch (s[0]) {
                    case "Roles":
                    case "Role":
                        p.setRole(data);
                        break;
                    case "Mailing Address":
                        p.setMailingAddress(data);
                        break;
                    case "Department":
                        p.setDepartment(data);
                        break;
                    case "Phone":
                        p.setPhone(data);
                        break;
                    case "Email":
                        p.setEmail(data);
                        break;
                }
            }
            System.out.println(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

}
