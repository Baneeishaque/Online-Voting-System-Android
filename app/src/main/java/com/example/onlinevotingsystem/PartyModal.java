package com.example.onlinevotingsystem;

public class PartyModal {

    private String mPartyName;
    private int mPartyImage;

    public PartyModal(String partyName, int partyImage) {

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
