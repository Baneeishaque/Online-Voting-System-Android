package com.example.onlinevotingsystem;

import androidx.annotation.NonNull;

public class VoterInfoModal {

    // TODO : include isAssemblyVoteDone & isParlimentVoteDone
    String name, address, age, mobileNumber, voterId, aadharNumber, parliamentName, assemblyName, DOB, gender;
    boolean isAssemblyVoteDone;

    public boolean isAssemblyVoteDone() {
        return isAssemblyVoteDone;
    }

    public void setAssemblyVoteDone(boolean assemblyVoteDone) {
        isAssemblyVoteDone = assemblyVoteDone;
    }

    public boolean isParlimentVoteDone() {
        return isParlimentVoteDone;
    }

    public void setParlimentVoteDone(boolean parlimentVoteDone) {
        isParlimentVoteDone = parlimentVoteDone;
    }

    boolean isParlimentVoteDone;

    public VoterInfoModal(String name, String address, String age, String mobileNumber, String voterId, String aadharNumber, String parliamentName, String assemblyName, String DOB, String gender, boolean isAssemblyVoteDone, boolean isParlimentVoteDone) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.voterId = voterId;
        this.aadharNumber = aadharNumber;
        this.parliamentName = parliamentName;
        this.assemblyName = assemblyName;
        this.DOB = DOB;
        this.gender = gender;
        this.isAssemblyVoteDone = isAssemblyVoteDone;
        this.isParlimentVoteDone = isParlimentVoteDone;
    }

    public VoterInfoModal() {
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

    public String getAssemblyName() {
        return assemblyName;
    }

    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
    }

    @NonNull
    @Override
    public String toString() {
        return "VoterInfoModal{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age='" + age + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", voterId='" + voterId + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", parliamentName='" + parliamentName + '\'' +
                ", assemblyName='" + assemblyName + '\'' +
                ", DOB='" + DOB + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
