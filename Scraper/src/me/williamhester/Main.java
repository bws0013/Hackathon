package me.williamhester;

import me.williamhester.network.DeanList;
import me.williamhester.network.PeopleFinder;

public class Main {

    /**
     * Our worker loop for the scraper. Any API requests should be funneled through here.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        DeanList.read();
        PeopleFinder.getPerson();
    }
}
