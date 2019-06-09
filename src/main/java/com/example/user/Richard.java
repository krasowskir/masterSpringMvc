package com.example.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "telefonNummer")
public class Richard {


    private int telefonNummer;

    private String geburtsName;

    public int getTelefonNummer() {
        return telefonNummer;
    }

    public void setTelefonNummer(final int telefonNummer) {
        this.telefonNummer = telefonNummer;
    }

    public String getGeburtsName() {
        return geburtsName;
    }

    public void setGeburtsName(final String geburtsName) {
        this.geburtsName = geburtsName;
    }
}
