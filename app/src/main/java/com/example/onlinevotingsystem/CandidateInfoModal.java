package com.example.onlinevotingsystem;

public class CandidateInfoModal {

    String name, address, age, mobileNumber, voterId, aadharNumber, parliamentName, partyName, assemblyName, DOB, gender;

    public CandidateInfoModal(String name, String address, String age, String mobileNumber, String voterId, String aadharNumber, String parliamentName, String partyName, String assemblyName, String DOB, String gender) {

        this.name = name;
        this.address = address;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.voterId = voterId;
        this.aadharNumber = aadharNumber;
        this.parliamentName = parliamentName;
        this.partyName = partyName;
        this.assemblyName = assemblyName;
        this.DOB = DOB;
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getParliamentName() {
        return parliamentName;
    }

    public void setParliamentName(String parliamentName) {
        this.parliamentName = parliamentName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getAssemblyName() {
        return assemblyName;
    }

    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
    }
}
