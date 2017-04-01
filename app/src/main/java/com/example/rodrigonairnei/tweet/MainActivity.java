package com.example.rodrigonairnei.tweet;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import twitter4j.Twitter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.buttonText);

        String tweetWords = "#windows #Linux";
        int nTweets = 10;

        Twitter twitter = TwitterUtils.createTwitter();
        ArrayList<Tweet> data = TwitterUtils.search(twitter, tweetWords, nTweets);

       Iterator i = data.iterator();
        while(i.hasNext())
        {
            Tweet tweet = (Tweet)i.next();
            System.out.println(tweet.text);
            textView.setText(tweet.text);
        }
    }
}
