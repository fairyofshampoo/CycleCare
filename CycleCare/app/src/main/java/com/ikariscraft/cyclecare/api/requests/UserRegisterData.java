package com.ikariscraft.cyclecare.api.requests;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserRegisterData implements Parcelable{
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String email;
    private String username;
    private String password;
    private String role;
    private int isRegular;
    private int aproxCycleDuration;
    private int aproxPeriodDuration;

    public UserRegisterData(String name, String firstLastName, String secondLastName, String email, String username, String password, String role, int isRegular, int aproxCycleDuration, int aproxPeriodDuration) {
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isRegular = isRegular;
        this.aproxCycleDuration = aproxCycleDuration;
        this.aproxPeriodDuration = aproxPeriodDuration;
    }

    public UserRegisterData(){ }

    protected UserRegisterData(Parcel in) {
        name = in.readString();
        firstLastName = in.readString();
        secondLastName = in.readString();
        email = in.readString();
        username = in.readString();
        password = in.readString();
        role = in.readString();
        isRegular = in.readInt();
        aproxCycleDuration = in.readInt();
        aproxPeriodDuration = in.readInt();
    }

    public static final Creator<UserRegisterData> CREATOR = new Creator<UserRegisterData>() {
        @Override
        public UserRegisterData createFromParcel(Parcel in) {
            return new UserRegisterData(in);
        }

        @Override
        public UserRegisterData[] newArray(int size) {
            return new UserRegisterData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIsRegular() {
        return isRegular;
    }

    public void setIsRegular(int isRegular) {
        this.isRegular = isRegular;
    }

    public int getAproxCycleDuration() {
        return aproxCycleDuration;
    }

    public void setAproxCycleDuration(int aproxCycleDuration) {
        this.aproxCycleDuration = aproxCycleDuration;
    }

    public int getAproxPeriodDuration() {
        return aproxPeriodDuration;
    }

    public void setAproxPeriodDuration(int aproxPeriodDuration) {
        this.aproxPeriodDuration = aproxPeriodDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(firstLastName);
        dest.writeString(secondLastName);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(role);
        dest.writeInt(isRegular);
        dest.writeInt(aproxCycleDuration);
        dest.writeInt(aproxPeriodDuration);
    }
}
