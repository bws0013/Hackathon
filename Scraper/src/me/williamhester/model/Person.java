package me.williamhester.model;

/**
 * Created by william on 2/13/16.
 */
public class Person {

    private String name;
    private String url;

    public Person(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
