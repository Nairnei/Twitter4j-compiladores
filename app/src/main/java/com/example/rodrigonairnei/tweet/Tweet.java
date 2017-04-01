package com.example.rodrigonairnei.tweet;

/**
 * Created by Rodrigo Nairnei on 01/04/2017.
 */

class Tweet {

    int id;
    String user;
    String text;
    String image;
    String link;

    public Tweet(int id, String user, String text, String image, String link) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.image = image;
        this.link = link;
    }
}
