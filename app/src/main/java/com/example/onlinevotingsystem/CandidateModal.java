package com.example.onlinevotingsystem;

import android.graphics.drawable.Drawable;

public class CandidateModal {

    public Drawable partySymbol;
    public String name;

    public CandidateModal(Drawable partySymbol, String name) {
        this.partySymbol = partySymbol;
        this.name = name;
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
