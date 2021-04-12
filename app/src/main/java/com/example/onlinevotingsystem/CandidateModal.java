package com.example.onlinevotingsystem;

import android.graphics.drawable.Drawable;

public class CandidateModal {

    public Drawable partySymbol;
    public String name;
    public String assemblyName;
    public String parliment;

    public CandidateModal(Drawable partySymbol, String name, String assemblyName, String parliment, String partyName) {

        this.partySymbol = partySymbol;
        this.name = name;
        this.assemblyName = assemblyName;
        this.parliment = parliment;
        this.partyName = partyName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String partyName;


    public String getAssemblyName() {
        return assemblyName;
    }

    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
    }

    public String getParliment() {
        return parliment;
    }

    public void setParliment(String parliment) {
        this.parliment = parliment;
    }

    public Drawable getPartySymbol() {
        return partySymbol;
    }

    public void setPartySymbol(Drawable partySymbol) {
        this.partySymbol = partySymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
