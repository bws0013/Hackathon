package me.williamhester.network;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import jdk.internal.org.xml.sax.ErrorHandler;
import me.williamhester.model.AuburnPerson;
import org.apache.commons.logging.LogFactory;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by william on 2/13/16.
 */
public class PeopleFinder2 {
    public static List<AuburnPerson> getAllPeople(String query) {
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            HtmlPage page = webClient.getPage("http://peoplefinder.auburn.edu/peoplefinder/");
            Thread.sleep(1000);
            HtmlInput input = page.getHtmlElementById("gensearch");
            input.setValueAttribute(query);
            input.type('\n');
            HtmlForm form = page.getFormByName("pfsearchform");
            page = form.getButtonByName("submit").click();

            ArrayList<AuburnPerson> people = new ArrayList<>();
            if (!page.getBody().getByXPath( "/div[class='panel-body']").isEmpty()) {
                people.add(PeopleFinder.getPerson(page.getWebResponse().getContentAsString()));
                System.out.println("Single person");
            } else {
                List<HtmlElement> elements = page.getBody().getElementsByTagName("a");
                List<HtmlAnchor> anchors = new ArrayList<>();
                for (HtmlElement e : elements) {
                    HtmlAnchor a = (HtmlAnchor) e;
                    if (a.getHrefAttribute().startsWith("index.php")) {
                        anchors.add(a);
                    }
                }

                System.out.println(anchors.size() + " anchors");
                for (HtmlAnchor a : anchors) {
                    Thread.sleep(2000);
                    HtmlPage p = a.click();
                    people.add(PeopleFinder.getPerson(p.getWebResponse().getContentAsString()));
                }
            }

            return people;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
