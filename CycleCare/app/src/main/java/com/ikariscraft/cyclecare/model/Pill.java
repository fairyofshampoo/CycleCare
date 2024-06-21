package com.ikariscraft.cyclecare.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Pill implements Parcelable {
    private Integer pillId;
    private String status;

    public Pill() {
    }

    protected Pill(Parcel in) {
        pillId = in.readInt();
        status = in.readString();
    }

    public static final Creator<Pill> CREATOR = new Creator<Pill>() {
        @Override
        public Pill createFromParcel(Parcel in) {
            return new Pill(in);
        }

        @Override
        public Pill[] newArray(int size) {
            return new Pill[size];
        }
    };

    public Integer getPillId() {
        return pillId;
    }

    public void setPillId(Integer pillId) {
        this.pillId = pillId;
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
        dest.writeInt(pillId);
        dest.writeString(status);
    }
}
