package com.e.teamup4successapp;

public class FachObject {

   String name;
   String email;
   String abteilung;
   String klasse;

    public FachObject(String name, String email, String abteilung, String klasse) {

        this.name = name;
        this.email = email;
        this.abteilung = abteilung;
        this.klasse = klasse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }
}
