package com.example.onlinevotingsystem;

public class PartyItem {
    private String mPartyName;
    private int mPartyImage;

    public PartyItem(String partyName, int partyImage) {
        mPartyName = partyName;
        mPartyImage = partyImage;
    }

    public String getPartyName() {
        return mPartyName;
    }

    public int getPartyImage() {
        return mPartyImage;
    }
}
