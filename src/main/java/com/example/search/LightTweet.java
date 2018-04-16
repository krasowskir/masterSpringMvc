package com.example.search;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

public class LightTweet {

    private String user;
    private String text;
    private String lang;
    private String profileImageUrl;
    private LocalDateTime date;
    private Integer retweetCount;

    public String getUser() {
	return user;
    }

    public String getText() {
	return text;
    }

    public String getLang() {
	return lang;
    }

    public String getProfileImageUrl() {
	return profileImageUrl;
    }

    public LocalDateTime getDate() {
	return date;
    }

    public Integer getRetweetCount() {
	return retweetCount;
    }

    public LightTweet(String text) {
	this.text = text;
    }

    public static LightTweet ofTweet(Tweet tweet) {
	LightTweet lightTweet = new LightTweet(tweet.getText());
	Date createdAt = tweet.getCreatedAt();
	if (createdAt != null) {
	    lightTweet.date = LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault());
	}
	TwitterProfile tweetUser = tweet.getUser();
	if (tweet.getUser() != null) {
	    lightTweet.user = tweetUser.getName();
	    lightTweet.profileImageUrl = tweetUser.getProfileImageUrl();
	}
	lightTweet.lang = tweet.getLanguageCode();
	lightTweet.retweetCount = tweet.getRetweetCount();
	return lightTweet;
    }
}
