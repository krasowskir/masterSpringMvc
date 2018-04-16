package com.example.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String email;
    private String twitterHandle;
    private LocalDate date;
    private List<String> tastes = new ArrayList<String>();

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getTwitterHandle() {
	return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
	this.twitterHandle = twitterHandle;
    }

    public LocalDate getDate() {
	return date;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public List<String> getTastes() {
	return tastes;
    }

    public void setTastes(List<String> tastes) {
	this.tastes = tastes;
    }

}
