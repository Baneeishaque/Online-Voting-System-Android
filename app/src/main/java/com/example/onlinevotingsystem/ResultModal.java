package com.example.onlinevotingsystem;

import android.graphics.drawable.Drawable;

public class ResultModal {

    public Drawable partySymbol;
    public String name;
    public int noOfVotes;

    public ResultModal(Drawable partySymbol, String name, int noOfVotes) {
        this.partySymbol = partySymbol;
        this.name = name;
        this.noOfVotes = noOfVotes;
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

    public int getNoOfVotes() {
        return noOfVotes;
    }

    public void setNoOfVotes(int noOfVotes) {
        this.noOfVotes = noOfVotes;
    }
}
