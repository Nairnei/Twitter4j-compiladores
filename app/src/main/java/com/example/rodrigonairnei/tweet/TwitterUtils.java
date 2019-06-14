package com.example.rodrigonairnei.tweet;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterUtils {

     //https://developer.twitter.com/en/docs/basics/authentication/guides/securing-keys-and-tokens.html
     
     private static final String token = ""; //TODO
     private static final String tokenSecret = ""; // TODO
     private static final String consumerKey = ""; //TODO
     private static final String consumerSecret = ""; //TODO

     //Site para redirecinamento de tweets
     private static final String tweetLink = "https://twitter.com/tweet/status/";

     public static Twitter twitter = null;
     public static Twitter createTwitter(){
          if (twitter == null) {
               twitter = TwitterFactory.getSingleton();
               AccessToken accessToken = new AccessToken(token, tokenSecret);
               twitter.setOAuthConsumer(consumerKey, consumerSecret);
               twitter.setOAuthAccessToken(accessToken);
          }
          return twitter;
     }

     public static ArrayList<Tweet> search(Twitter twitter, String queryString, int tweetCount){
          try{
               Query query = new Query(queryString);
               query.setCount(tweetCount);

               QueryResult result = twitter.search(query);

               ArrayList<Tweet> tweets = new ArrayList<Tweet>();
               for (Status status : result.getTweets()) {
                    int id = (int) status.getId();
                    String user = status.getUser().getName();
                    String text = status.getText();
                    String image = status.getUser().getProfileImageURL();
                    String link = tweetLink + status.getId(); // Cria URL para o tweet na web.
                    Tweet tweet = new Tweet(id, user, text, image, link);
                    tweets.add(tweet);
               }
               return tweets;
          } catch(TwitterException e){
               System.out.println("Erro no twitter");
               e.printStackTrace();
               return new ArrayList<Tweet>();
          } catch(NullPointerException e){
               System.out.println("Provavelmente Twitter nulo sendo passado para o método search.");
               e.printStackTrace();
               return new ArrayList<Tweet>();
          } catch(IllegalArgumentException e){
               System.out.println("Erro de log.");
               e.printStackTrace();
               return new ArrayList<Tweet>();
          }
     }
}
