package me.williamhester;

import me.williamhester.network.DeanList;
import me.williamhester.network.PeopleFinder;
import me.williamhester.network.PeopleFinder2;

public class Main {

    /**
     * Our worker loop for the scraper. Any API requests should be funneled through here.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        String chars = "aaa";
        if (args.length == 1) {
            chars = args[0];
        }
        for (char c = chars.charAt(0); c <= 'z'; c++) {
            for (char d = chars.charAt(1); d <= 'z'; d++) {
                for (char e = chars.charAt(2); e <= 'z'; e++) {
                    System.out.println("" + c + d + e);
                    System.out.println(PeopleFinder2.getAllPeople("" + c + d + e) + ",");
                }
            }
        }
    }
}
