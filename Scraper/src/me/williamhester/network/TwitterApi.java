package me.williamhester.network;

import me.williamhester.model.TwitterAuthentication;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Mac on 2/13/16.
 */
public class TwitterApi {
    private static AccessToken token = new AccessToken("", "");

    public static List<String> getUser(String name) {
        ArrayList<String> userList = new ArrayList<>();
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            User owner = twitter.verifyCredentials();

            ResponseList<User> users = twitter.searchUsers("Johnny Appleseed", 1);
            if (users.size() > 0) {
                userList.add("Here are a few profiles matching \""  + name + "\".");
                for (User u : users) {
                    userList.add(u.getBiggerProfileImageURL());
                    userList.add(u.getName());
                    userList.add(u.getScreenName());
                    userList.add(u.getLocation());
                    userList.add("");
                }
            }
            return userList;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        if (userList.size() == 0) {
            userList.add("Sorry, I couldn't find any users with that name.");
        }
        return userList;
    }

    public static List<String> findBirthday(String accountName) {
        ArrayList<String> birthday = new ArrayList<>();
        try {
            boolean found = false;
            Twitter twitter = TwitterFactory.getSingleton();
            User user = twitter.users().showUser(accountName);
            birthday.add(user.getBiggerProfileImageURL());
            if (user != null) {
                if (!user.isProtected()) {
                    int i = 0;
                    while (true) {
                        i++;
                        ResponseList<Status> statuses = twitter.getUserTimeline(accountName, new Paging(i, 200));
                        for (Status s : statuses) {
                            if (!s.isRetweet()) {
                                if (s.getText().toLowerCase().contains("my birthday") && s.getText().toLowerCase().contains("today")) {
                                    String date = s.getCreatedAt().toString();
                                    StringTokenizer split = new StringTokenizer(date);
                                    String out = "";
                                    split.nextToken();
                                    out += split.nextToken() + " ";
                                    out += split.nextToken();
                                    out += " is " + s.getUser().getName() + "'s birthday!";
                                    birthday.add(out);
                                    found = true;
                                    break;
                                } else if (s.getText().toLowerCase().contains("my birthday") && s.getText().toLowerCase().contains("tomorrow")) {
                                    String date = s.getCreatedAt().toString();
                                    StringTokenizer split = new StringTokenizer(date);
                                    String out = "";
                                    split.nextToken();
                                    out += split.nextToken() + " ";
                                    out += (Integer.parseInt(split.nextToken()) + 1);
                                    out += " is " + s.getUser().getName() + "'s birthday!";
                                    birthday.add(out);
                                    found = true;
                                    break;
                                } else if (s.getText().toLowerCase().contains("my birthday")) {
                                    birthday.add("I couldn't find the exact birthday, but here are a few tweets that might be relevent:");
                                    birthday.add("@" + s.getUser().getScreenName() + " " + s.getText() + " " + s.getCreatedAt().toString());
                                }
                            }
                        }
                        if (found || statuses.size() == 0 || i > 15) {
                            break;
                        }

                    }
                    if (birthday.size() < 2) {
                        birthday.add("Sorry, but I couldn't find " + accountName + "'s birthday.");
                    }
                } else {
                    birthday.add("I'm sorry, but " + user.getName() + "'s account is protected.");
                }
            } else {
                birthday.add("I'm sorry, but " + accountName + " doesn't exist.");
            }
            return birthday;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return birthday;
    }

    public static List<String> getRecentLocation(String accountName) {
        ArrayList<String> location = new ArrayList<>();
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            User user = twitter.users().showUser(accountName);
            location.add(user.getBiggerProfileImageURL());
            if (user != null) {
                if (!user.isProtected()) {
                    int i = 0;
                    boolean found = false;
                    while (true) {
                        i++;
                        ResponseList<Status> statuses = twitter.getUserTimeline(accountName, new Paging(i, 200));
                        for (Status s : statuses) {
                            if (s.getPlace() != null) {
                                found = true;
                                location.add(s.getUser().getName() + "'s most recent location is " + s.getPlace().getFullName() + ".");
                                break;
                            }
                        }
                        if (found || i > 20) {
                            break;
                        }
                    }
                    if (location.size() < 2) {
                        location.add("I'm sorry, I could'nt find " + accountName + "'s most recent location.");
                    }
                } else {
                    location.add("I'm sorry, but " + user.getName() + "'s account is protected.");
                }
            } else {
                location.add("I'm sorry, but " + accountName + " doesn't exist.");
            }
        } catch (TwitterException e) {
            if (location.size() == 0) {
                location.add("I'm sorry, I could'nt find " + accountName + "'s most recent location.");
            }
            e.printStackTrace();
        }
        return location;
    }

    public static void getLimit() {
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            HashMap<String, RateLimitStatus> status = new HashMap<>(twitter.getRateLimitStatus());
            for (String s : status.keySet()) {
                System.out.println(s);
                System.out.println(status.get(s).getRemaining() + "");
                System.out.println(status.get(s).getSecondsUntilReset() + "");
                System.out.println("");
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public static void setAccount() {
        Twitter twitter = TwitterFactory.getSingleton();
        TwitterAuthentication ta = TwitterAuthentication.getAuthentication();
        twitter.setOAuthConsumer(ta.getConsumerKey(), ta.getConsumerSecret());
        twitter.setOAuthAccessToken(new AccessToken(ta.getAccessToken(), ta.getAccessTokenSecret()));

    }

    public static List<String> getTrending() {
        ArrayList<String> trending = new ArrayList<>();
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            Trends trends = twitter.trends().getPlaceTrends(1);
            int i = 0;
            trending.add("*Here's what is trending globally:*");
            trending.add("");
            for (Trend t : trends.getTrends()) {
                trending.add(t.getName());
                trending.add(t.getURL());
                trending.add("");
                i++;
                if (i == 10) {
                    break;
                }
            }
        } catch (TwitterException e) {

        }
        return trending;
    }

    /*public static List<String> getTrendingLocation(String city) {
        ArrayList<String> trending = new ArrayList<>();
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            GeoLocation location = null;
            int id = 0;
            query.setQuery(city);
            query.setMaxResults(5);
            ResponseList<Place> places = twitter.searchPlaces(query);
            for (Place p : places) {
                if (p.getFullName().toLowerCase().contains(city.toLowerCase())) {
                    GeoLocation[][] geolocation = p.getGeometryCoordinates();
                    location = new GeoLocation()
                }
            }
            int i = 0;
            trending.add("*Here's what is trending in " + city + ":*");
            trending.add("");
            Trends trends = twitter.trends().getClosestTrends();
            System.out.println(trends.toString());
            for (Trend t : trends.getTrends()) {
                trending.add(t.getName());
                trending.add(t.getURL());
                trending.add("");
                i++;
                if (i == 10) {
                    break;
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return trending;
    }*/

    public static List<String> getTweets(String hashtag) {
        ArrayList<String> tweets = new ArrayList<>();
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            Query query = new Query("");
            query.setCount(10);
            query.setQuery("#" + hashtag);
            List<Status> list = twitter.search(query).getTweets();
            if (list.size() > 0) {
                tweets.add("*Here are a few tweets involving #" + hashtag+ ":*");
                tweets.add("");
                for (Status s : list) {
                    tweets.add("@" + s.getUser().getName() + " " + s.getText());
                    tweets.add(s.getCreatedAt().toString());
                    tweets.add("");
                }
            }
        } catch (TwitterException e) {}
        if (tweets.size() == 0) {
            tweets.add("Sorry, but I couldn't find any tweets for #" + hashtag + ".");
        }
        return tweets;
    }
}
