package com.ikariscraft.cyclecare.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BirthControl implements Parcelable {
    private Integer birthControlId;
    private String name;
    private String status;

    public BirthControl() {
    }

    protected BirthControl(Parcel in) {
        birthControlId = (Integer) in.readValue(Integer.class.getClassLoader());
        name = in.readString();
        status = in.readString();
    }

    public static final Creator<BirthControl> CREATOR = new Creator<BirthControl>() {
        @Override
        public BirthControl createFromParcel(Parcel in) {
            return new BirthControl(in);
        }

        @Override
        public BirthControl[] newArray(int size) {
            return new BirthControl[size];
        }
    };

    public Integer getBirthControlId() {
        return birthControlId;
    }

    public void setBirthControlId(Integer birthControlId) {
        this.birthControlId = birthControlId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeValue(birthControlId);
        dest.writeString(name);
        dest.writeString(status);
    }
}
