package com.example.profile;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import com.example.controller.ProfileForm;

@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class UserProfileSession implements Serializable {
    private String twitterHandle;
    private String email;
    private LocalDate birthDate;
    private List<String> tastes = new ArrayList<>();
    private URL picturePath;

    public void saveForm(ProfileForm profileForm) {
	this.twitterHandle = profileForm.getTwitterHandle();
	this.email = profileForm.getEmail();
	this.birthDate = profileForm.getBirthDate();
	this.tastes = profileForm.getTastes();
    }

    public ProfileForm toForm() {
	ProfileForm profileForm = new ProfileForm();
	profileForm.setTwitterHandle(this.twitterHandle);
	profileForm.setEmail(this.email);
	profileForm.setBirthDate(profileForm.getBirthDate());
	profileForm.setTastes(this.tastes);
	return profileForm;
    }

    public Resource getPicturePath() {
	return picturePath != null ? new UrlResource(picturePath) : null;
    }

    public void setPicturePath(Resource picturePath) {
	try {
	    this.picturePath = picturePath.getURL();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
